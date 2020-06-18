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
            tvConcentrationDetail.text = scanModel.concentration
            tvHQLevelDetail.text =scanModel.concentrationPercentage
            if(scanModel.status?.contains("Tidak Layak Pakai")!!){
                tvStatusDetail.text = "Status : ${scanModel.status!!.toLowerCase()} karena konsentrasi melebihi dari 0.02 ppm"
            }else{
                tvStatusDetail.text = "Status : ${scanModel.status!!.toLowerCase()} karena konsentrasi kurang dari 0.02 ppm"
            }
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
