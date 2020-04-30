package com.putra.hydroquinoneanalyzer.activity

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.putra.hydroquinoneanalyzer.R
import com.putra.hydroquinoneanalyzer.model.ScanModel
import com.putra.hydroquinoneanalyzer.presenter.ScanResultPresenter
import com.putra.hydroquinoneanalyzer.room.ScanDataDatabase
import com.putra.hydroquinoneanalyzer.utils.DecimalFormat.Companion.decimalFormat
import com.putra.hydroquinoneanalyzer.view.ScanResultView
import kotlinx.android.synthetic.main.activity_scan_result.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor

class ScanResultActivity : AppCompatActivity(),View.OnClickListener,ScanResultView {

    private lateinit var scanResultPresenter: ScanResultPresenter
    private lateinit var scanDataDatabase: ScanDataDatabase
    private lateinit var rgb: IntArray


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_result)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        scanDataDatabase = ScanDataDatabase.getInstance(this)!!
        scanResultPresenter = ScanResultPresenter(this)
        scanResultPresenter.initView()
        scanResultPresenter.calculationConcentration(rgb[2].toDouble())
        btnSaveResult.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        val scanModel = ScanModel(
            System.currentTimeMillis(),
            etSampleName.text.toString(),
            rgb[0],
            rgb[1],
            rgb[2],
            tvConcentration.text.toString(),
            tvHQLevel.text.toString(),
            tvStatus.text.toString()
        )
        if(view == btnSaveResult){
                if (etSampleName.text.toString() != "") {
                    scanResultPresenter.saveData(scanModel,scanDataDatabase)
                } else {
                    Toast.makeText(this, "Isi nama sampel", Toast.LENGTH_SHORT)
                        .show()
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
        rgb= scanResultPresenter.getAverageColorRGB(bitmap)
        tvRGBResult.text = "${resources.getString(R.string.RGB)} (${rgb[0]} , ${rgb[1]} , ${rgb[2]})"
        llColor.setBackgroundColor(Color.rgb(rgb[0], rgb[1], rgb[2]))
    }

    override fun intentToMain() {
        Toast.makeText(this, "Berhasil menyimpan hasil perhitungan", Toast.LENGTH_SHORT)
            .show()
        startActivity(intentFor<MainActivity>().clearTop().clearTask())
    }

    @SuppressLint("SetTextI18n")
    override fun setResultCalculation(concentration: Double, hqLevel: Double, status: String) {
        tvConcentration.text =
            resources.getString(R.string.konsentrasi) + decimalFormat.format(
                concentration
            ) + resources.getString(R.string.concentration_unit)
        tvHQLevel.text = resources.getString(R.string.tingkat_hq) + decimalFormat.format(
            hqLevel
        ) + resources.getString(R.string.percent)
        tvStatus.text =
            resources.getString(R.string.status) + status
    }

}
