package io.github.romanvht.byedpi.data

import android.content.Context
import android.os.Build
import io.github.romanvht.byedpi.utility.getDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeManager(context: Context) {
    private val dataStore = context.getDataStore()
    private val dynamicColorsKey = "dynamic_colors"

    val isDynamicColor: Flow<Boolean> = dataStore.getFlow(dynamicColorsKey, Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)

    val isDarkTheme: Flow<Boolean?> = dataStore.getFlow("app_theme", "system").map {
        when (it) {
            "dark" -> true
            "light" -> false
            else -> null
        }
    }

    val selectedColorScheme: Flow<String> = dataStore.getFlow("color_scheme", "Default")

    fun getDynamicColor(): Boolean = dataStore.get(dynamicColorsKey, Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)

    fun getDarkTheme(): Boolean? = when (dataStore.get("app_theme", "system")) {
        "dark" -> true
        "light" -> false
        else -> null
    }

    fun getSelectedColorScheme(): String = dataStore.get("color_scheme", "Default")

    fun setDynamicColor(enabled: Boolean) {
        dataStore.setAsync(dynamicColorsKey, enabled)
    }

    fun setDarkTheme(theme: String) {
        dataStore.setAsync("app_theme", theme)
    }

    fun setColorScheme(scheme: String) {
        dataStore.setAsync("color_scheme", scheme)
    }
}
