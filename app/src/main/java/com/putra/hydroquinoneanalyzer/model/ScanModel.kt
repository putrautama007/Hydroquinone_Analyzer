package com.putra.hydroquinoneanalyzer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scanData")
data class ScanModel(
    @PrimaryKey(autoGenerate = true)
    var scanId: Long = 0,

    @ColumnInfo(name = "sampleName")
    var sampleName: String? = "",

    @ColumnInfo(name = "red")
    var red: Int = 0,

    @ColumnInfo(name = "green")
    var green: Int = 0,

    @ColumnInfo(name = "blue")
    var blue: Int = 0,

    @ColumnInfo(name = "concentration")
    var concentration: String = "",

    @ColumnInfo(name = "concentrationPercentage")
    var concentrationPercentage: String = "",

    @ColumnInfo(name = "status")
    var status: String? = ""
)