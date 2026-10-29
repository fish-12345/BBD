package io.github.romanvht.byedpi.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import io.github.romanvht.byedpi.utility.AppPreferences
import io.github.romanvht.byedpi.utility.getDataStore

/**
 * Фильтры страницы подключений. Отдельный ViewModel, чтобы настройки
 * не зависели от жизненного цикла живого списка соединений.
 */
class ConnectionsFiltersViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = AppPreferences(application.getDataStore())

    var hideClosed by mutableStateOf(prefs.connectionsHideClosed)
        private set

    var hideErrors by mutableStateOf(prefs.connectionsHideErrors)
        private set

    var hideDns by mutableStateOf(prefs.connectionsHideDns)
        private set

    var hideUnnamed by mutableStateOf(prefs.connectionsHideUnnamed)
        private set

    var hideTcp by mutableStateOf(prefs.connectionsHideTcp)
        private set

    var hideUdp by mutableStateOf(prefs.connectionsHideUdp)
        private set

    var hideUnknownKind by mutableStateOf(prefs.connectionsHideUnknownKind)
        private set

    var hiddenKinds by mutableStateOf(parseHiddenKinds(prefs.connectionsHiddenKinds))
        private set

    val activeFilterCount: Int
        get() = listOf(
            hideClosed, hideErrors, hideDns, hideUnnamed,
            hideTcp, hideUdp, hideUnknownKind
        ).count { it } + hiddenKinds.size

    fun updateHideClosed(value: Boolean) {
        hideClosed = value
        prefs.connectionsHideClosed = value
    }

    fun updateHideErrors(value: Boolean) {
        hideErrors = value
        prefs.connectionsHideErrors = value
    }

    fun updateHideDns(value: Boolean) {
        hideDns = value
        prefs.connectionsHideDns = value
    }

    fun updateHideUnnamed(value: Boolean) {
        hideUnnamed = value
        prefs.connectionsHideUnnamed = value
    }

    fun updateHideTcp(value: Boolean) {
        hideTcp = value
        prefs.connectionsHideTcp = value
    }

    fun updateHideUdp(value: Boolean) {
        hideUdp = value
        prefs.connectionsHideUdp = value
    }

    fun updateHideUnknownKind(value: Boolean) {
        hideUnknownKind = value
        prefs.connectionsHideUnknownKind = value
    }

    fun toggleKind(kind: ConnectionKind) {
        if (kind == ConnectionKind.Unknown) return
        val next = if (kind in hiddenKinds) hiddenKinds - kind else hiddenKinds + kind
        hiddenKinds = next
        prefs.connectionsHiddenKinds = encodeHiddenKinds(next)
    }

    fun reset() {
        hideClosed = false
        hideErrors = false
        hideDns = false
        hideUnnamed = false
        hideTcp = false
        hideUdp = false
        hideUnknownKind = false
        hiddenKinds = emptySet()

        prefs.connectionsHideClosed = false
        prefs.connectionsHideErrors = false
        prefs.connectionsHideDns = false
        prefs.connectionsHideUnnamed = false
        prefs.connectionsHideTcp = false
        prefs.connectionsHideUdp = false
        prefs.connectionsHideUnknownKind = false
        prefs.connectionsHiddenKinds = ""
    }
}
