package com.putra.hydroquinoneanalyzer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.putra.hydroquinoneanalyzer.R
import kotlinx.android.synthetic.main.activity_standard_curve.*
import org.jetbrains.anko.startActivity

class StandardCurveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standard_curve)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        fabStandardCurve.setOnClickListener {
            startActivity<StandardCurveCalculationActivity>()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}