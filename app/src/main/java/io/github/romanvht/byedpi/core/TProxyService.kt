package io.github.romanvht.byedpi.core

object TProxyService {
    external fun TProxyStartService(configPath: String, fd: Int): Boolean
    external fun TProxyStopService(): Boolean

    init {
        System.loadLibrary("hev-socks5-tunnel")
    }
}