package com.putra.hydroquinoneanalyzer.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.putra.hydroquinoneanalyzer.R
import com.putra.hydroquinoneanalyzer.adapter.ListSampleAdapter
import com.putra.hydroquinoneanalyzer.model.ScanModel
import com.putra.hydroquinoneanalyzer.presenter.MainPresenter
import com.putra.hydroquinoneanalyzer.room.ScanDataDatabase
import com.putra.hydroquinoneanalyzer.view.MainView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(),View.OnClickListener,MainView {

    private lateinit var listSampleAdapter: ListSampleAdapter
    private lateinit var scanDataDatabase: ScanDataDatabase
    private lateinit var mainPresenter : MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainPresenter = MainPresenter(this)

        mainPresenter.initView()
        mainPresenter.retrieveScanData(scanDataDatabase)
    }

    override fun onClick(p0: View?) {
        when(p0){
            cardViewGuide ->{
                startActivity<UserGuideActivity>()
            }
            cardViewSampleList ->{
                startActivity<SampleListActivity>()
            }
            cardViewScan ->{
                startActivity<SelectImageActivity>()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mainPresenter.retrieveScanData(scanDataDatabase)
    }

    override fun initView() {
        cardViewGuide.setOnClickListener(this)
        cardViewSampleList.setOnClickListener(this)
        cardViewScan.setOnClickListener(this)
        rvListSample.layoutManager = LinearLayoutManager(this)
        listSampleAdapter = ListSampleAdapter(this)
        scanDataDatabase = ScanDataDatabase.getInstance(applicationContext)!!
        rvListSample.adapter = listSampleAdapter
    }

    override fun setSampleRecyclerView(scanModels: List<ScanModel>) {
        runOnUiThread { listSampleAdapter.setScanData(scanModels as ArrayList) }
    }

    override fun showNoData() {
        runOnUiThread {
            clNoData.visibility = View.VISIBLE
        }
    }
}
