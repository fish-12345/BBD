package io.github.romanvht.byedpi.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.VpnService
import android.os.SystemClock
import io.github.romanvht.byedpi.data.Mode
import io.github.romanvht.byedpi.services.ServiceManager
import io.github.romanvht.byedpi.utility.getDataStore

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == Intent.ACTION_REBOOT ||
            intent.action == "android.intent.action.QUICKBOOT_POWERON") {

            // for A15, todo: use wasForceStopped
            if (SystemClock.elapsedRealtime() > 5 * 60 * 1000) {
                return
            }

            val dataStore = context.getDataStore()
            val autorunEnabled = dataStore.get("autostart", false)

            if (autorunEnabled) {
                val modeStr = dataStore.get("byedpi_mode", "vpn")
                val mode = Mode.fromString(modeStr)
                
                when (mode) {
                    Mode.VPN -> {
                        if (VpnService.prepare(context) == null) {
                            ServiceManager.start(context, Mode.VPN)
                        }
                    }

                    Mode.Proxy -> ServiceManager.start(context, Mode.Proxy)
                }
            }
        }
    }
}
