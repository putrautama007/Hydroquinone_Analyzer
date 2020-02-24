package com.putra.hydroquinoneanalyzer.presenter

import android.content.Context
import com.putra.hydroquinoneanalyzer.view.splash_screen.SplashScreenView

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
}