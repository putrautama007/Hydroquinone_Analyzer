package com.putra.hydroquinoneanalyzer.presenter

import com.putra.hydroquinoneanalyzer.model.ScanModel
import com.putra.hydroquinoneanalyzer.room.AppExecutors
import com.putra.hydroquinoneanalyzer.room.ScanDataDatabase
import com.putra.hydroquinoneanalyzer.view.MainView

class MainPresenter(private val mainView: MainView) {
    fun initView(){
        mainView.initView()
    }

    fun retrieveScanData(scanDataDatabase:ScanDataDatabase){
        val appExecutors = AppExecutors.getInstance()
        appExecutors?.diskIO()?.execute {
            val scanModels: List<ScanModel>? =
                scanDataDatabase.calculationDao()?.getScanData()
            if (scanModels?.isEmpty()!!) {
                mainView.showNoData()
            } else {
                mainView.setSampleRecyclerView(scanModels as ArrayList)
            }
        }
    }
}