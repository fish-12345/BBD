package io.github.romanvht.byedpi.utility

import android.content.Context
import com.google.gson.Gson
import io.github.romanvht.byedpi.data.Command
import io.github.romanvht.byedpi.data.Mode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Context.getDataStore(): DataStoreManager = DataStoreManager(this)

class AppPreferences(private val dataStore: DataStoreManager) {
    var language: String
        get() = dataStore.get("language", "system")
        set(value) = dataStore.setAsync("language", value)

    var theme: String
        get() = dataStore.get("app_theme", "system")
        set(value) = dataStore.setAsync("app_theme", value)

    var colorScheme: String
        get() = dataStore.get("color_scheme", "Default")
        set(value) = dataStore.setAsync("color_scheme", value)

    var mode: Mode
        get() = Mode.fromString(dataStore.get("byedpi_mode", "vpn"))
        set(value) = dataStore.setAsync("byedpi_mode", value.toString().lowercase())

    var dnsIp: String
        get() = dataStore.get("dns_ip", "1.1.1.1")
        set(value) = dataStore.setAsync("dns_ip", value)

    var dnsSolution: String
        get() = dataStore.get("dns_solution", "1.1.1.1")
        set(value) = dataStore.setAsync("dns_solution", value)

    var ipv6Enable: Boolean
        get() = dataStore.get("ipv6_enable", false)
        set(value) = dataStore.setAsync("ipv6_enable", value)

    var applistType: String
        get() = dataStore.get("applist_type", "disable")
        set(value) = dataStore.setAsync("applist_type", value)

    var autostart: Boolean
        get() = dataStore.get("autostart", false)
        set(value) = dataStore.setAsync("autostart", value)

    var autoConnect: Boolean
        get() = dataStore.get("auto_connect", false)
        set(value) = dataStore.setAsync("auto_connect", value)

    var cmdEnable: Boolean
        get() = dataStore.get("byedpi_enable_cmd_settings", false)
        set(value) = dataStore.setAsync("byedpi_enable_cmd_settings", value)

    var proxyIp: String
        get() = dataStore.get("byedpi_proxy_ip", "127.0.0.1")
        set(value) = dataStore.setAsync("byedpi_proxy_ip", value)

    var proxyPort: String
        get() = dataStore.get("byedpi_proxy_port", "1080")
        set(value) = dataStore.setAsync("byedpi_proxy_port", value)

    var cmdArgs: String
        get() = dataStore.get("byedpi_cmd_args", "")
        set(value) = dataStore.setAsync("byedpi_cmd_args", value)

    var wifiProfile: String
        get() = dataStore.get("byedpi_wifi_profile", "")
        set(value) = dataStore.setAsync("byedpi_wifi_profile", value)

    var mobileProfile: String
        get() = dataStore.get("byedpi_mobile_profile", "")
        set(value) = dataStore.setAsync("byedpi_mobile_profile", value)

    var trafficMonitoring: Boolean
        get() = dataStore.get("traffic_monitoring", true)
        set(value) = dataStore.setAsync("traffic_monitoring", value)

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
    var delay: String
        get() = dataStore.get("byedpi_proxytest_delay", "6")
        set(value) = dataStore.setAsync("byedpi_proxytest_delay", value)

    var requests: String
        get() = dataStore.get("byedpi_proxytest_requests", "1")
        set(value) = dataStore.setAsync("byedpi_proxytest_requests", value)

    var timeout: String
        get() = dataStore.get("byedpi_proxytest_timeout", "5")
        set(value) = dataStore.setAsync("byedpi_proxytest_timeout", value)

    var sni: String
        get() = dataStore.get("byedpi_proxytest_sni", "max.ru")
        set(value) = dataStore.setAsync("byedpi_proxytest_sni", value)

    var fullLog: Boolean
        get() = dataStore.get("byedpi_proxytest_fulllog", false)
        set(value) = dataStore.setAsync("byedpi_proxytest_fulllog", value)

    var logClickable: Boolean
        get() = dataStore.get("byedpi_proxytest_logclickable", false)
        set(value) = dataStore.setAsync("byedpi_proxytest_logclickable", value)

    var autoSort: Boolean
        get() = dataStore.get("byedpi_proxytest_autosort", true)
        set(value) = dataStore.setAsync("byedpi_proxytest_autosort", value)

    var showAll: Boolean
        get() = dataStore.get("byedpi_proxytest_showall", false)
        set(value) = dataStore.setAsync("byedpi_proxytest_showall", value)

    var strategyLists: Set<String>
        get() = dataStore.get("byedpi_proxytest_strategy_lists", setOf("builtin"))
        set(value) = dataStore.setAsync("byedpi_proxytest_strategy_lists", value)

    var commands: String
        get() = dataStore.get("byedpi_proxytest_commands", "")
        set(value) = dataStore.setAsync("byedpi_proxytest_commands", value)

    var concurrentRequests: String
        get() = dataStore.get("byedpi_proxytest_concurrent_requests", "20")
        set(value) = dataStore.setAsync("byedpi_proxytest_concurrent_requests", value)
}

class UIPreferences(private val dataStore: DataStoreManager) {
    var maxConnections: String
        get() = dataStore.get("byedpi_max_connections", "512")
        set(value) = dataStore.setAsync("byedpi_max_connections", value)

