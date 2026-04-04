package io.github.romanvht.byedpi.utility

import java.util.regex.Pattern

private val DOMAIN_NAME_PATTERN = Pattern.compile(
    "^(?:[a-z0-9](?:[a-z0-9-]{0,61}[a-z0-9])?\\.)+[a-z0-9][a-z0-9-]{0,61}[a-z0-9]$",
    Pattern.CASE_INSENSITIVE
)

fun checkDomain(domain: String): Boolean {
    if (domain.isEmpty()) return false
    if (domain.length > 253) return false
    
    // Remove protocol if present for validation
    val cleanDomain = domain.removePrefix("https://").removePrefix("http://").split("/")[0].split(":")[0]
    
    return DOMAIN_NAME_PATTERN.matcher(cleanDomain).matches()
}
