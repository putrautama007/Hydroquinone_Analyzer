package com.putra.hydroquinoneanalyzer.presenter
import com.putra.hydroquinoneanalyzer.model.ScanModel
import com.putra.hydroquinoneanalyzer.room.AppExecutors
import com.putra.hydroquinoneanalyzer.room.ScanDataDatabase
import com.putra.hydroquinoneanalyzer.view.ScanResultView
import kotlin.math.log10

class ScanResultPresenter(private val scanResultView: ScanResultView) {
    fun initView(){
        scanResultView.initView()
    }

    fun calculationConcentration(rgb: Double){
        val a = 0.07
        val b = 0.1538
        val absorbency : Double = -log10(rgb / 255)
        val concentrationCalculation : Double = (absorbency - b) / a
        val p = 100 / 0.01 * concentrationCalculation
        val q = p / 1000
        val concentrationPercentage : Double = q / 100 * 100
        if (concentrationCalculation < 0.02) {
            scanResultView.setResultCalculation(concentrationCalculation,concentrationPercentage,STATUS_GOOD)
        } else {
            scanResultView.setResultCalculation(concentrationCalculation,concentrationPercentage,STATUS_BAD)
        }
    }

    fun saveData(scanModel: ScanModel,scanDataDatabase:ScanDataDatabase){
        AppExecutors.getInstance()?.diskIO()?.execute {
            scanDataDatabase.calculationDao()?.insertScanData(scanModel)
            scanResultView.intentToMain()
        }
    }
    companion object{
        private const val STATUS_GOOD = "Layak Pakai"
        private const val STATUS_BAD = "Tidak Layak Pakai"
    }
}