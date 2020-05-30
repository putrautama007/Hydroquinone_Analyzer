package com.putra.hydroquinoneanalyzer.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.putra.hydroquinoneanalyzer.R
import com.putra.hydroquinoneanalyzer.presenter.TakePicturePresenter
import com.putra.hydroquinoneanalyzer.view.TakePictureView
import kotlinx.android.synthetic.main.activity_take_picture.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TakePictureActivity : AppCompatActivity(),TakePictureView {
    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var camera: Camera? = null
    private lateinit var bitmap: Bitmap

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    private lateinit var takePicturePresenter: TakePicturePresenter

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        const val CAPTURE_IMAGE_CODE_RESULT = 1
        const val RED = "RED"
        const val GREEN = "GREEN"
        const val BLUE = "BLUE"
    }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_picture)

        takePicturePresenter = TakePicturePresenter(this)

        if (takePicturePresenter.allPermissionsGranted()) {
            takePicturePresenter.startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        capturePictureButton.setOnClickListener { takePicturePresenter.takePhoto() }

        outputDirectory = takePicturePresenter.getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()

        backTackPicture.setOnClickListener{
           finish()
        }
    }

     override fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            preview = Preview.Builder()
                .build()

            imageCapture = ImageCapture.Builder()
                .build()

            val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

            try {
                cameraProvider.unbindAll()

                camera = cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture)
                preview?.setSurfaceProvider(cameraOverlay.createSurfaceProvider(camera?.cameraInfo))
            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

     override fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg")

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    bitmap = BitmapFactory.decodeFile(photoFile.path)
                    val rgb = takePicturePresenter.getAverageColorRGB(bitmap)
                    Log.d(TAG, rgb[0].toString())
                    Log.d(TAG, rgb[1].toString())
                    Log.d(TAG, rgb[2].toString())
                    val resultIntent = Intent()
                    resultIntent.putExtra(RED, rgb[0])
                    resultIntent.putExtra(GREEN, rgb[1])
                    resultIntent.putExtra(BLUE, rgb[2])
                    setResult(CAPTURE_IMAGE_CODE_RESULT, resultIntent)
                    finish()
                }
            })
    }

     override fun allPermissionsGranted() : Boolean {
       return  REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                baseContext, it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (takePicturePresenter.allPermissionsGranted()) {
                takePicturePresenter.startCamera()
            } else {
                Toast.makeText(this,
                    "Permissions ditolak oleh pengguna",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
