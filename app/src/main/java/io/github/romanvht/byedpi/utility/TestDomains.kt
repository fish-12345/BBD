package io.github.romanvht.byedpi.utility

import android.content.Context

/**
 * Встроенные наборы доменов из assets (`proxytest_*.sites`).
 *
 * Раньше ими управлял отдельный экран «Списки доменов»; теперь набор
 * фиксированный: тест проверяет домены из всех встроенных файлов.
 */
object TestDomains {
    private const val PREFIX = "proxytest_"
    private const val SUFFIX = ".sites"

    private fun assetFiles(context: Context): List<String> =
        context.assets.list("")
            ?.filter { it.startsWith(PREFIX) && it.endsWith(SUFFIX) }
            ?.sorted()
            ?: emptyList()

    /** Имена встроенных наборов: youtube, googlevideo, discord, social, … */
    fun names(context: Context): List<String> =
        assetFiles(context).map { it.removePrefix(PREFIX).removeSuffix(SUFFIX) }

    /** Домены одного набора или null, если такого набора нет. */
    fun read(context: Context, name: String): List<String>? {
        val file = assetFiles(context).firstOrNull {
            it.removePrefix(PREFIX).removeSuffix(SUFFIX).equals(name, ignoreCase = true)
        } ?: return null

        return context.assets.open(file).bufferedReader().useLines { lines ->
            lines.map { it.trim() }.filter { it.isNotEmpty() }.toList()
        }
    }

    /** Все домены из всех встроенных наборов без повторов. */
    fun all(context: Context): List<String> =
        names(context).flatMap { read(context, it).orEmpty() }.distinct()
}
