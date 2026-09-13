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

    // Простые независимые поля — через composeState, без ручного observe
    val language by dataStore.composeState(viewModelScope, "language", "system")
    val theme by dataStore.composeState(viewModelScope, "app_theme", "system")
    val colorScheme by dataStore.composeState(viewModelScope, "color_scheme", "Default")
    val dnsIp by dataStore.composeState(viewModelScope, "dns_ip", "1.1.1.1")
    val dnsSolution by dataStore.composeState(viewModelScope, "dns_solution", "1.1.1.1")
    val ipv6Enable by dataStore.composeState(viewModelScope, "ipv6_enable", false)
    val applistType by dataStore.composeState(viewModelScope, "applist_type", "disable")
    val autostart by dataStore.composeState(viewModelScope, "autostart", false)
    val autoConnect by dataStore.composeState(viewModelScope, "auto_connect", false)
    val proxyIp by dataStore.composeState(viewModelScope, "byedpi_proxy_ip", "127.0.0.1")
    val proxyPort by dataStore.composeState(viewModelScope, "byedpi_proxy_port", "1080")

    // Эти поля идут через кастомные Flow (маппинг типов / внешний источник), оставляем как раньше
    var mode by mutableStateOf(appPrefs.mode)
        private set
    var cmdEnable by mutableStateOf(appPrefs.cmdEnable)
        private set
    var trafficMonitoring by mutableStateOf(appPrefs.trafficMonitoring)
        private set
    var dynamicColors by mutableStateOf(themeManager.getDynamicColor())
        private set

    // Не из DataStore — читаются из системных API по требованию
    var isBatteryOptimizationEnabled by mutableStateOf(application.isBatteryOptimizationEnabled())
        private set
    var hasStorageAccess by mutableStateOf(application.hasStorageAccess())
        private set

    val isProxyVisible: Boolean
        get() {
            if (!appPrefs.cmdEnable) return true

            val cmdArgs = shellSplit(appPrefs.cmdArgs)
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
        appPrefs.mode = Mode.fromString(newValue)
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