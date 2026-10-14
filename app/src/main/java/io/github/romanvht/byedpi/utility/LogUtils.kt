package io.github.romanvht.byedpi.utility

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import io.github.romanvht.byedpi.R
import io.github.romanvht.byedpi.core.ByeDpiProxy
import java.io.IOException

object LogUtils {
    private const val TAG = "LogUtils"

    /** Читает накопленный буфер логов из native-слоя и очищает его. */
    fun collectLogs(): String? =
        try {
            ByeDpiProxy.jniReadLogs()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to collect logs", e)
            null
        }

    fun saveLogs(context: Context, uri: Uri) {
        val logs = collectLogs()

        if (logs.isNullOrEmpty()) {
            showToast(context, R.string.logs_failed)
            return
        }

        try {
            context.contentResolver.openOutputStream(uri)?.use {
                it.write(logs.toByteArray())
            } ?: run {
                Log.e(TAG, "Failed to open output stream")
                showToast(context, R.string.logs_failed)
            }
        } catch (e: IOException) {
            Log.e(TAG, "Failed to save logs", e)
            showToast(context, R.string.logs_failed)
        }
    }

    private fun showToast(context: Context, resId: Int) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
        }
    }
}