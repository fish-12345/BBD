package io.github.romanvht.byedpi.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.romanvht.byedpi.utility.AppPreferences
import io.github.romanvht.byedpi.utility.HistoryUtils
import io.github.romanvht.byedpi.utility.getDataStore

class CmdSettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStore = application.getDataStore()
    private val appPrefs = AppPreferences(dataStore)
    private val historyUtils = HistoryUtils(application)

    var cmdArgs by mutableStateOf(appPrefs.cmdArgs)
        private set
    var history by mutableStateOf(historyUtils.getHistory())
        private set

    init {
        dataStore.run {
            observe(viewModelScope, "byedpi_cmd_args", "") { cmdArgs = it }
            observe(viewModelScope, "byedpi_command_history", "") { refreshHistory() }
        }
    }

    fun updateCmdArgs(newValue: String) {
        if (newValue.isNotBlank()) {
            historyUtils.addCommand(newValue)
            refreshHistory()
        }
        cmdArgs = newValue
        appPrefs.cmdArgs = newValue
        historyUtils.saveHistory(historyUtils.getHistory())
    }

    fun clearCmdArgs() {
        cmdArgs = ""
        appPrefs.cmdArgs = ""
    }

    fun clearUnpinnedHistory() {
        historyUtils.clearUnpinnedHistory()
        refreshHistory()
    }

    fun clearAllHistory() {
        historyUtils.clearAllHistory()
        refreshHistory()
    }

    fun pinCommand(command: String) {
        historyUtils.addCommand(command)
        historyUtils.pinCommand(command)
        refreshHistory()
    }

    fun unpinCommand(command: String) {
        historyUtils.unpinCommand(command)
        refreshHistory()
    }

    fun renameCommand(command: String, newName: String) {
        historyUtils.renameCommand(command, newName)
        refreshHistory()
    }

    fun editCommand(oldText: String, newText: String) {
        if (newText.isNotBlank()) {
            historyUtils.editCommand(oldText, newText)
            if (cmdArgs == oldText) {
                cmdArgs = newText
                appPrefs.cmdArgs = newText
            }
            refreshHistory()
        }
    }

    fun deleteCommand(command: String) {
        historyUtils.deleteCommand(command)
        refreshHistory()
    }

    private fun refreshHistory() {
        history = historyUtils.getHistory()
    }
}
