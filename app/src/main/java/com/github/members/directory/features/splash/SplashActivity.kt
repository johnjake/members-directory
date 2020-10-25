package com.github.members.directory.features.splash

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.members.directory.R
import com.github.members.directory.ext.isOnline
import com.github.members.directory.features.main.MainActivity
import com.github.members.directory.utils.alertDialog.ListenerCallBack
import com.github.members.directory.utils.alertDialog.VelloAlertDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        when(this.isOnline(this)) {
           true -> {
               scope.launch {
                   delay(5000)
                   launchActivity()
               }
           }
           false -> verifyConnection()
       }
    }

    private fun verifyConnection() {
        val alertDialog = VelloAlertDialog()
        alertDialog.alertInitialize(
                this,
                "No Internet",
                "Would you like to switch to offline?",
                Typeface.SANS_SERIF,
                Typeface.DEFAULT_BOLD,
                isCancelable = true,
                isNegativeBtnHide = false)
        alertDialog.setPositive("YES", object : ListenerCallBack {
            override fun onClick(dialog: VelloAlertDialog) {
                dialog.dismiss()
            }
        })
        alertDialog.setNegative("NO", object : ListenerCallBack {
            override fun onClick(dialog: VelloAlertDialog) {
                dialog.dismiss()
                exitGithubApp()
            }
        })
        alertDialog.show()
    }

    private fun exitGithubApp() {
        finishAndRemoveTask()
    }

    private fun launchActivity() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            putExtra("INTERNET", "1")
        })
    }
}