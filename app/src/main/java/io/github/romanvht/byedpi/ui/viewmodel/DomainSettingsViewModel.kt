package io.github.romanvht.byedpi.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.romanvht.byedpi.data.StrategyGroup
import io.github.romanvht.byedpi.utility.AppPreferences
import io.github.romanvht.byedpi.utility.getDataStore
import java.util.UUID

class DomainSettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStore = application.getDataStore()
    private val appPrefs = AppPreferences(dataStore)
    private val gson = Gson()
    private val groupsType = object : TypeToken<List<StrategyGroup>>() {}.type

    var groups = mutableStateListOf<StrategyGroup>()
        private set

    init {
        dataStore.observe(viewModelScope, "byedpi_domain_strategies", "[]") { json ->
            val list: List<StrategyGroup> = try {
                gson.fromJson(json, groupsType) ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
            groups.clear()
            groups.addAll(list)
        }
    }

    private fun saveGroups() {
        appPrefs.domainStrategiesJson = gson.toJson(groups.toList())
    }

    fun addGroup(name: String, domains: String, strategy: String) {
        groups.add(StrategyGroup(id = UUID.randomUUID().toString(), name = name, domains = domains, strategy = strategy))
        saveGroups()
    }

    fun updateGroup(id: String, name: String, domains: String, strategy: String) {
        val index = groups.indexOfFirst { it.id == id }
        if (index != -1) {
            groups[index] = StrategyGroup(id, name, domains, strategy)
            saveGroups()
        }
    }

    fun deleteGroup(id: String) {
        groups.removeAll { it.id == id }
        saveGroups()
    }
}