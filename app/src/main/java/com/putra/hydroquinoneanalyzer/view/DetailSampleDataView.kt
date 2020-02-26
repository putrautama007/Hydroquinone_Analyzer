package com.putra.hydroquinoneanalyzer.view

import com.putra.hydroquinoneanalyzer.model.ScanModel

interface DetailSampleDataView {
    fun retrieveScanDataById(scanModel: ScanModel)

}