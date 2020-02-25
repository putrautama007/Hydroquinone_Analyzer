package com.putra.hydroquinoneanalyzer.presenter

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.putra.hydroquinoneanalyzer.view.SplashScreenView

class SplashScreenPresenter(private val view : SplashScreenView) {

    fun startAnimation(context: Context){
        val thread: Thread
        thread = object : Thread() {
            override fun run() {
                try {
                    var waited = 0
                    while (waited < 3500) {
                        sleep(100)
                        waited += 100
                    }
                    view.startAnimation(context)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        thread.start()
    }

    fun hasPermissions(context: Context, vararg permissions: String): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }
}