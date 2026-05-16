package io.github.romanvht.byedpi.ui.viewmodel

import android.app.Application
import android.content.pm.ApplicationInfo
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.romanvht.byedpi.data.AppInfo
import io.github.romanvht.byedpi.utility.getDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppSelectionViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext
    private val dataStore = context.getDataStore()
    private val pm = context.packageManager

    var apps by mutableStateOf<List<AppInfo>>(emptyList())
        private set
    var searchQuery by mutableStateOf("")
    var isLoading by mutableStateOf(true)
        private set
    var showSelectedOnly by mutableStateOf(false)
    var showSystemApps by mutableStateOf(false)

    init {
        loadApps()
    }

    fun loadApps() {
        viewModelScope.launch {
            isLoading = true
            apps = withContext(Dispatchers.IO) {
                val installedApps = pm.getInstalledApplications(0)
                val selectedApps = dataStore.get("selected_apps", setOf<String>())

                installedApps
                    .filter { it.packageName != context.packageName }
                    .map { appInfo ->
                        async {
                            val appName = try {
                                pm.getApplicationLabel(appInfo).toString()
                            } catch (_: Exception) {
                                appInfo.packageName
                            }
                            val isSystem = (appInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0
                            AppInfo(
                                appName,
                                appInfo.packageName,
                                selectedApps.contains(appInfo.packageName),
                                isSystem
                            )
                        }
                    }
                    .awaitAll()
                    .sortedWith(compareBy({ !it.isSelected }, { it.appName.lowercase() }))
            }
            isLoading = false
        }
    }

    fun toggleAppSelection(app: AppInfo, isChecked: Boolean) {
        viewModelScope.launch {
            val currentSelected = dataStore.get("selected_apps", setOf<String>()).toMutableSet()
            if (isChecked) currentSelected.add(app.packageName)
            else currentSelected.remove(app.packageName)
            dataStore.set("selected_apps", currentSelected)

            apps = apps.map {
                if (it.packageName == app.packageName) it.copy(isSelected = isChecked)
                else it
            }
        }
    }

    fun clearSelection() {
        dataStore.removeAsync("selected_apps", setOf<String>())
        apps = apps.map { it.copy(isSelected = false) }
    }

    val filteredApps: List<AppInfo>
        get() {
            var filtered = if (searchQuery.isEmpty()) apps
            else apps.filter { it.appName.contains(searchQuery.trim(), ignoreCase = true) || it.packageName.contains(searchQuery.trim(), ignoreCase = true) }
            
            if (showSelectedOnly) {
                filtered = filtered.filter { it.isSelected }
            }
            
            if (!showSystemApps) {
                filtered = filtered.filter { !it.isSystem || it.isSelected }
            }
            
            return filtered
        }
}
