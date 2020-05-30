package com.putra.hydroquinoneanalyzer.view

import java.io.File

interface TakePictureView {
    fun startCamera()
    fun takePhoto()
    fun allPermissionsGranted() : Boolean
    fun getOutputDirectory(): File
}
