package io.github.dovecoteescapee.byedpi.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.dovecoteescapee.byedpi.data.DomainList
import io.github.dovecoteescapee.byedpi.utility.DomainListUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    // Поток для передачи сообщений (Toast) в UI
    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    init {
        loadDomainLists()
    }

    private fun showToast(message: String) {
        viewModelScope.launch {
            _toastEvent.emit(message)
        }
    }

    fun loadDomainLists() {
        viewModelScope.launch(Dispatchers.IO) {
            DomainListUtils.initializeDefaultLists(getApplication())
            val lists = DomainListUtils.getLists(getApplication())

            val sortedLists = lists.sortedWith(
                compareByDescending<DomainList> { it.isActive }
                    .thenBy { it.name.lowercase() }
            )

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
            if (name.isEmpty()) {
                showToast("Name cannot be empty")
                return@launch
            }

            if (domains.isEmpty()) {
                showToast("Domains cannot be empty")
                return@launch
            }

            if (DomainListUtils.addList(getApplication(), name, domains)) {
                loadDomainLists()
                withContext(Dispatchers.Main) {
                    hideAddDialog()
                }
                showToast("Domain list added")
            } else {
                showToast("List with this name already exists")
            }
        }
    }

    fun updateList(id: String, name: String, domains: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            if (name.isEmpty()) {
                showToast("Name cannot be empty")
                return@launch
            }

            if (domains.isEmpty()) {
                showToast("Domains cannot be empty")
                return@launch
            }

            if (DomainListUtils.updateList(getApplication(), id, name, domains)) {
                loadDomainLists()
                withContext(Dispatchers.Main) {
                    hideEditDialog()
                }
                showToast("Domain list updated")
            } else {
                showToast("Failed to update list")
            }
        }
    }

    fun deleteList(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            DomainListUtils.deleteList(getApplication(), id)
            loadDomainLists()
            // Мы не вызываем showToast здесь, так как Toast вызывается в Screen при клике,
            // но можно добавить и сюда для надежности.
        }
    }

    fun copyDomainsToClipboard(domainList: DomainList): String {
        return domainList.domains.joinToString("\n")
    }
}