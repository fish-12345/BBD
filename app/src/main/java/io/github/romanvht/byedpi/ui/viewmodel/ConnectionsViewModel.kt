package io.github.romanvht.byedpi.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.romanvht.byedpi.core.ByeDpiProxy
import io.github.romanvht.byedpi.data.AppStatus
import io.github.romanvht.byedpi.services.appStatus
import io.github.romanvht.byedpi.utility.AppPreferences
import io.github.romanvht.byedpi.utility.getDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class ConnectionProtocol { Tcp, Udp }

enum class ConnectionState { Started, Desync, Closed, Timeout, Error }

/** Протокол, определённый нативной частью по первым байтам соединения. */
enum class ConnectionKind(val label: String?) {
    Unknown(null),
    Tls("TLS"),
    Http("HTTP"),
    Http2("HTTP/2"),
    Dns("DNS"),
    Quic("QUIC"),
    Dtls("DTLS"),
    Ssh("SSH"),
    Stun("STUN");

    companion object {
        fun fromCode(code: Int): ConnectionKind = entries.getOrNull(code) ?: Unknown
    }
}

data class ConnectionEvent(
    /** Идентификатор записи в нативной таблице, уникален за всё время работы. */
    val id: Long,
    /** Начало соединения, epoch millis. */
    val startEpoch: Long,
    /** Конец соединения, epoch millis; null — ещё открыто. */
    val endEpoch: Long?,
    val host: String,
    val destination: String,
    val state: ConnectionState,
    val protocol: ConnectionProtocol = ConnectionProtocol.Tcp,
    val kind: ConnectionKind = ConnectionKind.Unknown,
    val detail: String? = null,
    /** Отдано приложением (байт). */
    val uploaded: Long? = null,
    /** Скачано из сети (байт). */
    val downloaded: Long? = null,
    /** Число раундов обработки — грубый аналог количества запросов. */
    val requests: Int? = null,
    val isDns: Boolean = false
) {
    val isOpen: Boolean
        get() = endEpoch == null &&
            (state == ConnectionState.Started || state == ConnectionState.Desync)

    /** Есть ли у соединения имя хоста, а не только IP. */
    val hasName: Boolean
        get() = host.isNotBlank() && host != destination

    val time: String
        get() = TIME_FORMAT.format(Date(startEpoch))

    fun durationMs(nowEpoch: Long): Long =
        ((endEpoch ?: nowEpoch) - startEpoch).coerceAtLeast(0)

    companion object {
        private val TIME_FORMAT = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    }
}

/**
 * Соединения, объединённые в одну раскрывающуюся запись.
 *
 * Ключ — хост (SNI), если он известен: один сайт может ходить через разные IP,
 * и такие соединения логичнее видеть вместе. Без имени группируем по IP.
 */
data class ConnectionGroup(
    val key: String,
    /** Подпись группы: хост или IP. */
    val title: String,
    /** true — группа собрана по хосту, false — по IP. */
    val byHost: Boolean,
    val entries: List<ConnectionEvent>
) {
    val size: Int get() = entries.size
    val downloaded: Long get() = entries.sumOf { it.downloaded ?: 0L }
    val uploaded: Long get() = entries.sumOf { it.uploaded ?: 0L }
    val latestStartEpoch: Long get() = entries.maxOf { it.startEpoch }
    val isOpen: Boolean get() = entries.any { it.isOpen }
    val hasError: Boolean
        get() = entries.any {
            it.state == ConnectionState.Error || it.state == ConnectionState.Timeout
        }
    val isDns: Boolean get() = entries.any { it.isDns }
    val kinds: List<ConnectionKind>
        get() = entries.map { it.kind }
            .filter { it != ConnectionKind.Unknown }
            .distinct()
    val hosts: List<String>
        get() = entries.filter { it.hasName }.map { it.host }.distinct()
    /** Уникальные IP назначения внутри группы. */
    val ips: List<String>
        get() = entries.map { destinationIp(it.destination) }
            .filter { it.isNotBlank() }
            .distinct()
}

