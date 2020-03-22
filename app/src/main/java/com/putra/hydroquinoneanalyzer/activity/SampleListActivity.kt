package com.putra.hydroquinoneanalyzer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.putra.hydroquinoneanalyzer.R
import com.putra.hydroquinoneanalyzer.adapter.ListSampleAdapter
import com.putra.hydroquinoneanalyzer.model.ScanModel
import com.putra.hydroquinoneanalyzer.presenter.SampleListPresenter
import com.putra.hydroquinoneanalyzer.room.ScanDataDatabase
import com.putra.hydroquinoneanalyzer.view.SampleListView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.clNoData
import kotlinx.android.synthetic.main.activity_main.rvListSample
import kotlinx.android.synthetic.main.activity_sample_list.*
import kotlinx.android.synthetic.main.no_data_layout.*

class SampleListActivity : AppCompatActivity(),SampleListView,View.OnClickListener {

    private lateinit var listSampleAdapter: ListSampleAdapter
    private lateinit var sampleListPresenter : SampleListPresenter
    private lateinit var scanDataDatabase: ScanDataDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_list)

        sampleListPresenter = SampleListPresenter(this)

        sampleListPresenter.initView()
        sampleListPresenter.retrieveScanData(scanDataDatabase)

        editTextSampleSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                sampleListPresenter.filterSampleList(editable.toString())
            }
        })
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
        TODO("not implemented")
    }

    override fun hideLoading() {
        TODO("not implemented")
    }

    override fun setSampleRecyclerView(scanModels: List<ScanModel>) {
        runOnUiThread { listSampleAdapter.setScanData(scanModels as ArrayList) }
    }

    override fun showNoData() {
        clNoData.visibility = View.VISIBLE
    }

    override fun filterRecyclerviewHasData(scanModels: ArrayList<ScanModel>) {
        listSampleAdapter.filterSampleList(scanModels)
    }

    override fun filterRecyclerviewHasNoData() {
        textViewNoDataMessage.text = getString(R.string.data_not_found)
    }

    override fun onClick(p0: View?) {
        when(p0){
            imageButtonBackListSample ->{
                onBackPressed()
            }
        }
    }
}
