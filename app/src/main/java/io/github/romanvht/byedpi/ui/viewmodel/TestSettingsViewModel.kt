package io.github.romanvht.byedpi.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.romanvht.byedpi.utility.TestPreferences
import io.github.romanvht.byedpi.utility.getDataStore

class TestSettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStore = application.getDataStore()
    private val testPrefs = TestPreferences(dataStore)

    val delay by dataStore.composeState(viewModelScope, "byedpi_proxytest_delay", "6")
    val requests by dataStore.composeState(viewModelScope, "byedpi_proxytest_requests", "1")
    val timeout by dataStore.composeState(viewModelScope, "byedpi_proxytest_timeout", "5")
    val sni by dataStore.composeState(viewModelScope, "byedpi_proxytest_sni", "max.ru")
    val fullLog by dataStore.composeState(viewModelScope, "byedpi_proxytest_fulllog", false)
    val logClickable by dataStore.composeState(viewModelScope, "byedpi_proxytest_logclickable", false)
    val autoSort by dataStore.composeState(viewModelScope, "byedpi_proxytest_autosort", true)
    val showAll by dataStore.composeState(viewModelScope, "byedpi_proxytest_showall", false)
    val strategyLists by dataStore.composeState(viewModelScope, "byedpi_proxytest_strategy_lists", setOf("builtin"))
    val concurrentRequests by dataStore.composeState(viewModelScope, "byedpi_proxytest_concurrent_requests", "20")

    private val commandsRaw by dataStore.composeState(viewModelScope, "byedpi_proxytest_commands", "")
    val commandsList: List<String> get() = commandsRaw.split("\n").filter { it.isNotBlank() }

    fun updateDelay(newValue: String) { testPrefs.delay = newValue }
    fun updateRequests(newValue: String) { testPrefs.requests = newValue }
    fun updateTimeout(newValue: String) { testPrefs.timeout = newValue }
    fun updateSni(newValue: String) { testPrefs.sni = newValue }
    fun updateFullLog(newValue: Boolean) { testPrefs.fullLog = newValue }
    fun updateLogClickable(newValue: Boolean) { testPrefs.logClickable = newValue }
    fun updateAutoSort(newValue: Boolean) { testPrefs.autoSort = newValue }
    fun updateShowAll(newValue: Boolean) { testPrefs.showAll = newValue }
    fun updateStrategyLists(newValue: Set<String>) { testPrefs.strategyLists = newValue }
    fun updateCommandsList(newList: List<String>) { testPrefs.commands = newList.joinToString("\n") }
    fun updateConcurrentRequests(newValue: String) { testPrefs.concurrentRequests = newValue }
}