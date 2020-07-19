package com.putra.hydroquinoneanalyzer.view

import com.putra.hydroquinoneanalyzer.model.RgbModel

interface StandardCurveCalculationView {
    fun intentToTakePictureActivity()
    fun checkPermission()
    fun initView()
    fun showPopup(redValue : Int,greenValue : Int , blueValue : Int)
}