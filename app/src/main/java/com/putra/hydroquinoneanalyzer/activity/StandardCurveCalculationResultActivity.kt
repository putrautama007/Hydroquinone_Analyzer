package com.putra.hydroquinoneanalyzer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.putra.hydroquinoneanalyzer.R

class StandardCurveCalculationResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standard_curve_calculation_result)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}