package io.github.romanvht.byedpi.core

object TProxyService {
    external fun TProxyStartService(configPath: String, fd: Int)
    external fun TProxyStopService()
    external fun TProxyIsRunning(): Boolean
    external fun TProxyGetStats(): LongArray

    init {
        System.loadLibrary("hev-socks5-tunnel")
    }
}