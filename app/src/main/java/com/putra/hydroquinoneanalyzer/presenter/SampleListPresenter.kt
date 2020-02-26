package com.putra.hydroquinoneanalyzer.presenter

import com.putra.hydroquinoneanalyzer.model.ScanModel
import com.putra.hydroquinoneanalyzer.room.AppExecutors
import com.putra.hydroquinoneanalyzer.room.ScanDataDatabase
import com.putra.hydroquinoneanalyzer.view.SampleListView

class SampleListPresenter(private val sampleListView: SampleListView) {
    fun initView(){
        sampleListView.initView()
    }

    fun retrieveScanData(scanDataDatabase: ScanDataDatabase){
        AppExecutors.getInstance()?.diskIO()?.execute {
            val scanModels: List<ScanModel>? =
                scanDataDatabase.calculationDao()?.getScanData()
            if (scanModels?.isEmpty()!!) {
                sampleListView.showNoData()
            } else {
                sampleListView.setSampleRecyclerView(scanModels as ArrayList)
            }
        }
    }
}