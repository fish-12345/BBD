package io.github.romanvht.byedpi.data

import com.google.gson.annotations.SerializedName

data class AppSettings(
    @SerializedName("app") val app: String,
    @SerializedName("version") val version: String,
    @SerializedName("history") val history: List<Command>?,
    @SerializedName("apps") val apps: List<String>?,
    @SerializedName("domainLists") val domainLists: List<DomainList>?,
    @SerializedName("settings") val settings: Map<String, Any?>
)
