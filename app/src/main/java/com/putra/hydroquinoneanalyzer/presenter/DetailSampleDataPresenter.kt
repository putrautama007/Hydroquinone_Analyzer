package com.putra.hydroquinoneanalyzer.presenter

import com.putra.hydroquinoneanalyzer.model.ScanModel
import com.putra.hydroquinoneanalyzer.room.AppExecutors
import com.putra.hydroquinoneanalyzer.room.ScanDataDatabase
import com.putra.hydroquinoneanalyzer.view.DetailSampleDataView

class DetailSampleDataPresenter(private val detailSampleDataView: DetailSampleDataView) {
    fun retrieveScanDataById(scanId : Long,scanDataDatabase: ScanDataDatabase){
        AppExecutors.getInstance()?.diskIO()?.execute{
            val scanModel: ScanModel = scanDataDatabase.calculationDao()!!.getScanDataById(scanId)
            detailSampleDataView.retrieveScanDataById(scanModel)
        }
    }
}