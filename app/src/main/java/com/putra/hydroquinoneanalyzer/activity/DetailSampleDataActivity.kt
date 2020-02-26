package com.putra.hydroquinoneanalyzer.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.putra.hydroquinoneanalyzer.R
import com.putra.hydroquinoneanalyzer.model.ScanModel
import com.putra.hydroquinoneanalyzer.presenter.DetailSampleDataPresenter
import com.putra.hydroquinoneanalyzer.room.ScanDataDatabase
import com.putra.hydroquinoneanalyzer.utils.DecimalFormat.Companion.decimalFormat
import com.putra.hydroquinoneanalyzer.view.DetailSampleDataView
import kotlinx.android.synthetic.main.activity_detail_sample_data.*

class DetailSampleDataActivity : AppCompatActivity(),DetailSampleDataView {

    private lateinit var scanDataDatabase: ScanDataDatabase
    private lateinit var detailSampleDataPresenter: DetailSampleDataPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_sample_data)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        scanDataDatabase = ScanDataDatabase.getInstance(this@DetailSampleDataActivity)!!
        val id = intent.getLongExtra("sampleId", 0)
        detailSampleDataPresenter = DetailSampleDataPresenter(this)
        detailSampleDataPresenter.retrieveScanDataById(id,scanDataDatabase)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @SuppressLint("SetTextI18n")
    override fun retrieveScanDataById(scanModel: ScanModel) {
        runOnUiThread {
            tvSampleNameDetail.text = scanModel.sampleName
            llColorDetail.setBackgroundColor(
                Color.rgb(
                    scanModel.red,
                    scanModel.green,
                    scanModel.blue
                )
            )
            txtRGBDetail.text = "${resources.getString(R.string.RGB)} ( ${scanModel.red} ,${scanModel.green} , ${scanModel.blue})"
            tvConcentrationDetail.text = "${resources.getString(R.string.konsentrasi)} ${decimalFormat.format(
                scanModel.concentration)} ${resources.getString(R.string.concentration_unit)}"
            tvHQLevelDetail.text = "${resources.getString(R.string.tingkat_hq)} ${decimalFormat.format(
                scanModel.concentrationPercentage)} ${resources.getString(R.string.percent)}"
            tvStatusDetail.text = "${resources.getString(R.string.status)} ${scanModel.status}"
        }
    }
}
