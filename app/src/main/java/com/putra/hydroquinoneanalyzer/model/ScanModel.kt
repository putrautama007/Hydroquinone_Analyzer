package com.putra.hydroquinoneanalyzer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scanData")
class ScanModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var scanId: Long = 0

    @ColumnInfo(name = "sampleName")
    private var sampleName: String? = null

    @ColumnInfo(name = "red")
    private var red = 0

    @ColumnInfo(name = "green")
    private var green = 0

    @ColumnInfo(name = "blue")
    private var blue = 0

    @ColumnInfo(name = "concentration")
    private var concentration = 0.0

    @ColumnInfo(name = "concentrationPercentage")
    private var concentrationPercentage = 0.0

    @ColumnInfo(name = "status")
    private var status: String? = null

    fun ScanModel(
        scanId: Long,
        sampleName: String?,
        red: Int,
        green: Int,
        blue: Int,
        concentration: Double,
        concentrationPercentage: Double,
        status: String?
    ) {
        this.scanId = scanId
        this.sampleName = sampleName
        this.red = red
        this.green = green
        this.blue = blue
        this.concentration = concentration
        this.concentrationPercentage = concentrationPercentage
        this.status = status
    }
}