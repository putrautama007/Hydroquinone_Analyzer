package com.putra.hydroquinoneanalyzer.presenter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Environment
import android.view.View
import com.putra.hydroquinoneanalyzer.view.SelectImageView
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class SelectImagePresenter(private val selectImageView: SelectImageView) {
    fun showPopUp(v: View?){
        selectImageView.showPopUp(v)
    }

    fun showLoadingPickImage(){
        selectImageView.showLoadingPickImage()
    }

    fun hideLoading(){
        selectImageView.hideLoading()
    }

    fun fromGallery(){
        selectImageView.fromGallery()
    }

    fun checkPermission(){
        selectImageView.checkPermission()
    }

    fun calculateFromGallery(bitmap: Bitmap?){
        if (bitmap != null) {
            selectImageView.intentToScanResultActivityFromGallery(bitmap)
        } else {
            selectImageView.showMessage()
        }
    }

    fun calculateFromCamera(redValue: Int,greenValue:Int, blueValue:Int?){
        if(blueValue != null){
            selectImageView.intentToScanResultActivityFromCamera(redValue,greenValue, blueValue)
        }else{
            selectImageView.showMessage()
        }
    }

    fun intentToTakePictureActivity(){
        selectImageView.intentToTakePictureActivity()
    }
}
