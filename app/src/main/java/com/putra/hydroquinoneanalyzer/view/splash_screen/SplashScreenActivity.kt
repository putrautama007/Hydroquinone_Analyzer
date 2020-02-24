package com.putra.hydroquinoneanalyzer.view.splash_screen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.putra.hydroquinoneanalyzer.R
import com.putra.hydroquinoneanalyzer.presenter.SplashScreenPresenter
import com.putra.hydroquinoneanalyzer.view.MainActivity

class SplashScreenActivity : AppCompatActivity(),SplashScreenView {
    lateinit var splashScreenPresenter: SplashScreenPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        splashScreenPresenter = SplashScreenPresenter(this@SplashScreenActivity)
        splashScreenPresenter.startAnimation(this@SplashScreenActivity)
    }

    override fun startAnimation(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        startActivity(intent)
        finish()
    }
}
