package com.putra.hydroquinoneanalyzer.presenter

import com.putra.hydroquinoneanalyzer.model.RgbModel
import com.putra.hydroquinoneanalyzer.view.StandardCurveCalculationView

class StandardCurveCalculationPresenter(private val standardCurveCalculationView: StandardCurveCalculationView) {
    fun intentToTakePictureActivity() {
        standardCurveCalculationView.intentToTakePictureActivity()
    }

    fun checkPermission() {
        standardCurveCalculationView.checkPermission()
    }

    fun initView() {
        standardCurveCalculationView.initView()
    }

    fun showPopup(redValue : Int,greenValue : Int , blueValue : Int){
        standardCurveCalculationView.showPopup(redValue, greenValue, blueValue)
    }
}
