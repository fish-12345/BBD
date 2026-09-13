package io.github.romanvht.byedpi.utility

import android.content.Context
import com.google.gson.Gson
import io.github.romanvht.byedpi.data.Command
import io.github.romanvht.byedpi.data.Mode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Context.getDataStore(): DataStoreManager = DataStoreManager(this)

class AppPreferences(private val dataStore: DataStoreManager) {
    var language by dataStore.pref("language", "system")
    var theme by dataStore.pref("app_theme", "system")
    var colorScheme by dataStore.pref("color_scheme", "Default")

    private var modeRaw by dataStore.pref("byedpi_mode", "vpn")
    var mode: Mode
        get() = Mode.fromString(modeRaw)
        set(value) { modeRaw = value.toString().lowercase() }

    var dnsIp by dataStore.pref("dns_ip", "1.1.1.1")
    var dnsSolution by dataStore.pref("dns_solution", "1.1.1.1")
    var ipv6Enable by dataStore.pref("ipv6_enable", false)
    var applistType by dataStore.pref("applist_type", "disable")
    var autostart by dataStore.pref("autostart", false)
    var autoConnect by dataStore.pref("auto_connect", false)
    var cmdEnable by dataStore.pref("byedpi_enable_cmd_settings", false)
    var proxyIp by dataStore.pref("byedpi_proxy_ip", "127.0.0.1")
    var proxyPort by dataStore.pref("byedpi_proxy_port", "1080")
    var cmdArgs by dataStore.pref("byedpi_cmd_args", "")
    var trafficMonitoring by dataStore.pref("traffic_monitoring", true)
    var domainStrategiesJson by dataStore.pref("byedpi_domain_strategies", "[]")

    fun getProfileName(command: String): String? {
        try {
            val historyJson = dataStore.get("byedpi_command_history", "")
            if (historyJson.isBlank()) return null
            val history = Gson().fromJson(historyJson, Array<Command>::class.java)
            return history.find { it.text == command }?.name
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    // Flows for Reactive UI
    val modeFlow: Flow<Mode> = dataStore.getFlow("byedpi_mode", "vpn").map { Mode.fromString(it) }
    val cmdEnableFlow: Flow<Boolean> = dataStore.getFlow("byedpi_enable_cmd_settings", false)
    val trafficMonitoringFlow: Flow<Boolean> = dataStore.getFlow("traffic_monitoring", true)
}

class TestPreferences(private val dataStore: DataStoreManager) {
    var delay by dataStore.pref("byedpi_proxytest_delay", "6")
    var requests by dataStore.pref("byedpi_proxytest_requests", "1")
    var timeout by dataStore.pref("byedpi_proxytest_timeout", "5")
    var sni by dataStore.pref("byedpi_proxytest_sni", "max.ru")
    var fullLog by dataStore.pref("byedpi_proxytest_fulllog", false)
    var logClickable by dataStore.pref("byedpi_proxytest_logclickable", false)
    var autoSort by dataStore.pref("byedpi_proxytest_autosort", true)
    var showAll by dataStore.pref("byedpi_proxytest_showall", false)
    var strategyLists by dataStore.pref("byedpi_proxytest_strategy_lists", setOf("builtin"))
    var commands by dataStore.pref("byedpi_proxytest_commands", "")
    var concurrentRequests by dataStore.pref("byedpi_proxytest_concurrent_requests", "20")
}

class UIPreferences(private val dataStore: DataStoreManager) {
    var maxConnections by dataStore.pref("byedpi_max_connections", "512")
    var bufferSize by dataStore.pref("byedpi_buffer_size", "16384")
    var noDomain by dataStore.pref("byedpi_no_domain", false)
    var tcpFastOpen by dataStore.pref("byedpi_tcp_fast_open", false)
    var desyncMethod by dataStore.pref("byedpi_desync_method", "oob")
    var hostsMode by dataStore.pref("byedpi_hosts_mode", "disable")
    var hostsBlacklist by dataStore.pref("byedpi_hosts_blacklist", "")
    var hostsWhitelist by dataStore.pref("byedpi_hosts_whitelist", "")
    var defaultTtl by dataStore.pref("byedpi_default_ttl", "0")
    var splitPosition by dataStore.pref("byedpi_split_position", "1")
    var splitAtHost by dataStore.pref("byedpi_split_at_host", false)
    var dropSack by dataStore.pref("byedpi_drop_sack", false)
    var fakeTtl by dataStore.pref("byedpi_fake_ttl", "8")
    var fakeOffset by dataStore.pref("byedpi_fake_offset", "0")
    var fakeSni by dataStore.pref("byedpi_fake_sni", "www.iana.org")
    var oobData by dataStore.pref("byedpi_oob_data", "a")
    var desyncHttp by dataStore.pref("byedpi_desync_http", true)
    var desyncHttps by dataStore.pref("byedpi_desync_https", true)
    var desyncUdp by dataStore.pref("byedpi_desync_udp", true)
    var hostMixedCase by dataStore.pref("byedpi_host_mixed_case", false)
    var domainMixedCase by dataStore.pref("byedpi_domain_mixed_case", false)
    var hostRemoveSpaces by dataStore.pref("byedpi_host_remove_spaces", false)
    var tlsRecEnabled by dataStore.pref("byedpi_tlsrec_enabled", false)
    var tlsRecPosition by dataStore.pref("byedpi_tlsrec_position", "0")
    var tlsRecAtSni by dataStore.pref("byedpi_tlsrec_at_sni", false)
    var udpFakeCount by dataStore.pref("byedpi_udp_fake_count", "1")
}

// Extension to get DataStore based proxy ip and port
fun DataStoreManager.getProxyIpAndPort(): Pair<String, String> {
    val cmdEnable = get("byedpi_enable_cmd_settings", false)
    val cmdArgsStr = get("byedpi_cmd_args", "")

    var cmdIp: String? = null
    var cmdPort: String? = null

    if (cmdEnable && cmdArgsStr.isNotBlank()) {
        val cmdArgsList = shellSplit(cmdArgsStr)

        fun getArgValue(argsList: List<String>, keys: List<String>): String? {
            for (i in argsList.indices) {
                val arg = argsList[i]
                for (key in keys) {
                    if (key.startsWith("--")) {
                        if (arg == key && i + 1 < argsList.size) return argsList[i + 1]
                        else if (arg.startsWith("$key=")) return arg.substringAfter('=')
                    } else if (key.startsWith("-")) {
                        if (arg.startsWith(key) && arg.length > key.length) return arg.substring(key.length)
                        else if (arg == key && i + 1 < argsList.size) return argsList[i + 1]
                    }
                }
            }
            return null
        }

        cmdIp = getArgValue(cmdArgsList, listOf("--ip", "-i"))
        cmdPort = getArgValue(cmdArgsList, listOf("--port", "-p"))
    }

    val ip = cmdIp ?: get("byedpi_proxy_ip", "127.0.0.1")
    val port = cmdPort ?: get("byedpi_proxy_port", "1080")

    return Pair(ip, port)
}