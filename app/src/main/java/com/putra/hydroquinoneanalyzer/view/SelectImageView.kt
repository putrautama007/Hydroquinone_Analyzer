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
    fun intentToScanResultActivity(bitmap: Bitmap)
    fun showMessage()
}