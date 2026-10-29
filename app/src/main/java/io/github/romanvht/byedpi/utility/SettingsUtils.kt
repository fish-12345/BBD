package io.github.romanvht.byedpi.utility

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.google.gson.Gson
import io.github.romanvht.byedpi.BuildConfig
import io.github.romanvht.byedpi.R
import io.github.romanvht.byedpi.data.AppSettings
import kotlinx.coroutines.runBlocking

object SettingsUtils {
    private const val TAG = "SettingsUtils"

    fun setLang(lang: String) {
        val appLocale = localeByName(lang) ?: LocaleListCompat.getEmptyLocaleList()

        if (AppCompatDelegate.getApplicationLocales()
                .toLanguageTags() != appLocale.toLanguageTags()
        ) {
            AppCompatDelegate.setApplicationLocales(appLocale)
        }
    }

    private fun localeByName(lang: String): LocaleListCompat? = when (lang) {
        "system" -> LocaleListCompat.getEmptyLocaleList()
        "ru" -> LocaleListCompat.forLanguageTags("ru")
        "en" -> LocaleListCompat.forLanguageTags("en")
        "tr" -> LocaleListCompat.forLanguageTags("tr")
        "kk" -> LocaleListCompat.forLanguageTags("kk")
        else -> {
            Log.w(TAG, "Invalid value for language: $lang")
            null
        }
    }

    fun setTheme(name: String) {
        val appTheme = themeByName(name) ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM

        if (AppCompatDelegate.getDefaultNightMode() != appTheme) {
            AppCompatDelegate.setDefaultNightMode(appTheme)
        }
    }

    private fun themeByName(name: String): Int? = when (name) {
        "system" -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        "light" -> AppCompatDelegate.MODE_NIGHT_NO
        "dark" -> AppCompatDelegate.MODE_NIGHT_YES
        else -> {
            Log.w(TAG, "Invalid value for app_theme: $name")
            null
        }
    }

    fun exportSettings(context: Context, uri: Uri) {
        try {
            val dataStore = context.getDataStore()
            val history = HistoryUtils(context).getHistory()
            val apps = dataStore.get("selected_apps", emptySet<String>()).toList()
            val domainLists = DomainListUtils.getAllLists(context)

            val importantKeys = listOf(
                "language",
                "app_theme",
                "color_scheme",
                "byedpi_mode",
                "dns_ip",
                "dns_solution",
                "ipv6_enable",
                "applist_type",
                "autostart",
                "auto_connect",
                "byedpi_enable_cmd_settings",
                "byedpi_proxy_ip",
                "byedpi_proxy_port",
                "byedpi_cmd_args",
                "byedpi_wifi_profile",
                "byedpi_mobile_profile",
                "traffic_monitoring",
                "byedpi_proxytest_delay",
                "byedpi_proxytest_requests",
                "byedpi_proxytest_timeout",
                "byedpi_proxytest_sni",
                "byedpi_proxytest_fulllog",
                "byedpi_proxytest_logclickable",
                "byedpi_proxytest_autosort",
                "byedpi_proxytest_showall",
                "byedpi_max_connections",
                "byedpi_buffer_size",
                "byedpi_no_domain",
                "byedpi_tcp_fast_open",
                "byedpi_desync_method",
                "byedpi_hosts_mode",
                "byedpi_default_ttl",
                "byedpi_split_position",
                "byedpi_split_at_host",
                "byedpi_drop_sack",
                "byedpi_fake_ttl",
                "byedpi_fake_offset",
                "byedpi_fake_sni",
                "byedpi_oob_data",
                "byedpi_desync_http",
                "byedpi_desync_https",
                "byedpi_desync_udp",
                "byedpi_host_mixed_case",
                "byedpi_domain_mixed_case",
                "byedpi_host_remove_spaces",
                "byedpi_tlsrec_enabled",
                "byedpi_tlsrec_position",
                "byedpi_tlsrec_at_sni",
                "byedpi_udp_fake_count"
            )

            val settingsMap = mutableMapOf<String, Any>()
            importantKeys.forEach { keyName ->
                dataStore.getNullable(keyName)?.let { value ->
                    settingsMap[keyName] = value
                }
            }

            val export = AppSettings(
                app = BuildConfig.APPLICATION_ID,
                version = BuildConfig.VERSION_NAME,
                history = history,
                apps = apps,
                domainLists = domainLists,
                settings = settingsMap
            )

            val json = Gson().toJson(export)

            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                outputStream.write(json.toByteArray())
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to export settings", e)
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, "Failed to export settings", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun importSettings(context: Context, uri: Uri, onRestart: () -> Unit) {
        try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val json = inputStream.bufferedReader().readText()
                val import = try {
                    Gson().fromJson(json, AppSettings::class.java)
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to import settings", e)
                    null
                }

                if (import == null || import.app != BuildConfig.APPLICATION_ID) {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(context, R.string.logs_failed, Toast.LENGTH_LONG).show()
                    }
                    return@use
                }

                val dataStore = context.getDataStore()
                runBlocking {
                    dataStore.clear()
                    import.settings.forEach { (key, value) ->
                        if (value != null) {
                            dataStore.set(key, value)
                        }
                    }

                    if (import.apps != null) {
                        dataStore.set("selected_apps", import.apps.toSet())
                    }
                }

                if (import.history != null) {
                    HistoryUtils(context).saveHistory(import.history)
                }

                if (import.domainLists != null) {
                    val normalized = import.domainLists.map {
                        if (it.isBuiltIn) {
                            it.copy(
                                isModified = it.isModified,
                                isDeleted = it.isDeleted
                            )
                        } else {
                            it.copy(
                                isModified = false,
                                isDeleted = false
                            )
                        }
                    }

                    DomainListUtils.saveLists(context, normalized)
                }

                val newLang = dataStore.get("language", "system")
                val newTheme = dataStore.get("app_theme", "system")
                setLang(newLang)
                setTheme(newTheme)

                Handler(Looper.getMainLooper()).post {
                    onRestart()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to import settings", e)
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, "Failed to import settings", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun resetSettings(context: Context, onRestart: () -> Unit) {
        try {
            val dataStore = context.getDataStore()

            runBlocking {
                dataStore.clear()
            }

            HistoryUtils(context).saveHistory(emptyList())
            DomainListUtils.resetLists(context)

            setLang("system")
            setTheme("system")

            Handler(Looper.getMainLooper()).post {
                onRestart()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to reset settings", e)
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, "Failed to reset settings", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
