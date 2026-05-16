package io.github.romanvht.byedpi.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.romanvht.byedpi.data.Mode
import io.github.romanvht.byedpi.data.ThemeManager
import io.github.romanvht.byedpi.utility.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStore = application.getDataStore()
    private val appPrefs = AppPreferences(dataStore)
    private val themeManager = ThemeManager(application)

    var language by mutableStateOf(appPrefs.language)
        private set
    var theme by mutableStateOf(appPrefs.theme)
        private set
    var colorScheme by mutableStateOf(appPrefs.colorScheme)
        private set
    var mode by mutableStateOf(appPrefs.mode)
        private set
    var dnsIp by mutableStateOf(appPrefs.dnsIp)
        private set
    var dnsSolution by mutableStateOf(appPrefs.dnsSolution)
        private set
    var ipv6Enable by mutableStateOf(appPrefs.ipv6Enable)
        private set
    var applistType by mutableStateOf(appPrefs.applistType)
        private set
    var autostart by mutableStateOf(appPrefs.autostart)
        private set
    var autoConnect by mutableStateOf(appPrefs.autoConnect)
        private set
    var cmdEnable by mutableStateOf(appPrefs.cmdEnable)
        private set
    var proxyIp by mutableStateOf(appPrefs.proxyIp)
        private set
    var proxyPort by mutableStateOf(appPrefs.proxyPort)
        private set
    var dynamicColors by mutableStateOf(themeManager.getDynamicColor())
        private set
    var isBatteryOptimizationEnabled by mutableStateOf(application.isBatteryOptimizationEnabled())
        private set
    var hasStorageAccess by mutableStateOf(application.hasStorageAccess())
        private set
    var trafficMonitoring by mutableStateOf(appPrefs.trafficMonitoring)
        private set

    val isProxyVisible: Boolean
        get() {
            val cmdEnableSync = dataStore.get("byedpi_enable_cmd_settings", false)
            val cmdArgsStr = dataStore.get("byedpi_cmd_args", "")
            
            if (!cmdEnableSync) return true
            
            val cmdArgs = shellSplit(cmdArgsStr)
            fun hasArg(argsList: List<String>, keys: List<String>): Boolean {
                for (arg in argsList) {
                    for (key in keys) {
                        if (arg == key || arg.startsWith("$key=")) return true
                    }
                }
                return false
            }
            
            val hasIp = hasArg(cmdArgs, listOf("--ip", "-i"))
            val hasPort = hasArg(cmdArgs, listOf("--port", "-p"))
            
            return !hasIp && !hasPort
        }

    init {
        dataStore.run {
            observe(viewModelScope, "language", "system") { language = it }
            observe(viewModelScope, "app_theme", "system") { theme = it }
            observe(viewModelScope, "color_scheme", "Default") { colorScheme = it }
            observe(viewModelScope, "dns_ip", "1.1.1.1") { dnsIp = it }
            observe(viewModelScope, "dns_solution", "1.1.1.1") { dnsSolution = it }
            observe(viewModelScope, "ipv6_enable", false) { ipv6Enable = it }
            observe(viewModelScope, "applist_type", "disable") { applistType = it }
            observe(viewModelScope, "autostart", false) { autostart = it }
            observe(viewModelScope, "auto_connect", false) { autoConnect = it }
            observe(viewModelScope, "byedpi_proxy_ip", "127.0.0.1") { proxyIp = it }
            observe(viewModelScope, "byedpi_proxy_port", "1080") { proxyPort = it }
        }
        viewModelScope.launch {
            launch { appPrefs.modeFlow.collectLatest { mode = it } }
            launch { appPrefs.cmdEnableFlow.collectLatest { cmdEnable = it } }
            launch { appPrefs.trafficMonitoringFlow.collectLatest { trafficMonitoring = it } }
            launch { themeManager.isDynamicColor.collectLatest { dynamicColors = it } }
        }
    }

    fun updateLanguage(newValue: String) {
        appPrefs.language = newValue
        SettingsUtils.setLang(newValue)
    }

    fun updateTheme(newValue: String) {
        appPrefs.theme = newValue
        themeManager.setDarkTheme(newValue)
    }

    fun updateColorScheme(newValue: String) {
        appPrefs.colorScheme = newValue
        themeManager.setColorScheme(newValue)
    }

    fun updateDynamicColors(newValue: Boolean) {
        themeManager.setDynamicColor(newValue)
    }

    fun updateMode(newValue: String) {
        val newMode = Mode.fromString(newValue)
        appPrefs.mode = newMode
    }

    fun updateDns(newValue: String) {
        appPrefs.dnsIp = newValue
    }

    fun updateDnsSolution(newValue: String) {
        appPrefs.dnsSolution = newValue
        if (newValue != "custom") {
            updateDns(newValue)
        }
    }

    fun updateIpv6(newValue: Boolean) {
        appPrefs.ipv6Enable = newValue
    }

    fun updateApplistType(newValue: String) {
        appPrefs.applistType = newValue
    }

    fun updateAutostart(newValue: Boolean) {
        appPrefs.autostart = newValue
    }

    fun updateAutoConnect(newValue: Boolean) {
        appPrefs.autoConnect = newValue
    }

    fun updateCmdEnable(newValue: Boolean) {
        appPrefs.cmdEnable = newValue
    }

    fun updateProxyIp(newValue: String) {
        appPrefs.proxyIp = newValue
    }

    fun updateProxyPort(newValue: String) {
        appPrefs.proxyPort = newValue
    }

    fun updateTrafficMonitoring(newValue: Boolean) {
        appPrefs.trafficMonitoring = newValue
    }

    fun refreshBatteryOptimizationStatus() {
        isBatteryOptimizationEnabled = getApplication<Application>().isBatteryOptimizationEnabled()
    }

    fun refreshStorageAccessStatus() {
        hasStorageAccess = getApplication<Application>().hasStorageAccess()
    }
}
