package com.putra.hydroquinoneanalyzer.view

import android.graphics.Bitmap
import android.view.View

interface SelectImageView {
    fun showPopUp(v: View?)
    fun showLoadingPickImage()
    fun hideLoading()
    fun captureFromCamera()
    fun fromGallery()
    fun checkPermission()
    fun intentToScanResultActivityFromGallery(bitmap: Bitmap)
    fun intentToScanResultActivityFromCamera(redValue: Int,greenValue:Int, blueValue:Int)
    fun intentToTakePictureActivity()
    fun showMessage()
}