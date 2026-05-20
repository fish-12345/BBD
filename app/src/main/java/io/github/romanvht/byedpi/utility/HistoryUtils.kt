package io.github.romanvht.byedpi.utility

import android.content.Context
import io.github.romanvht.byedpi.data.Command
import com.google.gson.Gson

class HistoryUtils(private val context: Context) {

    private val dataStore = context.applicationContext.getDataStore()
    private val maxHistorySize = 40

    fun addCommand(command: String) {
        if (command.isBlank()) return

        val history = getHistory().toMutableList()
        val unpinned = history.filter { !it.pinned }
        val search = history.find { it.text == command }

        if (search == null) {
            history.add(0, Command(command))
            if (history.size > maxHistorySize) {
                if (unpinned.isNotEmpty()) {
                    history.remove(unpinned.last())
                }
            }
        }

        saveHistory(history)
    }

    fun pinCommand(command: String) {
        val history = getHistory().toMutableList()
        history.find { it.text == command }?.pinned = true
        saveHistory(history)
    }

    fun unpinCommand(command: String) {
        val history = getHistory().toMutableList()
        history.find { it.text == command }?.pinned = false
        saveHistory(history)
    }

    fun deleteCommand(command: String) {
        val history = getHistory().toMutableList()
        history.removeAll { it.text == command }
        saveHistory(history)
    }

    fun renameCommand(command: String, newName: String) {
        val history = getHistory().toMutableList()
        history.find { it.text == command }?.name = newName
        saveHistory(history)
    }

    fun editCommand(command: String, newText: String) {
        val history = getHistory().toMutableList()
        history.find { it.text == command }?.text = newText
        saveHistory(history)
    }

    fun findCommandByText(text: String): Command? {
        if (text.isBlank()) return null
        return getHistory().find { it.text == text }
    }

    fun getHistory(): List<Command> {
        val historyJson = dataStore.get("byedpi_command_history", "")
        return if (historyJson.isNotBlank()) {
            try {
                Gson().fromJson(historyJson, Array<Command>::class.java).toList()
            } catch (_: Exception) {
                emptyList()
            }
        } else {
            emptyList()
        }
    }

    fun saveHistory(history: List<Command>) {
        val historyJson = Gson().toJson(history)
        dataStore.setAsync("byedpi_command_history", historyJson)
        ShortcutUtils.update(context)
    }

    fun clearAllHistory() {
        saveHistory(emptyList())
    }

    fun clearUnpinnedHistory() {
        val history = getHistory().filter { it.pinned }
        saveHistory(history)
    }
}
