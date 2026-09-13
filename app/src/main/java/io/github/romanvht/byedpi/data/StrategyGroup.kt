package io.github.romanvht.byedpi.data

data class StrategyGroup(
    val id: String,
    val name: String,
    val domains: String, // Comma or newline separated
    val strategy: String
)
