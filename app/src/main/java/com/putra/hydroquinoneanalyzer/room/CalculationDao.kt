package com.putra.hydroquinoneanalyzer.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.putra.hydroquinoneanalyzer.model.ScanModel

@Dao
interface CalculationDao {

    @Query("Select * from scanData")
    fun getScanData(): List<ScanModel>

    @Insert
    fun insertScanData(scanModel: ScanModel?)

    @Query("SELECT * FROM scanData WHERE scanId = :id")
    fun getScanDataById(id: Long): ScanModel
}