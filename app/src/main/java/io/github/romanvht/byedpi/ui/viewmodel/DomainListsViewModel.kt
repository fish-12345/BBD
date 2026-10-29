package io.github.romanvht.byedpi.ui.viewmodel

import android.app.Application
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.romanvht.byedpi.R
import io.github.romanvht.byedpi.data.DomainList
import io.github.romanvht.byedpi.utility.DomainListUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Списки доменов для проверки стратегий.
 *
 * Встроенные списки читаются из assets (`proxytest_*.sites`), свои можно
 * добавлять и редактировать. Активные списки попадают в тест стратегий.
 */
class DomainListsViewModel(application: Application) : AndroidViewModel(application) {

    var domainLists by mutableStateOf<List<DomainList>>(emptyList())
        private set

    var showAddDialog by mutableStateOf(false)
        private set

    var showEditDialog by mutableStateOf(false)
        private set

    var editingList by mutableStateOf<DomainList?>(null)
        private set

    var showActionDialog by mutableStateOf(false)
        private set

    var selectedList by mutableStateOf<DomainList?>(null)
        private set

    private val _toastEvent = MutableSharedFlow<Int>()
    val toastEvent = _toastEvent.asSharedFlow()

    init {
        loadDomainLists()
    }

    private fun showToast(@StringRes messageRes: Int) {
        viewModelScope.launch {
            _toastEvent.emit(messageRes)
        }
    }

    fun loadDomainLists() {
        viewModelScope.launch(Dispatchers.IO) {
            DomainListUtils.syncLists(getApplication())
            val lists = DomainListUtils.getLists(getApplication())

            val userLists = lists.filter { !it.isBuiltIn }
                .sortedByDescending { it.name }
            val builtInLists = lists.filter { it.isBuiltIn }
                .sortedBy { it.name.lowercase() }
            val sortedLists = userLists + builtInLists

            withContext(Dispatchers.Main) {
                domainLists = sortedLists
            }
        }
    }

    fun toggleListActive(domainList: DomainList) {
        viewModelScope.launch(Dispatchers.IO) {
            DomainListUtils.toggleListActive(getApplication(), domainList.id)
            loadDomainLists()
        }
    }

    fun showAddDialogAction() {
        showAddDialog = true
    }

    fun hideAddDialog() {
        showAddDialog = false
    }

    fun showEditDialogAction(domainList: DomainList) {
        editingList = domainList
        showEditDialog = true
    }

    fun hideEditDialog() {
        showEditDialog = false
        editingList = null
    }

    fun showActionDialogAction(domainList: DomainList) {
        selectedList = domainList
        showActionDialog = true
    }

    fun hideActionDialog() {
        showActionDialog = false
        selectedList = null
    }

    fun addList(name: String, domains: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            if (name.isBlank()) {
                showToast(R.string.domain_list_error_name)
                return@launch
            }

            if (domains.isEmpty()) {
                showToast(R.string.domain_list_error_domains)
                return@launch
            }

            if (DomainListUtils.addList(getApplication(), name, domains)) {
                loadDomainLists()
                withContext(Dispatchers.Main) {
                    hideAddDialog()
                }
                showToast(R.string.domain_list_added)
            } else {
                showToast(R.string.domain_list_exists)
            }
        }
    }

    fun updateList(id: String, name: String, domains: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            if (name.isBlank()) {
                showToast(R.string.domain_list_error_name)
                return@launch
            }

            if (domains.isEmpty()) {
                showToast(R.string.domain_list_error_domains)
                return@launch
            }

            if (DomainListUtils.updateList(getApplication(), id, name, domains)) {
                loadDomainLists()
                withContext(Dispatchers.Main) {
                    hideEditDialog()
                }
                showToast(R.string.domain_list_updated)
            } else {
                showToast(R.string.domain_list_update_failed)
            }
        }
    }

    fun deleteList(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            DomainListUtils.deleteList(getApplication(), id)
            loadDomainLists()
            showToast(R.string.domain_list_deleted)
        }
    }

    fun copyDomainsToClipboard(domainList: DomainList): String {
        return domainList.domains.joinToString("\n")
    }
}
