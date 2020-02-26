package com.putra.hydroquinoneanalyzer.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.putra.hydroquinoneanalyzer.R
import com.putra.hydroquinoneanalyzer.model.BitMapConverter
import com.putra.hydroquinoneanalyzer.model.CalculationConcentration
import com.putra.hydroquinoneanalyzer.model.ScanModel
import com.putra.hydroquinoneanalyzer.presenter.ScanResultPresenter
import com.putra.hydroquinoneanalyzer.room.ScanDataDatabase
import com.putra.hydroquinoneanalyzer.utils.DecimalFormat.Companion.decimalFormat
import com.putra.hydroquinoneanalyzer.view.ScanResultView
import kotlinx.android.synthetic.main.activity_scan_result.*
import java.text.DecimalFormat

class ScanResultActivity : AppCompatActivity(),View.OnClickListener,ScanResultView {

    private lateinit var scanResultPresenter: ScanResultPresenter
    private lateinit var scanDataDatabase: ScanDataDatabase
    private lateinit var rgb: IntArray
    private lateinit var calculationConcentration :CalculationConcentration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_result)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        scanDataDatabase = ScanDataDatabase.getInstance(this)!!
        scanResultPresenter = ScanResultPresenter(this)
        scanResultPresenter.initView()
        btnSaveResult.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when(p0){
            btnSaveResult ->{
                if (etSampleName.text.toString() != "") {
                    val scanModel = ScanModel(
                        System.currentTimeMillis(),
                        etSampleName.text.toString(),
                        rgb[0],
                        rgb[1],
                        rgb[2],
                        calculationConcentration.concentrationCalculation(),
                        calculationConcentration.concentrationPercentage(),
                        calculationConcentration.checkStatus()
                    )
                    scanResultPresenter.saveData(scanModel,scanDataDatabase)
                } else {
                    Toast.makeText(this, "Isi nama sample", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        btnSaveResult.setOnClickListener(this)
        val bitmap = intent.getParcelableExtra<Bitmap>("result")!!
        val bitMapConverter = BitMapConverter(bitmap)
        rgb= bitMapConverter.getAverageColorRGB()
        tvRGBResult.text = "${resources.getString(R.string.RGB)} (${rgb[0]} , ${rgb[1]} , ${rgb[2]})"

        llColor.setBackgroundColor(Color.rgb(rgb[0], rgb[1], rgb[2]))

        calculationConcentration =
            CalculationConcentration(rgb[2].toDouble())
        tvConcentration.text =
            resources.getString(R.string.konsentrasi) + decimalFormat.format(
                calculationConcentration.concentrationCalculation()
            ) + resources.getString(R.string.concentration_unit)
        tvHQLevel.text = resources.getString(R.string.tingkat_hq) + decimalFormat.format(
            calculationConcentration.concentrationPercentage()
        ) + resources.getString(R.string.percent)
        tvStatus.text =
            resources.getString(R.string.status) + calculationConcentration.checkStatus()
    }

    override fun intentToMain() {
        val intentToMain = Intent(this, MainActivity::class.java)
        intentToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intentToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intentToMain)
    }

}
