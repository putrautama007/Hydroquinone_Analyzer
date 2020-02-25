package com.putra.hydroquinoneanalyzer.presenter
import com.putra.hydroquinoneanalyzer.model.ScanModel
import com.putra.hydroquinoneanalyzer.room.AppExecutors
import com.putra.hydroquinoneanalyzer.room.ScanDataDatabase
import com.putra.hydroquinoneanalyzer.view.ScanResultView

class ScanResultPresenter(private val scanResultView: ScanResultView) {

    fun initView(){
        scanResultView.initView()
    }

    fun saveData(scanModel: ScanModel,scanDataDatabase:ScanDataDatabase){
        AppExecutors.getInstance()?.diskIO()?.execute {
            scanDataDatabase.calculationDao()?.insertScanData(scanModel)
            scanResultView.intentToMain()
        }
    }
}