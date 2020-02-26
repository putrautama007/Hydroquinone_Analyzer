package com.putra.hydroquinoneanalyzer.view

import com.putra.hydroquinoneanalyzer.model.ScanModel

interface SampleListView {
    fun initView()
    fun showLoading()
    fun hideLoading()
    fun setSampleRecyclerView(scanModels: List<ScanModel>)
    fun showNoData()
}