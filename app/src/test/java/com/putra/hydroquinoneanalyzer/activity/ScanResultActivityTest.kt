package com.putra.hydroquinoneanalyzer.activity

import android.view.View
import com.putra.hydroquinoneanalyzer.R
import com.putra.hydroquinoneanalyzer.model.ScanModel
import com.putra.hydroquinoneanalyzer.presenter.ScanResultPresenter
import com.putra.hydroquinoneanalyzer.room.ScanDataDatabase
import com.putra.hydroquinoneanalyzer.view.ScanResultView
import kotlinx.android.synthetic.main.activity_scan_result.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class  ScanResultActivityTest{
    companion object{
        private const val STATUS_BAD = "Tidak Layak Pakai"
    }


    var view1: View? = null
    var view2: Int = R.id.btnSaveResult
    var view3: Int = R.id.btnSaveResult

    @Test
    fun onClickViewWithNull() {
        val view: View? = null
        val scanModel = ScanModel(
            System.currentTimeMillis(),
           "Sampel 1",
            79,49,47,
           "Konsentrasi : 8,29 ppm",
            "Tingkat HQ : 82,95%",
            STATUS_BAD
        )
        Assert.assertEquals(view1, view)
    }

    @Test
    fun onClickViewWithBtnResultAndEmptySampleName() {
        val view: Int? = R.id.btnSaveResult
        val scanModel = ScanModel(
            System.currentTimeMillis(),
            "",
            79,49,47,
            "Konsentrasi : 8,29 ppm",
            "Tingkat HQ : 82,95%",
            STATUS_BAD
        )
        Assert.assertEquals(view2, view)
        Assert.assertEquals("",scanModel.sampleName)
    }

    @Test
    fun onClickViewWithBtnResultAndSampleNameNotEmpty() {
        val view: Int? = R.id.btnSaveResult
        val scanModel = ScanModel(
            System.currentTimeMillis(),
            "Sampel 1",
            79,49,47,
            "Konsentrasi : 8,29 ppm",
            "Tingkat HQ : 82,95%",
            STATUS_BAD
        )
        Assert.assertEquals(view2, view)
        Assert.assertEquals("Sampel 1",scanModel.sampleName)
    }
}