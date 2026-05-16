package io.github.romanvht.byedpi.utility

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings",
    produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, context.packageName + "_preferences"))
    }
)

class DataStoreManager(private val context: Context) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val dataStore = context.dataStore

    // Cache for instant reading in non-suspend contexts
    private var _cachedPreferences = runBlocking { dataStore.data.first() }

    init {
        scope.launch {
            dataStore.data.collect {
                _cachedPreferences = it
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getFlow(keyName: String, defaultValue: T): Flow<T> {
        val key = when (defaultValue) {
            is String -> stringPreferencesKey(keyName)
            is Int -> intPreferencesKey(keyName)
            is Boolean -> booleanPreferencesKey(keyName)
            is Long -> longPreferencesKey(keyName)
            is Float -> floatPreferencesKey(keyName)
            is Double -> doublePreferencesKey(keyName)
            is Set<*> -> stringSetPreferencesKey(keyName)
            else -> throw IllegalArgumentException("Unsupported type")
        } as Preferences.Key<T>
        return dataStore.data.map { it[key] ?: defaultValue }
    }

    fun getNullable(keyName: String): Any? {
        val keys = listOf(
            stringPreferencesKey(keyName),
            intPreferencesKey(keyName),
            booleanPreferencesKey(keyName),
            longPreferencesKey(keyName),
            floatPreferencesKey(keyName),
            doublePreferencesKey(keyName),
            stringSetPreferencesKey(keyName)
        )
        for (key in keys) {
            val value = _cachedPreferences[key]
            if (value != null) return value
        }
        return null
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(keyName: String, defaultValue: T): T {
        val key = when (defaultValue) {
            is String -> stringPreferencesKey(keyName)
            is Int -> intPreferencesKey(keyName)
            is Boolean -> booleanPreferencesKey(keyName)
            is Long -> longPreferencesKey(keyName)
            is Float -> floatPreferencesKey(keyName)
            is Double -> doublePreferencesKey(keyName)
            is Set<*> -> stringSetPreferencesKey(keyName)
            else -> throw IllegalArgumentException("Unsupported type")
        } as Preferences.Key<T>
        return _cachedPreferences[key] ?: defaultValue
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun set(keyName: String, value: Any) {
        val key = when (value) {
            is String -> stringPreferencesKey(keyName)
            is Int -> intPreferencesKey(keyName)
            is Boolean -> booleanPreferencesKey(keyName)
            is Long -> longPreferencesKey(keyName)
            is Float -> floatPreferencesKey(keyName)
            is Double -> doublePreferencesKey(keyName)
            is Set<*> -> stringSetPreferencesKey(keyName)
            else -> throw IllegalArgumentException("Unsupported type")
        } as Preferences.Key<Any>
        dataStore.edit { it[key] = value }
    }

    fun setAsync(keyName: String, value: Any) {
        scope.launch {
            set(keyName, value)
        }
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun <T> remove(keyName: String, typeDefault: T) {
        val key = when (typeDefault) {
            is String -> stringPreferencesKey(keyName)
            is Int -> intPreferencesKey(keyName)
            is Boolean -> booleanPreferencesKey(keyName)
            is Long -> longPreferencesKey(keyName)
            is Float -> floatPreferencesKey(keyName)
            is Double -> doublePreferencesKey(keyName)
            is Set<*> -> stringSetPreferencesKey(keyName)
            else -> throw IllegalArgumentException("Unsupported type")
        } as Preferences.Key<T>
        dataStore.edit { it.remove(key) }
    }

    fun <T> removeAsync(keyName: String, typeDefault: T) {
        scope.launch {
            remove(keyName, typeDefault)
        }
    }

    suspend fun clear() {
        dataStore.edit { it.clear() }
    }

    fun <T> observe(
        scope: CoroutineScope,
        keyName: String,
        defaultValue: T,
        onUpdate: (T) -> Unit
    ) {
        scope.launch {
            getFlow(keyName, defaultValue).collectLatest { onUpdate(it) }
        }
    }
}
