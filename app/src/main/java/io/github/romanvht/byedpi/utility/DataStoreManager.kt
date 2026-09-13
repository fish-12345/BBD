package io.github.romanvht.byedpi.utility

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
import kotlin.reflect.KProperty

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings",
    produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, context.packageName + "_preferences"))
    }
)

class DataStoreManager(context: Context) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val dataStore = context.dataStore

    private var _cachedPreferences = runBlocking { dataStore.data.first() }

    init {
        scope.launch {
            dataStore.data.collect { _cachedPreferences = it }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> keyFor(keyName: String, defaultValue: T): Preferences.Key<T> = when (defaultValue) {
        is String -> stringPreferencesKey(keyName)
        is Int -> intPreferencesKey(keyName)
        is Boolean -> booleanPreferencesKey(keyName)
        is Long -> longPreferencesKey(keyName)
        is Float -> floatPreferencesKey(keyName)
        is Double -> doublePreferencesKey(keyName)
        is Set<*> -> stringSetPreferencesKey(keyName)
        else -> throw IllegalArgumentException("Unsupported type")
    } as Preferences.Key<T>

    fun getNullable(keyName: String): Any? {
        val keys = listOf(
            stringPreferencesKey(keyName), intPreferencesKey(keyName), booleanPreferencesKey(keyName),
            longPreferencesKey(keyName), floatPreferencesKey(keyName), doublePreferencesKey(keyName),
            stringSetPreferencesKey(keyName)
        )
        for (key in keys) {
            val value = _cachedPreferences[key]
            if (value != null) return value
        }
        return null
    }

    fun <T> get(keyName: String, defaultValue: T): T =
        _cachedPreferences[keyFor(keyName, defaultValue)] ?: defaultValue

    fun <T> getFlow(keyName: String, defaultValue: T): Flow<T> =
        dataStore.data.map { it[keyFor(keyName, defaultValue)] ?: defaultValue }

    suspend fun <T : Any> set(keyName: String, value: T) {
        dataStore.edit { it[keyFor(keyName, value)] = value }
    }

    fun <T : Any> setAsync(keyName: String, value: T) {
        scope.launch { set(keyName, value) }
    }

    suspend fun <T> remove(keyName: String, typeDefault: T) {
        dataStore.edit { it.remove(keyFor(keyName, typeDefault)) }
    }

    fun <T> removeAsync(keyName: String, typeDefault: T) {
        scope.launch { remove(keyName, typeDefault) }
    }

    suspend fun clear() {
        dataStore.edit { it.clear() }
    }

    fun <T> observe(scope: CoroutineScope, keyName: String, defaultValue: T, onUpdate: (T) -> Unit) {
        scope.launch {
            getFlow(keyName, defaultValue).collectLatest { onUpdate(it) }
        }
    }

    /** Готовый Compose State, который сам подписывается на изменения ключа. */
    fun <T> composeState(scope: CoroutineScope, keyName: String, defaultValue: T): State<T> {
        val state = mutableStateOf(get(keyName, defaultValue))
        scope.launch {
            getFlow(keyName, defaultValue).collectLatest { state.value = it }
        }
        return state
    }
}

/** Property-делегат: убирает шаблонные get()/set() в *Preferences классах. */
class Pref<T : Any>(
    private val dataStore: DataStoreManager,
    private val key: String,
    private val default: T
) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = dataStore.get(key, default)
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = dataStore.setAsync(key, value)
}

fun <T : Any> DataStoreManager.pref(key: String, default: T) = Pref(this, key, default)