package com.putra.hydroquinoneanalyzer.presenter


import android.graphics.Bitmap
import android.graphics.Color
import com.putra.hydroquinoneanalyzer.view.TakePictureView
import java.io.File

class TakePicturePresenter(private val takePictureView: TakePictureView) {
    fun startCamera(){
        takePictureView.startCamera()
    }

    fun takePhoto(){
        takePictureView.takePhoto()
    }

    fun allPermissionsGranted() : Boolean{
        return takePictureView.allPermissionsGranted()
    }

    fun getOutputDirectory(): File {
        return takePictureView.getOutputDirectory()
    }

    fun getAverageColorRGB(bitmap: Bitmap): IntArray {
        val width = bitmap.width / 2
        val height = bitmap.height / 2

        val pixelColor: Int
        pixelColor = bitmap.getPixel(width, height)
        val redValue = Color.red(pixelColor)
        val blueValue = Color.blue(pixelColor)
        val greenValue = Color.green(pixelColor)

        return intArrayOf(
            redValue, greenValue, blueValue
        )
    }
}
