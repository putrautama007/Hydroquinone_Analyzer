package com.putra.hydroquinoneanalyzer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scanData")
class ScanModel(
    @PrimaryKey(autoGenerate = true)
    var scanId: Long = 0,

    @ColumnInfo(name = "sampleName")
    private var sampleName: String? = "",

    @ColumnInfo(name = "red")
    private var red: Int = 0,

    @ColumnInfo(name = "green")
    private var green: Int = 0,

    @ColumnInfo(name = "blue")
    private var blue: Int = 0,

    @ColumnInfo(name = "concentration")
    private var concentration: Double = 0.0,

    @ColumnInfo(name = "concentrationPercentage")
    private var concentrationPercentage: Double = 0.0,

    @ColumnInfo(name = "status")
    private var status: String? = ""
)