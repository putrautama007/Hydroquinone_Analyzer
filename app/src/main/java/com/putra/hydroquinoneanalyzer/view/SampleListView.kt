package com.putra.hydroquinoneanalyzer.view

import com.putra.hydroquinoneanalyzer.model.ScanModel

interface SampleListView {
    fun initView()
    fun setSampleRecyclerView(scanModels: List<ScanModel>)
    fun showNoData()
    fun filterRecyclerviewHasData(scanModels: ArrayList<ScanModel>)
    fun filterRecyclerviewHasNoData()
}