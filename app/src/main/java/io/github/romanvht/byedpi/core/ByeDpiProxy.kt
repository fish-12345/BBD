package io.github.romanvht.byedpi.core

class ByeDpiProxy {
    companion object {
        init {
            System.loadLibrary("byedpi")
        }

        @JvmStatic external fun jniReadLogs(): String?
        @JvmStatic external fun jniForceClose(): Int
        @JvmStatic private external fun jniStartProxy(args: Array<String>): Int
        @JvmStatic private external fun jniStopProxy(): Int
    }

    fun startProxy(preferences: ByeDpiProxyPreferences): Int =
        jniStartProxy(prepareArgs(preferences))

    fun stopProxy(): Int = jniStopProxy()

    private fun prepareArgs(preferences: ByeDpiProxyPreferences): Array<String> =
        when (preferences) {
            is ByeDpiProxyCmdPreferences -> preferences.args
            is ByeDpiProxyUIPreferences -> preferences.uiargs
        }
}