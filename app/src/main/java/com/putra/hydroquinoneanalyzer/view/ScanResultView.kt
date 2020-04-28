package com.putra.hydroquinoneanalyzer.view

interface ScanResultView {
    fun initView()
    fun intentToMain()
    fun setResultCalculation(concentration:Double,hqLevel : Double, status: String)
}