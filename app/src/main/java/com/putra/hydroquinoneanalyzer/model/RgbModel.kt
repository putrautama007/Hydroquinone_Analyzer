package com.putra.hydroquinoneanalyzer.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RgbModel (
    var redValue : Int = 0,
    var greenValue : Int = 0,
    var blueValue : Int = 0,
    var concentration: Double = 0.0
) : Parcelable