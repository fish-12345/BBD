package io.github.romanvht.byedpi.activities

import android.content.Intent
import android.net.VpnService
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import io.github.romanvht.byedpi.data.AppStatus
import io.github.romanvht.byedpi.data.Mode
import io.github.romanvht.byedpi.services.ServiceManager
import io.github.romanvht.byedpi.services.appStatus
import io.github.romanvht.byedpi.utility.getDataStore

class ToggleActivity : ComponentActivity() {
    private val vpnRegister =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                ServiceManager.start(this, Mode.VPN)
            }
            finish()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        val strategy = intent?.getStringExtra("strategy")
        val modeStr = intent?.getStringExtra("mode")
        val dataStore = getDataStore()
        var updated = false

        if (strategy != null && strategy != dataStore.get("byedpi_cmd_args", "")) {
            dataStore.setAsync("byedpi_cmd_args", strategy)
            updated = true
        }

        if (modeStr != null) {
            val newMode = try { Mode.fromString(modeStr) } catch (e: Exception) { null }
            if (newMode != null) {
                val currentModeStr = dataStore.get("byedpi_mode", "vpn")
                if (newMode != Mode.fromString(currentModeStr)) {
                    dataStore.setAsync("byedpi_mode", newMode.toString().lowercase())
                    updated = true
                }
            }
        }

        val onlyUpdate = intent?.getBooleanExtra("only_update", false) ?: false
        val onlyStart = intent?.getBooleanExtra("only_start", false) ?: false
        val onlyStop = intent?.getBooleanExtra("only_stop", false) ?: false

        val (status, _) = appStatus

        when {
            onlyUpdate -> { /* already updated above */ }
            onlyStart -> if (status == AppStatus.Halted) start()
            onlyStop -> if (status == AppStatus.Running) stop()
            else -> {
                if (status == AppStatus.Halted) {
                    start()
                } else {
                    if (updated) {
                        val currentModeStr = dataStore.get("byedpi_mode", "vpn")
                        ServiceManager.restart(this, Mode.fromString(currentModeStr))
                    } else {
                        stop()
                    }
                }
            }
        }

        if (intent?.getBooleanExtra("finish_after", true) != false) {
            val currentModeStr = dataStore.get("byedpi_mode", "vpn")
            val mode = Mode.fromString(currentModeStr)
            if (status == AppStatus.Halted && !onlyStop && !onlyUpdate && mode == Mode.VPN && VpnService.prepare(this) != null) {
                return
            }
            finish()
        }
    }

    private fun start() {
        val currentModeStr = getDataStore().get("byedpi_mode", "vpn")
        val mode = Mode.fromString(currentModeStr)
        if (mode == Mode.VPN) {
            val intentPrepare = VpnService.prepare(this)
            if (intentPrepare != null) {
                vpnRegister.launch(intentPrepare)
            } else {
                ServiceManager.start(this, Mode.VPN)
            }
        } else {
            ServiceManager.start(this, Mode.Proxy)
        }
    }

    private fun stop() {
        ServiceManager.stop(this)
    }
}
