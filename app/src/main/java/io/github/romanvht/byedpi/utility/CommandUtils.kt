package io.github.romanvht.byedpi.utility

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.romanvht.byedpi.data.StrategyGroup

object CommandUtils {
    private val gson = Gson()

    fun assembleFullCommand(baseCmd: String, groupsJson: String): String {
        val type = object : TypeToken<List<StrategyGroup>>() {}.type
        val groups: List<StrategyGroup> = try {
            gson.fromJson(groupsJson, type) ?: emptyList()
        } catch (_: Exception) {
            emptyList()
        }

        val activeGroups = groups.filter { it.domains.isNotBlank() && it.strategy.isNotBlank() }
        if (activeGroups.isEmpty()) return baseCmd

        val prefix = buildString {
            activeGroups.forEach { group ->
                val domainList = group.domains.lines().filter { it.isNotBlank() }.joinToString(" ")
                append("${group.strategy} -H :$domainList -A n ")
            }
        }

        val trimmedBase = baseCmd.trim()
        val trimmedPrefix = prefix.trim()

        return if (trimmedBase.startsWith(trimmedPrefix)) {
            trimmedBase
        } else {
            (prefix + baseCmd).trim()
        }
    }
}
