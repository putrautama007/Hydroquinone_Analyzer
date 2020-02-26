package com.putra.hydroquinoneanalyzer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.putra.hydroquinoneanalyzer.R
import com.putra.hydroquinoneanalyzer.adapter.ListSampleAdapter
import com.putra.hydroquinoneanalyzer.model.ScanModel
import com.putra.hydroquinoneanalyzer.presenter.SampleListPresenter
import com.putra.hydroquinoneanalyzer.room.ScanDataDatabase
import com.putra.hydroquinoneanalyzer.view.SampleListView
import kotlinx.android.synthetic.main.activity_main.*

class SampleListActivity : AppCompatActivity(),SampleListView {

    private lateinit var listSampleAdapter: ListSampleAdapter
    private lateinit var sampleListPresenter : SampleListPresenter
    private lateinit var scanDataDatabase: ScanDataDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_list)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        sampleListPresenter = SampleListPresenter(this)

        sampleListPresenter.initView()
        sampleListPresenter.retrieveScanData(scanDataDatabase)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun initView() {
        rvListSample.layoutManager = LinearLayoutManager(this)
        listSampleAdapter = ListSampleAdapter(this)
        scanDataDatabase = ScanDataDatabase.getInstance(applicationContext)!!
        rvListSample.adapter = listSampleAdapter
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setSampleRecyclerView(scanModels: List<ScanModel>) {
        runOnUiThread { listSampleAdapter.setScanData(scanModels as ArrayList) }
    }

    override fun showNoData() {
        clNoData.visibility = View.VISIBLE
    }
}