/** IP без порта из строки вида "1.2.3.4:443" или "2001:db8::1:443". */
internal fun destinationIp(destination: String): String {
    val value = destination.trim()
    if (value.isBlank()) return ""
    if (value.startsWith("[")) {
        return value.substringAfter('[').substringBefore(']')
    }
    return value.substringBeforeLast(':')
}

internal fun normalizeIp(value: String): String =
    value.trim().removePrefix("[").removeSuffix("]")

internal fun parseHiddenKinds(raw: String): Set<ConnectionKind> =
    raw.split(',')
        .mapNotNull { it.trim().toIntOrNull() }
        .map { ConnectionKind.fromCode(it) }
        .filter { it != ConnectionKind.Unknown }
        .toSet()

internal fun encodeHiddenKinds(kinds: Set<ConnectionKind>): String =
    kinds.map { it.ordinal }.sorted().joinToString(",")

/**
 * Живой список соединений через прокси.
 *
 * Данные приходят из нативной таблицы `conn_stats` (см. conn_stats.h) —
 * это отдельный от лога канал: прокси пишет туда состояние, адрес и счётчики
 * байт, а UI раз в секунду забирает готовый снимок через JNI.
 */
class ConnectionsViewModel(application: Application) : AndroidViewModel(application) {

    private val appPrefs = AppPreferences(application.getDataStore())

    val events = mutableStateListOf<ConnectionEvent>()

    var isRunning by mutableStateOf(appStatus.first == AppStatus.Running)
        private set

    var isPaused by mutableStateOf(false)
        private set

    var totalCount by mutableIntStateOf(0)
        private set

    var openCount by mutableIntStateOf(0)
        private set

    var errorCount by mutableIntStateOf(0)
        private set

    var hideClosed by mutableStateOf(appPrefs.connectionsHideClosed)
        private set

    var hideDns by mutableStateOf(appPrefs.connectionsHideDns)
        private set

    var hideErrors by mutableStateOf(appPrefs.connectionsHideErrors)
        private set

    var hideUnnamed by mutableStateOf(appPrefs.connectionsHideUnnamed)
        private set

    var hideTcp by mutableStateOf(appPrefs.connectionsHideTcp)
        private set

    var hideUdp by mutableStateOf(appPrefs.connectionsHideUdp)
        private set

    var hideUnknownKind by mutableStateOf(appPrefs.connectionsHideUnknownKind)
        private set

    var hiddenKinds by mutableStateOf(parseHiddenKinds(appPrefs.connectionsHiddenKinds))
        private set

    /** IP DNS-сервера из настроек: такие адреса помечаем как DNS сразу. */
    private var dnsIp = normalizeIp(appPrefs.dnsIp)

    /** Записи с id <= watermark скрыты кнопкой «Очистить». */
    private var watermark = 0L

    /** Сколько соединений было создано на момент очистки списка. */
    private var totalBase = 0L

    private var lastTotal = 0L
    private var pollJob: Job? = null

    /** Сколько фильтров включено — для значка на кнопке. */
    val activeFilterCount: Int
        get() = listOf(
            hideClosed, hideDns, hideErrors, hideUnnamed,
            hideTcp, hideUdp, hideUnknownKind
        ).count { it } + hiddenKinds.size

    init {
        viewModelScope.launch {
            snapshotFlow { appStatus.first }
                .distinctUntilChanged()
                .collect { status ->
                    val running = status == AppStatus.Running
                    if (running == isRunning) return@collect
                    isRunning = running
                    if (!running) {
                        for (i in events.indices) {
                            val event = events[i]
                            if (event.isOpen) {
                                events[i] = event.copy(
                                    state = ConnectionState.Closed,
                                    endEpoch = event.endEpoch ?: System.currentTimeMillis()
                                )
                            }
                        }
                        openCount = 0
                    }
                }
        }
    }

