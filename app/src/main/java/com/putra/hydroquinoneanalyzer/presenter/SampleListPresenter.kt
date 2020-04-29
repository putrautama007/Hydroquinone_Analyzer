package com.putra.hydroquinoneanalyzer.presenter

import com.putra.hydroquinoneanalyzer.model.ScanModel
import com.putra.hydroquinoneanalyzer.room.AppExecutors
import com.putra.hydroquinoneanalyzer.room.ScanDataDatabase
import com.putra.hydroquinoneanalyzer.view.SampleListView
import java.util.*
import kotlin.collections.ArrayList

class SampleListPresenter(private val sampleListView: SampleListView) {
    private lateinit var scanModels: List<ScanModel>
    fun initView(){
        sampleListView.initView()
    }

    fun retrieveScanData(scanDataDatabase: ScanDataDatabase){
        AppExecutors.getInstance()?.diskIO()?.execute {
            scanModels =
                scanDataDatabase.calculationDao()!!.getScanData()
            if (scanModels.isEmpty()) {
                sampleListView.showNoData()
            } else {
                sampleListView.setSampleRecyclerView(scanModels as ArrayList)
            }
        }
    }

    fun filterSampleList(searchSampleName: String){
        val filteredSampleData : ArrayList<ScanModel> = ArrayList()
        for (data in scanModels){
            if (data.sampleName?.toLowerCase(Locale.getDefault())?.contains(searchSampleName.toLowerCase(Locale.getDefault()))!!){
                filteredSampleData.add(data)
            }
        }
        if (filteredSampleData.isNotEmpty()) {
            sampleListView.filterRecyclerviewHasData(filteredSampleData)
        }else{
            sampleListView.filterRecyclerviewHasNoData()
        }
    }
}