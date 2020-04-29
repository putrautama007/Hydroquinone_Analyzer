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

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    fun createImageFile(): File {
        val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM
            ), "Camera"
        )
        val image = File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )
        getCameraFilePath(image)
        return image
    }

    fun getCameraFilePath(imageFile : File) : String{
        return "file://" + imageFile.absolutePath
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

    fun captureFromCamera(){
        selectImageView.captureFromCamera()
    }

    fun checkPermission(){
        selectImageView.checkPermission()
    }

    fun calculate(bitmap: Bitmap?){
        if (bitmap != null) {
            selectImageView.intentToScanResultActivity(bitmap)
        } else {
            selectImageView.showMessage()
        }
    }

}