    fun startMonitoring() {
        reloadFilters()
        if (pollJob?.isActive == true) return
        pollJob = viewModelScope.launch {
            while (isActive) {
                if (isRunning && !isPaused) poll()
                delay(POLL_INTERVAL_MS)
            }
        }
    }

    fun stopMonitoring() {
        pollJob?.cancel()
        pollJob = null
    }

    /** Подхватывает фильтры, изменённые на отдельном экране. */
    private fun reloadFilters() {
        hideClosed = appPrefs.connectionsHideClosed
        hideDns = appPrefs.connectionsHideDns
        hideErrors = appPrefs.connectionsHideErrors
        hideUnnamed = appPrefs.connectionsHideUnnamed
        hideTcp = appPrefs.connectionsHideTcp
        hideUdp = appPrefs.connectionsHideUdp
        hideUnknownKind = appPrefs.connectionsHideUnknownKind
        hiddenKinds = parseHiddenKinds(appPrefs.connectionsHiddenKinds)
        dnsIp = normalizeIp(appPrefs.dnsIp)
    }

    fun togglePause() {
        isPaused = !isPaused
    }

    /** События с учётом включённых фильтров. */
    fun visibleEvents(): List<ConnectionEvent> {
        return events.filter { event ->
            (!hideClosed || event.isOpen) &&
                (!hideErrors || !isError(event)) &&
                (!hideDns || !event.isDns) &&
                (!hideUnnamed || event.hasName) &&
                (!hideTcp || event.protocol != ConnectionProtocol.Tcp) &&
                (!hideUdp || event.protocol != ConnectionProtocol.Udp) &&
                (!hideUnknownKind || event.kind != ConnectionKind.Unknown) &&
                event.kind !in hiddenKinds
        }
    }

    /**
     * Список для отображения: одинаковые IP собираются в группы.
     * Порядок: активные, затем ошибки, затем закрытые; внутри — новые сверху.
     */
    /**
     * Список для отображения: одинаковые хосты (а без имени — IP) собираются
     * в группы. Порядок: активные, затем ошибки, затем закрытые; внутри — новые сверху.
     */
    fun groupedEvents(): List<ConnectionGroup> {
        val sorted = visibleEvents().sortedWith(
            compareBy<ConnectionEvent>({ stateRank(it) }, { -it.startEpoch })
        )
        return sorted.groupBy { groupKey(it) }
            .map { (key, entries) ->
                val byHost = key.startsWith(HOST_KEY_PREFIX)
                val title = if (byHost) {
                    entries.first().host
                } else {
                    key.removePrefix(IP_KEY_PREFIX)
                }
                ConnectionGroup(
                    key = key,
                    title = title,
                    byHost = byHost,
                    entries = entries
                )
            }
            .sortedWith(
                compareBy<ConnectionGroup>({ groupRank(it) }, { -it.latestStartEpoch })
            )
    }

    fun clear() {
        watermark = events.maxOfOrNull { it.id } ?: watermark
        totalBase = lastTotal
        events.clear()
        totalCount = 0
        openCount = 0
        errorCount = 0
    }

    override fun onCleared() {
        stopMonitoring()
        super.onCleared()
    }

    private suspend fun poll() {
        val raw = withContext(Dispatchers.IO) {
            runCatching { ByeDpiProxy.jniConnStats() }.getOrNull()
        } ?: return
        parseSnapshot(raw)
    }

    private fun parseSnapshot(raw: String) {
        val lines = raw.split('\n')
        if (lines.isEmpty()) return

        val total = lines[0].trim().toLongOrNull() ?: return

        /* Нативная таблица очищается при старте прокси, счётчик созданных
           соединений обнуляется. Старые id при этом не переиспользуются. */
        if (total < lastTotal) {
            events.clear()
            watermark = 0
            totalBase = 0
        }
        lastTotal = total
        totalCount = (total - totalBase).coerceAtLeast(0).toInt()

        val parsed = ArrayList<ConnectionEvent>(lines.size)
        for (index in 1 until lines.size) {
            val line = lines[index]
            if (line.isEmpty()) continue
            val event = parseLine(line) ?: continue
            if (event.id <= watermark) continue
            parsed.add(event)
        }

        events.clear()
        events.addAll(parsed)

        openCount = parsed.count { it.isOpen }
        errorCount = parsed.count { isError(it) }
    }

