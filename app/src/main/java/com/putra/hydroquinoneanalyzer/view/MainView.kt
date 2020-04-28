package com.putra.hydroquinoneanalyzer.view

import com.putra.hydroquinoneanalyzer.model.ScanModel

interface MainView {
    fun initView()
    fun setSampleRecyclerView(scanModels: List<ScanModel>)
    fun showNoData()
}