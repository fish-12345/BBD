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

    val time: String
        get() = TIME_FORMAT.format(Date(startEpoch))

    fun durationMs(nowEpoch: Long): Long =
        ((endEpoch ?: nowEpoch) - startEpoch).coerceAtLeast(0)

    companion object {
        private val TIME_FORMAT = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    }
}

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

    /** Записи с id <= watermark скрыты кнопкой «Очистить». */
    private var watermark = 0L

    /** Сколько соединений было создано на момент очистки списка. */
    private var totalBase = 0L

    private var lastTotal = 0L
    private var pollJob: Job? = null

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

    fun togglePause() {
        isPaused = !isPaused
    }

    fun toggleHideClosed() {
        hideClosed = !hideClosed
        appPrefs.connectionsHideClosed = hideClosed
    }

    fun toggleHideDns() {
        hideDns = !hideDns
        appPrefs.connectionsHideDns = hideDns
    }

    /** События с учётом включённых фильтров. */
    fun visibleEvents(): List<ConnectionEvent> {
        if (!hideClosed && !hideDns) return events
        return events.filter { event ->
            (!hideClosed || event.isOpen) && (!hideDns || !event.isDns)
        }
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
        errorCount = parsed.count { it.state == ConnectionState.Error }
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

        val start = parts[9].toLongOrNull() ?: return null
        val end = parts[10].toLongOrNull() ?: 0L
        val host = parts[11]
        val destination = parts[12]
        val up = parts[7].toLongOrNull() ?: 0L
        val down = parts[8].toLongOrNull() ?: 0L
        val rounds = parts[6].toIntOrNull() ?: 0

        return ConnectionEvent(
            id = id,
            startEpoch = start,
            endEpoch = end.takeIf { it > 0 },
            host = host.ifBlank { destination },
            destination = destination,
            state = state,
            protocol = protocol,
            detail = parts[13].ifBlank { null },
            uploaded = up.takeIf { it > 0 },
            downloaded = down.takeIf { it > 0 },
            requests = rounds.takeIf { it > 0 },
            isDns = parts[5] == "1"
        )
    }

    companion object {
        private const val POLL_INTERVAL_MS = 1000L

        private const val FIELDS = 14

        private const val STATE_OPEN = 0
        private const val STATE_DESYNC = 1
        private const val STATE_CLOSED = 2
        private const val STATE_TIMEOUT = 3
        private const val STATE_ERROR = 4

        private const val PROTO_UDP = 1
    }
}
