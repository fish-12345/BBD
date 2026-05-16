package io.github.romanvht.byedpi.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.romanvht.byedpi.data.UISettings
import io.github.romanvht.byedpi.utility.UIPreferences
import io.github.romanvht.byedpi.utility.getDataStore

class UISettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStore = application.getDataStore()
    private val uiPrefs = UIPreferences(dataStore)

    var maxConnections by mutableStateOf(uiPrefs.maxConnections)
        private set
    var bufferSize by mutableStateOf(uiPrefs.bufferSize)
        private set
    var noDomain by mutableStateOf(uiPrefs.noDomain)
        private set
    var tcpFastOpen by mutableStateOf(uiPrefs.tcpFastOpen)
        private set

    var desyncMethod by mutableStateOf(
        UISettings.DesyncMethod.fromName(uiPrefs.desyncMethod)
    )
        private set

    var hostsMode by mutableStateOf(
        UISettings.HostsMode.fromName(uiPrefs.hostsMode)
    )
        private set

    var hostsBlacklist by mutableStateOf(
        uiPrefs.hostsBlacklist.split(",").filter { it.isNotBlank() }
    )
        private set
    var hostsWhitelist by mutableStateOf(
        uiPrefs.hostsWhitelist.split(",").filter { it.isNotBlank() }
    )
        private set
    var defaultTtl by mutableStateOf(uiPrefs.defaultTtl)
        private set
    var splitPosition by mutableStateOf(uiPrefs.splitPosition)
        private set
    var splitAtHost by mutableStateOf(uiPrefs.splitAtHost)
        private set
    var dropSack by mutableStateOf(uiPrefs.dropSack)
        private set
    var fakeTtl by mutableStateOf(uiPrefs.fakeTtl)
        private set
    var fakeOffset by mutableStateOf(uiPrefs.fakeOffset)
        private set
    var fakeSni by mutableStateOf(uiPrefs.fakeSni)
        private set
    var oobData by mutableStateOf(uiPrefs.oobData)
        private set

    var desyncHttp by mutableStateOf(uiPrefs.desyncHttp)
        private set
    var desyncHttps by mutableStateOf(uiPrefs.desyncHttps)
        private set
    var desyncUdp by mutableStateOf(uiPrefs.desyncUdp)
        private set

    var hostMixedCase by mutableStateOf(uiPrefs.hostMixedCase)
        private set
    var domainMixedCase by mutableStateOf(uiPrefs.domainMixedCase)
        private set
    var hostRemoveSpaces by mutableStateOf(uiPrefs.hostRemoveSpaces)
        private set

    var tlsRecEnabled by mutableStateOf(uiPrefs.tlsRecEnabled)
        private set
    var tlsRecPosition by mutableStateOf(uiPrefs.tlsRecPosition)
        private set
    var tlsRecAtSni by mutableStateOf(uiPrefs.tlsRecAtSni)
        private set
    var udpFakeCount by mutableStateOf(uiPrefs.udpFakeCount)
        private set

    init {
        dataStore.run {
            observe(viewModelScope, "byedpi_max_connections", "512") { maxConnections = it }
            observe(viewModelScope, "byedpi_buffer_size", "16384") { bufferSize = it }
            observe(viewModelScope, "byedpi_no_domain", false) { noDomain = it }
            observe(viewModelScope, "byedpi_tcp_fast_open", false) { tcpFastOpen = it }
            observe(viewModelScope, "byedpi_desync_method", "oob") { desyncMethod = UISettings.DesyncMethod.fromName(it) }
            observe(viewModelScope, "byedpi_hosts_mode", "disable") { hostsMode = UISettings.HostsMode.fromName(it) }
            observe(viewModelScope, "byedpi_hosts_blacklist", "") { hostsBlacklist = it.split(",").filter { s -> s.isNotBlank() } }
            observe(viewModelScope, "byedpi_hosts_whitelist", "") { hostsWhitelist = it.split(",").filter { s -> s.isNotBlank() } }
            observe(viewModelScope, "byedpi_default_ttl", "0") { defaultTtl = it }
            observe(viewModelScope, "byedpi_split_position", "1") { splitPosition = it }
            observe(viewModelScope, "byedpi_split_at_host", false) { splitAtHost = it }
            observe(viewModelScope, "byedpi_drop_sack", false) { dropSack = it }
            observe(viewModelScope, "byedpi_fake_ttl", "8") { fakeTtl = it }
            observe(viewModelScope, "byedpi_fake_offset", "0") { fakeOffset = it }
            observe(viewModelScope, "byedpi_fake_sni", "www.iana.org") { fakeSni = it }
            observe(viewModelScope, "byedpi_oob_data", "a") { oobData = it }
            observe(viewModelScope, "byedpi_desync_http", true) { desyncHttp = it }
            observe(viewModelScope, "byedpi_desync_https", true) { desyncHttps = it }
            observe(viewModelScope, "byedpi_desync_udp", true) { desyncUdp = it }
            observe(viewModelScope, "byedpi_host_mixed_case", false) { hostMixedCase = it }
            observe(viewModelScope, "byedpi_domain_mixed_case", false) { domainMixedCase = it }
            observe(viewModelScope, "byedpi_host_remove_spaces", false) { hostRemoveSpaces = it }
            observe(viewModelScope, "byedpi_tlsrec_enabled", false) { tlsRecEnabled = it }
            observe(viewModelScope, "byedpi_tlsrec_position", "0") { tlsRecPosition = it }
            observe(viewModelScope, "byedpi_tlsrec_at_sni", false) { tlsRecAtSni = it }
            observe(viewModelScope, "byedpi_udp_fake_count", "1") { udpFakeCount = it }
        }
    }

    fun updateMaxConnections(value: String) {
        uiPrefs.maxConnections = value
    }

    fun updateBufferSize(value: String) {
        uiPrefs.bufferSize = value
    }

    fun updateNoDomain(value: Boolean) {
        uiPrefs.noDomain = value
    }

    fun updateTcpFastOpen(value: Boolean) {
        uiPrefs.tcpFastOpen = value
    }

    fun updateHostsMode(value: String) {
        uiPrefs.hostsMode = value
    }

    fun updateHostsBlacklist(value: List<String>) {
        val stringValue = value.joinToString(",")
        uiPrefs.hostsBlacklist = stringValue
    }

    fun updateHostsWhitelist(value: List<String>) {
        val stringValue = value.joinToString(",")
        uiPrefs.hostsWhitelist = stringValue
    }

    fun updateDefaultTtl(value: String) {
        uiPrefs.defaultTtl = value
    }

    fun updateDesyncMethod(value: String) {
        uiPrefs.desyncMethod = value
    }

    fun updateSplitPosition(value: String) {
        uiPrefs.splitPosition = value
    }

    fun updateSplitAtHost(value: Boolean) {
        uiPrefs.splitAtHost = value
    }

    fun updateDropSack(value: Boolean) {
        uiPrefs.dropSack = value
    }

    fun updateFakeTtl(value: String) {
        uiPrefs.fakeTtl = value
    }

    fun updateFakeOffset(value: String) {
        uiPrefs.fakeOffset = value
    }

    fun updateFakeSni(value: String) {
        uiPrefs.fakeSni = value
    }

    fun updateOobData(value: String) {
        if (value.length <= 1) {
            uiPrefs.oobData = value
        }
    }

    fun updateDesyncHttp(value: Boolean) {
        uiPrefs.desyncHttp = value
    }

    fun updateDesyncHttps(value: Boolean) {
        uiPrefs.desyncHttps = value
    }

    fun updateDesyncUdp(value: Boolean) {
        uiPrefs.desyncUdp = value
    }

    fun updateHostMixedCase(value: Boolean) {
        uiPrefs.hostMixedCase = value
    }

    fun updateDomainMixedCase(value: Boolean) {
        uiPrefs.domainMixedCase = value
    }

    fun updateHostRemoveSpaces(value: Boolean) {
        uiPrefs.hostRemoveSpaces = value
    }

    fun updateTlsRecEnabled(value: Boolean) {
        uiPrefs.tlsRecEnabled = value
    }

    fun updateTlsRecPosition(value: String) {
        uiPrefs.tlsRecPosition = value
    }

    fun updateTlsRecAtSni(value: Boolean) {
        uiPrefs.tlsRecAtSni = value
    }

    fun updateUdpFakeCount(value: String) {
        uiPrefs.udpFakeCount = value
    }
}
