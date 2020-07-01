package com.putra.hydroquinoneanalyzer.presenter

import com.putra.hydroquinoneanalyzer.activity.ScanResultActivityTest
import com.putra.hydroquinoneanalyzer.model.ScanModel
import org.junit.Assert
import org.junit.Test
import java.util.*
import kotlin.collections.ArrayList

class SampleListPresenterTest {
    companion object{
        private const val STATUS_GOOD = "Layak Pakai"
        private const val STATUS_BAD = "Tidak Layak Pakai"
    }

    @Test
    fun filterSampleListWithOneList(){
        val searchSampleName = "Sampel 1";
        val scanModels :ArrayList<ScanModel> = ArrayList()
        scanModels.add(ScanModel(
            System.currentTimeMillis(),
            "Sampel 1",
            79,49,47,
            "Konsentrasi : 8,29 ppm",
            "Tingkat HQ : 82,95%",
            STATUS_BAD
        ))

        val filteredSampleData : ArrayList<ScanModel> = ArrayList()
        for (data in scanModels){
            if (data.sampleName?.toLowerCase(Locale.getDefault())?.contains(searchSampleName.toLowerCase(
                    Locale.getDefault()))!!){
                filteredSampleData.add(data)
            }
        }
        if (filteredSampleData.isNotEmpty()) {
            Assert.assertTrue("Ditemukan", filteredSampleData[0].sampleName.equals(searchSampleName))
        }
    }

    @Test
    fun filterSampleListWithNotGetData(){
        val searchSampleName = "Sampel 100";
        val scanModels :ArrayList<ScanModel> = ArrayList()
        scanModels.add(ScanModel(
            System.currentTimeMillis(),
            "Sampel 1",
            79,49,47,
            "Konsentrasi : 8,29 ppm",
            "Tingkat HQ : 82,95%",
            STATUS_BAD
        ))
        val filteredSampleData : ArrayList<ScanModel> = ArrayList()
        for (data in scanModels){
            if (data.sampleName?.toLowerCase(Locale.getDefault())?.contains(searchSampleName.toLowerCase(
                    Locale.getDefault()))!!){
                filteredSampleData.add(data)
            }
        }
        Assert.assertTrue("Data tidak ditemukan", filteredSampleData.isEmpty())
    }

    @Test
    fun filterSampleListWithManyData(){
        val searchSampleName = "Sampel";
        val scanModels :ArrayList<ScanModel> = ArrayList()
        scanModels.add(ScanModel(
            System.currentTimeMillis(),
            "Sampel 1",
            79,49,47,
            "Konsentrasi : 8,29 ppm",
            "Tingkat HQ : 82,95%",
            STATUS_BAD
        ))
        scanModels.add(ScanModel(
            System.currentTimeMillis(),
            "Sampel 2",
            79,49,47,
            "Konsentrasi : 8,29 ppm",
            "Tingkat HQ : 82,95%",
            STATUS_BAD
        ))
        scanModels.add(ScanModel(
            System.currentTimeMillis(),
            "Sampel 3",
            79,49,47,
            "Konsentrasi : 8,29 ppm",
            "Tingkat HQ : 82,95%",
            STATUS_BAD
        ))
        scanModels.add(ScanModel(
            System.currentTimeMillis(),
            "Sampel 4",
            79,49,47,
            "Konsentrasi : 8,29 ppm",
            "Tingkat HQ : 82,95%",
            STATUS_BAD
        ))

        val filteredSampleData : ArrayList<ScanModel> = ArrayList()
        for (data in scanModels){
            if (data.sampleName?.toLowerCase(Locale.getDefault())?.contains(searchSampleName.toLowerCase(
                    Locale.getDefault()))!!){
                filteredSampleData.add(data)
            }
        }
        if (filteredSampleData.isNotEmpty()) {
            filteredSampleData[0].sampleName?.contains(searchSampleName)?.let {
                Assert.assertTrue("Ditemukan",
                    it
                )
            }
        }
    }

    @Test
    fun filterSampleListWithManyDataButNotGetData(){
        val searchSampleName = "Sampel 9";
        val scanModels :ArrayList<ScanModel> = ArrayList()
        scanModels.add(ScanModel(
            System.currentTimeMillis(),
            "Sampel 1",
            79,49,47,
            "Konsentrasi : 8,29 ppm",
            "Tingkat HQ : 82,95%",
            STATUS_BAD
        ))
        scanModels.add(ScanModel(
            System.currentTimeMillis(),
            "Sampel 2",
            79,49,47,
            "Konsentrasi : 8,29 ppm",
            "Tingkat HQ : 82,95%",
            STATUS_BAD
        ))
        scanModels.add(ScanModel(
            System.currentTimeMillis(),
            "Sampel 3",
            79,49,47,
            "Konsentrasi : 8,29 ppm",
            "Tingkat HQ : 82,95%",
            STATUS_BAD
        ))
        scanModels.add(ScanModel(
            System.currentTimeMillis(),
            "Sampel 4",
            79,49,47,
            "Konsentrasi : 8,29 ppm",
            "Tingkat HQ : 82,95%",
            STATUS_BAD
        ))

        val filteredSampleData : ArrayList<ScanModel> = ArrayList()
        for (data in scanModels){
            if (data.sampleName?.toLowerCase(Locale.getDefault())?.contains(searchSampleName.toLowerCase(
                    Locale.getDefault()))!!){
                filteredSampleData.add(data)
            }
        }
        Assert.assertTrue("Data tidak ditemukan", filteredSampleData.isEmpty())
    }
}