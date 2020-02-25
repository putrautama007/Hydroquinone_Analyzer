package com.putra.hydroquinoneanalyzer.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.putra.hydroquinoneanalyzer.R
import com.putra.hydroquinoneanalyzer.presenter.SplashScreenPresenter
import com.putra.hydroquinoneanalyzer.view.SplashScreenView


class SplashScreenActivity : AppCompatActivity(),
    SplashScreenView {
    lateinit var splashScreenPresenter: SplashScreenPresenter

    companion object {
        const val PERMISSION_ALL = 1
        val PERMISSIONS = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        splashScreenPresenter = SplashScreenPresenter(this@SplashScreenActivity)
        if (!splashScreenPresenter.hasPermissions(this, *PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL)
        }
        splashScreenPresenter.startAnimation(this@SplashScreenActivity)
    }

    override fun startAnimation(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        startActivity(intent)
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ALL) {
            val perms: HashMap<String, Int> = HashMap()
            perms[Manifest.permission.READ_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
            perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
            perms[Manifest.permission.CAMERA] = PackageManager.PERMISSION_GRANTED

            for (i in permissions.indices) perms[permissions[i]] = grantResults[i]

            if (perms[Manifest.permission.READ_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
                && perms[Manifest.permission.ACCESS_COARSE_LOCATION] == PackageManager.PERMISSION_GRANTED
                && perms[Manifest.permission.ACCESS_FINE_LOCATION] == PackageManager.PERMISSION_GRANTED
                && perms[Manifest.permission.READ_SMS] == PackageManager.PERMISSION_GRANTED
            ) {
                splashScreenPresenter.startAnimation(this)
            } else { // Permission Denied
                Toast.makeText(this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                    .show()
                splashScreenPresenter.startAnimation(this)
            }
        }
    }
}