    private fun parseLine(line: String): ConnectionEvent? {
        val parts = line.split('\t')
        if (parts.size < FIELDS) return null

        val id = parts[0].toLongOrNull() ?: return null
        val state = when (parts[3].toIntOrNull()) {
            STATE_OPEN -> ConnectionState.Started
            STATE_DESYNC -> ConnectionState.Desync
            STATE_CLOSED -> ConnectionState.Closed
            STATE_TIMEOUT -> ConnectionState.Timeout
            STATE_ERROR -> ConnectionState.Error
            else -> ConnectionState.Closed
        }
        val protocol = if (parts[4].toIntOrNull() == PROTO_UDP) {
            ConnectionProtocol.Udp
        } else {
            ConnectionProtocol.Tcp
        }
        val detectedKind = ConnectionKind.fromCode(parts[6].toIntOrNull() ?: 0)

        val start = parts[10].toLongOrNull() ?: return null
        val end = parts[11].toLongOrNull() ?: 0L
        val host = parts[12]
        val destination = parts[13]
        val up = parts[8].toLongOrNull() ?: 0L
        val down = parts[9].toLongOrNull() ?: 0L
        val rounds = parts[7].toIntOrNull() ?: 0

        val dnsByIp = isDnsDestination(destination)
        val isDns = parts[5] == "1" || dnsByIp
        val kind = if (detectedKind == ConnectionKind.Unknown && dnsByIp) {
            ConnectionKind.Dns
        } else {
            detectedKind
        }

        return ConnectionEvent(
            id = id,
            startEpoch = start,
            endEpoch = end.takeIf { it > 0 },
            host = host.ifBlank { destination },
            destination = destination,
            state = state,
            protocol = protocol,
            kind = kind,
            detail = parts[14].ifBlank { null },
            uploaded = up.takeIf { it > 0 },
            downloaded = down.takeIf { it > 0 },
            requests = rounds.takeIf { it > 0 },
            isDns = isDns
        )
    }

    private fun isDnsDestination(destination: String): Boolean {
        if (dnsIp.isBlank()) return false
        val ip = destinationIp(destination)
        return ip.isNotBlank() && ip.equals(dnsIp, ignoreCase = true)
    }

    private fun isError(event: ConnectionEvent): Boolean =
        event.state == ConnectionState.Error || event.state == ConnectionState.Timeout

    private fun stateRank(event: ConnectionEvent): Int = when {
        event.isOpen -> 0
        isError(event) -> 1
        else -> 2
    }

    private fun groupRank(group: ConnectionGroup): Int = when {
        group.isOpen -> 0
        group.hasError -> 1
        else -> 2
    }

    private fun groupKey(event: ConnectionEvent): String {
        if (event.hasName) {
            return HOST_KEY_PREFIX + event.host.lowercase(Locale.ROOT)
        }
        val ip = destinationIp(event.destination)
        val address = ip.ifBlank { event.destination.ifBlank { UNKNOWN_KEY } }
        return IP_KEY_PREFIX + address
    }

    companion object {
        private const val POLL_INTERVAL_MS = 1000L

        private const val FIELDS = 15

        private const val STATE_OPEN = 0
        private const val STATE_DESYNC = 1
        private const val STATE_CLOSED = 2
        private const val STATE_TIMEOUT = 3
        private const val STATE_ERROR = 4

        private const val PROTO_UDP = 1

        private const val UNKNOWN_KEY = "—"

        /** Префиксы ключей групп, чтобы хост и IP не совпали случайно. */
        private const val HOST_KEY_PREFIX = "h:"
        private const val IP_KEY_PREFIX = "i:"
    }
}
