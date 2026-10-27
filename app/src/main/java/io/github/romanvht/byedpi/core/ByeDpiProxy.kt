package io.github.romanvht.byedpi.core

class ByeDpiProxy {
    companion object {
        init {
            System.loadLibrary("byedpi")
        }

        @JvmStatic external fun jniReadLogs(): String?
        /** Неразрушающее чтение лога: "<всего байт>\n<хвост лога>". */
        @JvmStatic external fun jniPeekLogs(): String?
        /** Снимок таблицы соединений (см. conn_stats.h). */
        @JvmStatic external fun jniConnStats(): String?
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