    var bufferSize: String
        get() = dataStore.get("byedpi_buffer_size", "16384")
        set(value) = dataStore.setAsync("byedpi_buffer_size", value)

    var noDomain: Boolean
        get() = dataStore.get("byedpi_no_domain", false)
        set(value) = dataStore.setAsync("byedpi_no_domain", value)

    var tcpFastOpen: Boolean
        get() = dataStore.get("byedpi_tcp_fast_open", false)
        set(value) = dataStore.setAsync("byedpi_tcp_fast_open", value)

    var desyncMethod: String
        get() = dataStore.get("byedpi_desync_method", "oob")
        set(value) = dataStore.setAsync("byedpi_desync_method", value)

    var hostsMode: String
        get() = dataStore.get("byedpi_hosts_mode", "disable")
        set(value) = dataStore.setAsync("byedpi_hosts_mode", value)

    var hostsBlacklist: String
        get() = dataStore.get("byedpi_hosts_blacklist", "")
        set(value) = dataStore.setAsync("byedpi_hosts_blacklist", value)

    var hostsWhitelist: String
        get() = dataStore.get("byedpi_hosts_whitelist", "")
        set(value) = dataStore.setAsync("byedpi_hosts_whitelist", value)

    var defaultTtl: String
        get() = dataStore.get("byedpi_default_ttl", "0")
        set(value) = dataStore.setAsync("byedpi_default_ttl", value)

    var splitPosition: String
        get() = dataStore.get("byedpi_split_position", "1")
        set(value) = dataStore.setAsync("byedpi_split_position", value)

    var splitAtHost: Boolean
        get() = dataStore.get("byedpi_split_at_host", false)
        set(value) = dataStore.setAsync("byedpi_split_at_host", value)

    var dropSack: Boolean
        get() = dataStore.get("byedpi_drop_sack", false)
        set(value) = dataStore.setAsync("byedpi_drop_sack", value)

    var fakeTtl: String
        get() = dataStore.get("byedpi_fake_ttl", "8")
        set(value) = dataStore.setAsync("byedpi_fake_ttl", value)

    var fakeOffset: String
        get() = dataStore.get("byedpi_fake_offset", "0")
        set(value) = dataStore.setAsync("byedpi_fake_offset", value)

    var fakeSni: String
        get() = dataStore.get("byedpi_fake_sni", "www.iana.org")
        set(value) = dataStore.setAsync("byedpi_fake_sni", value)

    var oobData: String
        get() = dataStore.get("byedpi_oob_data", "a")
        set(value) = dataStore.setAsync("byedpi_oob_data", value)

    var desyncHttp: Boolean
        get() = dataStore.get("byedpi_desync_http", true)
        set(value) = dataStore.setAsync("byedpi_desync_http", value)

    var desyncHttps: Boolean
        get() = dataStore.get("byedpi_desync_https", true)
        set(value) = dataStore.setAsync("byedpi_desync_https", value)

    var desyncUdp: Boolean
        get() = dataStore.get("byedpi_desync_udp", true)
        set(value) = dataStore.setAsync("byedpi_desync_udp", value)

    var hostMixedCase: Boolean
        get() = dataStore.get("byedpi_host_mixed_case", false)
        set(value) = dataStore.setAsync("byedpi_host_mixed_case", value)

    var domainMixedCase: Boolean
        get() = dataStore.get("byedpi_domain_mixed_case", false)
        set(value) = dataStore.setAsync("byedpi_domain_mixed_case", value)

    var hostRemoveSpaces: Boolean
        get() = dataStore.get("byedpi_host_remove_spaces", false)
        set(value) = dataStore.setAsync("byedpi_host_remove_spaces", value)

    var tlsRecEnabled: Boolean
        get() = dataStore.get("byedpi_tlsrec_enabled", false)
        set(value) = dataStore.setAsync("byedpi_tlsrec_enabled", value)

    var tlsRecPosition: String
        get() = dataStore.get("byedpi_tlsrec_position", "0")
        set(value) = dataStore.setAsync("byedpi_tlsrec_position", value)

    var tlsRecAtSni: Boolean
        get() = dataStore.get("byedpi_tlsrec_at_sni", false)
        set(value) = dataStore.setAsync("byedpi_tlsrec_at_sni", value)

    var udpFakeCount: String
        get() = dataStore.get("byedpi_udp_fake_count", "1")
        set(value) = dataStore.setAsync("byedpi_udp_fake_count", value)
}

// Extension to get DataStore based proxy ip and port
fun DataStoreManager.getProxyIpAndPort(): Pair<String, String> {
    val cmdEnable = get("byedpi_enable_cmd_settings", false)
    val cmdArgsStr = get("byedpi_cmd_args", "")

    var cmdIp: String? = null
    var cmdPort: String? = null

    if (cmdEnable && cmdArgsStr.isNotBlank()) {
        val cmdArgs = shellSplit(cmdArgsStr)

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

        cmdIp = getArgValue(cmdArgs, listOf("--ip", "-i"))
        cmdPort = getArgValue(cmdArgs, listOf("--port", "-p"))
    }

    val ip = cmdIp ?: get("byedpi_proxy_ip", "127.0.0.1")
    val port = cmdPort ?: get("byedpi_proxy_port", "1080")

    return Pair(ip, port)
}
