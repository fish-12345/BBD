package io.github.romanvht.byedpi.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.romanvht.byedpi.utility.TestPreferences
import io.github.romanvht.byedpi.utility.getDataStore
import io.github.romanvht.byedpi.utility.DomainListUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestSettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStore = application.getDataStore()
    private val testPrefs = TestPreferences(dataStore)

    var delay by mutableStateOf(testPrefs.delay)
        private set

    var requests by mutableStateOf(testPrefs.requests)
        private set

    var timeout by mutableStateOf(testPrefs.timeout)
        private set

    var sni by mutableStateOf(testPrefs.sni)
        private set

    var fullLog by mutableStateOf(testPrefs.fullLog)
        private set

    var logClickable by mutableStateOf(testPrefs.logClickable)
        private set

    var autoSort by mutableStateOf(testPrefs.autoSort)
        private set

    var showAll by mutableStateOf(testPrefs.showAll)
        private set

    var domainListsSummary by mutableStateOf("No lists selected")
        private set

    var strategyLists by mutableStateOf(testPrefs.strategyLists)
        private set

    var commandsList by mutableStateOf(testPrefs.commands.split("\n").filter { it.isNotBlank() })
        private set

    var concurrentRequests by mutableStateOf(testPrefs.concurrentRequests)
        private set

    init {
        updateDomainListsSummary()
        dataStore.run {
            observe(viewModelScope, "byedpi_proxytest_delay", "6") { delay = it }
            observe(viewModelScope, "byedpi_proxytest_requests", "1") { requests = it }
            observe(viewModelScope, "byedpi_proxytest_timeout", "5") { timeout = it }
            observe(viewModelScope, "byedpi_proxytest_sni", "max.ru") { sni = it }
            observe(viewModelScope, "byedpi_proxytest_fulllog", false) { fullLog = it }
            observe(viewModelScope, "byedpi_proxytest_logclickable", false) { logClickable = it }
            observe(viewModelScope, "byedpi_proxytest_autosort", true) { autoSort = it }
            observe(viewModelScope, "byedpi_proxytest_showall", false) { showAll = it }
            observe(viewModelScope, "byedpi_proxytest_strategy_lists", setOf("builtin")) { strategyLists = it }
            observe(viewModelScope, "byedpi_proxytest_commands", "") { commandsList = it.split("\n").filter { s -> s.isNotBlank() } }
            observe(viewModelScope, "byedpi_proxytest_concurrent_requests", "20") { concurrentRequests = it }
        }
    }

    fun updateDelay(newValue: String) {
        testPrefs.delay = newValue
    }

    fun updateRequests(newValue: String) {
        testPrefs.requests = newValue
    }

    fun updateTimeout(newValue: String) {
        testPrefs.timeout = newValue
    }

    fun updateSni(newValue: String) {
        testPrefs.sni = newValue
    }

    fun updateFullLog(newValue: Boolean) {
        testPrefs.fullLog = newValue
    }

    fun updateLogClickable(newValue: Boolean) {
        testPrefs.logClickable = newValue
    }

    fun updateAutoSort(newValue: Boolean) {
        testPrefs.autoSort = newValue
    }

    fun updateShowAll(newValue: Boolean) {
        testPrefs.showAll = newValue
    }

    fun updateDomainListsSummary() {
        viewModelScope.launch(Dispatchers.IO) {
            DomainListUtils.syncLists(getApplication())
            val activeLists = DomainListUtils.getLists(getApplication()).filter { it.isActive }

            val summaryText = if (activeLists.isEmpty()) {
                "No lists selected"
            } else {
                activeLists.joinToString(", ") { it.name }
            }

            withContext(Dispatchers.Main) {
                domainListsSummary = summaryText
            }
        }
    }


    fun updateStrategyLists(newValue: Set<String>) {
        testPrefs.strategyLists = newValue
    }

    fun updateCommandsList(newList: List<String>) {
        testPrefs.commands = newList.joinToString("\n")
    }

    fun updateConcurrentRequests(newValue: String) {
        testPrefs.concurrentRequests = newValue
    }
}
