package com.putra.hydroquinoneanalyzer.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.putra.hydroquinoneanalyzer.R
import com.putra.hydroquinoneanalyzer.model.ScanModel
import com.putra.hydroquinoneanalyzer.presenter.DetailSampleDataPresenter
import com.putra.hydroquinoneanalyzer.room.ScanDataDatabase
import com.putra.hydroquinoneanalyzer.utils.DecimalFormat.Companion.decimalFormat
import com.putra.hydroquinoneanalyzer.view.DetailSampleDataView
import kotlinx.android.synthetic.main.activity_detail_sample_data.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity

class DetailSampleDataActivity : AppCompatActivity(),DetailSampleDataView,View.OnClickListener {

    private lateinit var scanDataDatabase: ScanDataDatabase
    private lateinit var detailSampleDataPresenter: DetailSampleDataPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_sample_data)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        scanDataDatabase = ScanDataDatabase.getInstance(this@DetailSampleDataActivity)!!
        val id = intent.getLongExtra("sampleId", 0)
        detailSampleDataPresenter = DetailSampleDataPresenter(this,scanDataDatabase)
        detailSampleDataPresenter.retrieveScanDataById(id)
        buttonDeleteSample.setOnClickListener(this)
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

    override fun deleteScanData() {
        startActivity(intentFor<MainActivity>().clearTop().clearTask())
    }

    override fun onClick(p0: View?) {
        when(p0){
            buttonDeleteSample ->{
                detailSampleDataPresenter.deleteScanData()
            }
        }
    }
}
