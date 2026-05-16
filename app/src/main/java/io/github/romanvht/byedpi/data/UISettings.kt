package io.github.romanvht.byedpi.data

import io.github.romanvht.byedpi.utility.DataStoreManager

data class UISettings(
    val ip: String = "127.0.0.1",
    val port: Int = 1080,
    val maxConnections: Int = 512,
    val bufferSize: Int = 16384,
    val defaultTtl: Int = 0,
    val noDomain: Boolean = false,
    val desyncHttp: Boolean = true,
    val desyncHttps: Boolean = true,
    val desyncUdp: Boolean = true,
    val desyncMethod: DesyncMethod = DesyncMethod.OOB,
    val splitPosition: Int = 1,
    val splitAtHost: Boolean = false,
    val fakeTtl: Int = 8,
    val fakeSni: String = "www.iana.org",
    val oobChar: String = "a",
    val hostMixedCase: Boolean = false,
    val domainMixedCase: Boolean = false,
    val hostRemoveSpaces: Boolean = false,
    val tlsRecordSplit: Boolean = false,
    val tlsRecordSplitPosition: Int = 0,
    val tlsRecordSplitAtSni: Boolean = false,
    val hostsMode: HostsMode = HostsMode.Disable,
    val hosts: String? = null,
    val tcpFastOpen: Boolean = false,
    val udpFakeCount: Int = 1,
    val dropSack: Boolean = false,
    val fakeOffset: Int = 0,
) {
    enum class DesyncMethod {
        None, Split, Disorder, Fake, OOB, DISOOB;

        companion object {
            fun fromName(name: String): DesyncMethod = when (name) {
                "none" -> None
                "split" -> Split
                "disorder" -> Disorder
                "fake" -> Fake
                "oob" -> OOB
                "disoob" -> DISOOB
                else -> throw IllegalArgumentException("Unknown desync method: $name")
            }
        }
    }

    enum class HostsMode {
        Disable, Blacklist, Whitelist;

        companion object {
            fun fromName(name: String): HostsMode = when (name) {
                "disable" -> Disable
                "blacklist" -> Blacklist
                "whitelist" -> Whitelist
                else -> throw IllegalArgumentException("Unknown hosts mode: $name")
            }
        }
    }

    companion object {
        fun fromDataStore(dataStore: DataStoreManager): UISettings {
            val hostsMode = dataStore.get("byedpi_hosts_mode", "disable").let { HostsMode.fromName(it) }

            val hosts = when (hostsMode) {
                HostsMode.Blacklist -> dataStore.get("byedpi_hosts_blacklist", "")
                HostsMode.Whitelist -> dataStore.get("byedpi_hosts_whitelist", "")
                else -> null
            }

            return UISettings(
                ip = dataStore.get("byedpi_proxy_ip", "127.0.0.1"),
                port = dataStore.get("byedpi_proxy_port", "1080").toIntOrNull() ?: 1080,
                maxConnections = dataStore.get("byedpi_max_connections", "512").toIntOrNull() ?: 512,
                bufferSize = dataStore.get("byedpi_buffer_size", "16384").toIntOrNull() ?: 16384,
                defaultTtl = dataStore.get("byedpi_default_ttl", "0").toIntOrNull() ?: 0,
                noDomain = dataStore.get("byedpi_no_domain", false),
                desyncHttp = dataStore.get("byedpi_desync_http", true),
                desyncHttps = dataStore.get("byedpi_desync_https", true),
                desyncUdp = dataStore.get("byedpi_desync_udp", true),
                desyncMethod = dataStore.get("byedpi_desync_method", "oob").let { DesyncMethod.fromName(it) },
                splitPosition = dataStore.get("byedpi_split_position", "1").toIntOrNull() ?: 1,
                splitAtHost = dataStore.get("byedpi_split_at_host", false),
                fakeTtl = dataStore.get("byedpi_fake_ttl", "8").toIntOrNull() ?: 8,
                fakeSni = dataStore.get("byedpi_fake_sni", "www.iana.org"),
                oobChar = dataStore.get("byedpi_oob_data", "a"),
                hostMixedCase = dataStore.get("byedpi_host_mixed_case", false),
                domainMixedCase = dataStore.get("byedpi_domain_mixed_case", false),
                hostRemoveSpaces = dataStore.get("byedpi_host_remove_spaces", false),
                tlsRecordSplit = dataStore.get("byedpi_tlsrec_enabled", false),
                tlsRecordSplitPosition = dataStore.get("byedpi_tlsrec_position", "0").toIntOrNull() ?: 0,
                tlsRecordSplitAtSni = dataStore.get("byedpi_tlsrec_at_sni", false),
                tcpFastOpen = dataStore.get("byedpi_tcp_fast_open", false),
                udpFakeCount = dataStore.get("byedpi_udp_fake_count", "1").toIntOrNull() ?: 1,
                dropSack = dataStore.get("byedpi_drop_sack", false),
                fakeOffset = dataStore.get("byedpi_fake_offset", "0").toIntOrNull() ?: 0,
                hostsMode = hostsMode,
                hosts = hosts,
            )
        }
    }
}
