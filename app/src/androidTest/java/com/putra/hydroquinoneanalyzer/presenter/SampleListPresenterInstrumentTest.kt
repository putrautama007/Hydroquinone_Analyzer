package com.putra.hydroquinoneanalyzer.presenter

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.putra.hydroquinoneanalyzer.model.ScanModel
import com.putra.hydroquinoneanalyzer.room.CalculationDao
import com.putra.hydroquinoneanalyzer.room.ScanDataDatabase
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SampleListPresenterInstrumentTest {
    companion object{
        private const val STATUS_GOOD = "Layak Pakai"
        private const val STATUS_BAD = "Tidak Layak Pakai"
    }
    private lateinit var scanDataDatabase: ScanDataDatabase
    private lateinit var calculationDao: CalculationDao

    @Before
    fun initDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        scanDataDatabase = Room.inMemoryDatabaseBuilder(context,
            ScanDataDatabase::class.java).build()
        calculationDao = scanDataDatabase.calculationDao()!!
    }

    @After
    fun closeDb() {
        scanDataDatabase.close()
    }

    @Test
    fun retrieveScanDataWithNoData() {
        val scanModel =  calculationDao.getScanData()
        Assert.assertTrue(scanModel.isEmpty())
    }

    @Test
    fun retrieveScanDataWithData() {
        val scanData = ScanModel(
            System.currentTimeMillis(),
            "Sampel 1",
            79,49,47,
            "Konsentrasi : 8,29 ppm",
            "Tingkat HQ : 82,95%",
            STATUS_BAD
        )
        calculationDao.insertScanData(scanData)
        val scanModel =  calculationDao.getScanData()
        Assert.assertTrue(scanModel.isNotEmpty())
    }

}