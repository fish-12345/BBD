package io.github.dovecoteescapee.byedpi.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.dovecoteescapee.byedpi.utility.TestPreferences
import io.github.dovecoteescapee.byedpi.utility.getPreferences
import io.github.dovecoteescapee.byedpi.utility.DomainListUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestSettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val testPrefs = TestPreferences(application.getPreferences())

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

    var domainsList by mutableStateOf(testPrefs.domains.split("\n").filter { it.isNotBlank() })
        private set

    var strategyLists by mutableStateOf(testPrefs.strategyLists)
        private set

    var commandsList by mutableStateOf(testPrefs.commands.split("\n").filter { it.isNotBlank() })
        private set

    var concurrentRequests by mutableStateOf(testPrefs.concurrentRequests)
        private set

    init {
        updateDomainListsSummary()
    }

    fun updateDelay(newValue: String) {
        testPrefs.delay = newValue
        delay = newValue
    }

    fun updateRequests(newValue: String) {
        testPrefs.requests = newValue
        requests = newValue
    }

    fun updateTimeout(newValue: String) {
        testPrefs.timeout = newValue
        timeout = newValue
    }

    fun updateSni(newValue: String) {
        testPrefs.sni = newValue
        sni = newValue
    }

    fun updateFullLog(newValue: Boolean) {
        testPrefs.fullLog = newValue
        fullLog = newValue
    }

    fun updateLogClickable(newValue: Boolean) {
        testPrefs.logClickable = newValue
        logClickable = newValue
    }

    fun updateAutoSort(newValue: Boolean) {
        testPrefs.autoSort = newValue
        autoSort = newValue
    }

    fun updateShowAll(newValue: Boolean) {
        testPrefs.showAll = newValue
        showAll = newValue
    }

    fun updateDomainListsSummary() {
        viewModelScope.launch(Dispatchers.IO) {
            DomainListUtils.initializeDefaultLists(getApplication())
            val activeLists = DomainListUtils.getLists(getApplication()).filter { it.isActive }

            // Вычисляем строку в фоновом потоке
            val summaryText = if (activeLists.isEmpty()) {
                "No lists selected"
            } else {
                activeLists.joinToString(", ") { it.name }
            }

            // ПУБЛИКУЕМ результат в Главном потоке (Main)
            withContext(Dispatchers.Main) {
                domainListsSummary = summaryText
            }
        }
    }


    fun updateStrategyLists(newValue: Set<String>) {
        testPrefs.strategyLists = newValue
        strategyLists = newValue
    }

    fun updateCommandsList(newList: List<String>) {
        commandsList = newList
        testPrefs.commands = newList.joinToString("\n")
    }

    fun updateConcurrentRequests(newValue: String) {
        testPrefs.concurrentRequests = newValue
        concurrentRequests = newValue
    }
}