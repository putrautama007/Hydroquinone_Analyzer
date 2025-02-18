package com.putra.hydroquinoneanalyzer.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.putra.hydroquinoneanalyzer.model.ScanModel

@Dao
interface CalculationDao {

    @Query("Select * from scanData ORDER BY scanId DESC")
    fun getScanData(): List<ScanModel>

    @Insert
    fun insertScanData(scanModel: ScanModel?)

    @Query("SELECT * FROM scanData WHERE scanId = :id")
    fun getScanDataById(id: Long): ScanModel

    @Delete
    fun deleteScanData(scanModel: ScanModel?)
}