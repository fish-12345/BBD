package io.github.romanvht.byedpi.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.romanvht.byedpi.data.UISettings
import io.github.romanvht.byedpi.utility.UIPreferences
import io.github.romanvht.byedpi.utility.getDataStore

class UISettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStore = application.getDataStore()
    private val uiPrefs = UIPreferences(dataStore)

    val maxConnections by dataStore.composeState(viewModelScope, "byedpi_max_connections", "512")
    val bufferSize by dataStore.composeState(viewModelScope, "byedpi_buffer_size", "16384")
    val noDomain by dataStore.composeState(viewModelScope, "byedpi_no_domain", false)
    val tcpFastOpen by dataStore.composeState(viewModelScope, "byedpi_tcp_fast_open", false)

    private val desyncMethodRaw by dataStore.composeState(viewModelScope, "byedpi_desync_method", "oob")
    val desyncMethod: UISettings.DesyncMethod get() = UISettings.DesyncMethod.fromName(desyncMethodRaw)

    private val hostsModeRaw by dataStore.composeState(viewModelScope, "byedpi_hosts_mode", "disable")
    val hostsMode: UISettings.HostsMode get() = UISettings.HostsMode.fromName(hostsModeRaw)

    private val hostsBlacklistRaw by dataStore.composeState(viewModelScope, "byedpi_hosts_blacklist", "")
    val hostsBlacklist: List<String> get() = hostsBlacklistRaw.split(",").filter { it.isNotBlank() }

    private val hostsWhitelistRaw by dataStore.composeState(viewModelScope, "byedpi_hosts_whitelist", "")
    val hostsWhitelist: List<String> get() = hostsWhitelistRaw.split(",").filter { it.isNotBlank() }

    val defaultTtl by dataStore.composeState(viewModelScope, "byedpi_default_ttl", "0")
    val splitPosition by dataStore.composeState(viewModelScope, "byedpi_split_position", "1")
    val splitAtHost by dataStore.composeState(viewModelScope, "byedpi_split_at_host", false)
    val dropSack by dataStore.composeState(viewModelScope, "byedpi_drop_sack", false)
    val fakeTtl by dataStore.composeState(viewModelScope, "byedpi_fake_ttl", "8")
    val fakeOffset by dataStore.composeState(viewModelScope, "byedpi_fake_offset", "0")
    val fakeSni by dataStore.composeState(viewModelScope, "byedpi_fake_sni", "www.iana.org")
    val oobData by dataStore.composeState(viewModelScope, "byedpi_oob_data", "a")

    val desyncHttp by dataStore.composeState(viewModelScope, "byedpi_desync_http", true)
    val desyncHttps by dataStore.composeState(viewModelScope, "byedpi_desync_https", true)
    val desyncUdp by dataStore.composeState(viewModelScope, "byedpi_desync_udp", true)

    val hostMixedCase by dataStore.composeState(viewModelScope, "byedpi_host_mixed_case", false)
    val domainMixedCase by dataStore.composeState(viewModelScope, "byedpi_domain_mixed_case", false)
    val hostRemoveSpaces by dataStore.composeState(viewModelScope, "byedpi_host_remove_spaces", false)

    val tlsRecEnabled by dataStore.composeState(viewModelScope, "byedpi_tlsrec_enabled", false)
    val tlsRecPosition by dataStore.composeState(viewModelScope, "byedpi_tlsrec_position", "0")
    val tlsRecAtSni by dataStore.composeState(viewModelScope, "byedpi_tlsrec_at_sni", false)
    val udpFakeCount by dataStore.composeState(viewModelScope, "byedpi_udp_fake_count", "1")

    fun updateMaxConnections(value: String) { uiPrefs.maxConnections = value }
    fun updateBufferSize(value: String) { uiPrefs.bufferSize = value }
    fun updateNoDomain(value: Boolean) { uiPrefs.noDomain = value }
    fun updateTcpFastOpen(value: Boolean) { uiPrefs.tcpFastOpen = value }
    fun updateDesyncMethod(value: String) { uiPrefs.desyncMethod = value }
    fun updateHostsMode(value: String) { uiPrefs.hostsMode = value }

    fun updateHostsBlacklist(value: List<String>) { uiPrefs.hostsBlacklist = value.joinToString(",") }
    fun updateHostsWhitelist(value: List<String>) { uiPrefs.hostsWhitelist = value.joinToString(",") }

    fun updateDefaultTtl(value: String) { uiPrefs.defaultTtl = value }
    fun updateSplitPosition(value: String) { uiPrefs.splitPosition = value }
    fun updateSplitAtHost(value: Boolean) { uiPrefs.splitAtHost = value }
    fun updateDropSack(value: Boolean) { uiPrefs.dropSack = value }
    fun updateFakeTtl(value: String) { uiPrefs.fakeTtl = value }
    fun updateFakeOffset(value: String) { uiPrefs.fakeOffset = value }
    fun updateFakeSni(value: String) { uiPrefs.fakeSni = value }

    fun updateOobData(value: String) {
        if (value.length <= 1) uiPrefs.oobData = value
    }

    fun updateDesyncHttp(value: Boolean) { uiPrefs.desyncHttp = value }
    fun updateDesyncHttps(value: Boolean) { uiPrefs.desyncHttps = value }
    fun updateDesyncUdp(value: Boolean) { uiPrefs.desyncUdp = value }

    fun updateHostMixedCase(value: Boolean) { uiPrefs.hostMixedCase = value }
    fun updateDomainMixedCase(value: Boolean) { uiPrefs.domainMixedCase = value }
    fun updateHostRemoveSpaces(value: Boolean) { uiPrefs.hostRemoveSpaces = value }

    fun updateTlsRecEnabled(value: Boolean) { uiPrefs.tlsRecEnabled = value }
    fun updateTlsRecPosition(value: String) { uiPrefs.tlsRecPosition = value }
    fun updateTlsRecAtSni(value: Boolean) { uiPrefs.tlsRecAtSni = value }
    fun updateUdpFakeCount(value: String) { uiPrefs.udpFakeCount = value }
}