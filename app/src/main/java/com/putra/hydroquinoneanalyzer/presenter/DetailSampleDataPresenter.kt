package com.putra.hydroquinoneanalyzer.presenter

import com.putra.hydroquinoneanalyzer.model.ScanModel
import com.putra.hydroquinoneanalyzer.room.AppExecutors
import com.putra.hydroquinoneanalyzer.room.ScanDataDatabase
import com.putra.hydroquinoneanalyzer.view.DetailSampleDataView

class DetailSampleDataPresenter(private val detailSampleDataView: DetailSampleDataView, private val scanDataDatabase: ScanDataDatabase) {
    lateinit var scanModel: ScanModel
    fun retrieveScanDataById(scanId : Long){
        AppExecutors.getInstance()?.diskIO()?.execute{
            scanModel = scanDataDatabase.calculationDao()!!.getScanDataById(scanId)
            detailSampleDataView.retrieveScanDataById(scanModel)
        }
    }
    fun deleteScanData(){
        AppExecutors.getInstance()?.diskIO()?.execute{
            scanDataDatabase.calculationDao()!!.deleteScanData(scanModel)
           detailSampleDataView.deleteScanData()
        }
    }
}