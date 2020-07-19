package com.putra.hydroquinoneanalyzer.presenter

import com.putra.hydroquinoneanalyzer.view.StandardCurveCalculationResultView

class StandardCurveCalculationResultPresenter(private val standardCurveCalculationResultView: StandardCurveCalculationResultView) {
    fun initView() {
        standardCurveCalculationResultView.initView()
    }
}