package com.putra.hydroquinoneanalyzer.activity

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.putra.hydroquinoneanalyzer.BuildConfig
import com.putra.hydroquinoneanalyzer.R
import com.putra.hydroquinoneanalyzer.presenter.SelectImagePresenter
import com.putra.hydroquinoneanalyzer.view.SelectImageView
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_select_image.*
import java.io.File
import java.io.IOException

class SelectImageActivity : AppCompatActivity(),View.OnClickListener,SelectImageView {

    lateinit var bitmap: Bitmap
    lateinit var selectPickImageDialog : Dialog
    lateinit var selectImagePresenter: SelectImagePresenter
    lateinit var imageFile : File

    companion object{
        private const val CAPTURE_IMAGE_CODE = 0
        private const val GALLERY_IMAGE_CODE = 1
    }

    private lateinit var cameraFilePath : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_image)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        selectPickImageDialog = Dialog(this)
        selectImagePresenter = SelectImagePresenter(this)
        btnScan.setOnClickListener(this)
        ivSetImage.setOnClickListener(this)
        ivScanImage.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0){
            btnScan -> {
                if (bitmap != null) {
                    val intent =
                        Intent(this@SelectImageActivity, ScanResultActivity::class.java)
                    intent.putExtra("result", bitmap)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Tolong pilih gambar yang akan di scan",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            ivScanImage ->{
                selectImagePresenter.showPopUp(ivScanImage)
            }
            ivSetImage ->{
                selectImagePresenter.showPopUp(ivSetImage)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun showPopUp(v: View?) {
        selectPickImageDialog.setContentView(R.layout.popup)
        val btnCamera: Button = selectPickImageDialog.findViewById(R.id.btnCamera)
        val btnGallery: Button = selectPickImageDialog.findViewById(R.id.btnGallery)
        btnCamera.setOnClickListener {
            selectPickImageDialog.dismiss()
            selectImagePresenter.showLoadingPickImage()
            selectImagePresenter.checkPermission()
        }
        btnGallery.setOnClickListener {
            selectPickImageDialog.dismiss()
            selectImagePresenter.showLoadingPickImage()
            selectImagePresenter.fromGallery()
        }
        selectPickImageDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        selectPickImageDialog.show()
    }

    override fun showLoadingPickImage() {
        ivScanImage.visibility = View.GONE
        ivSetImage.visibility = View.VISIBLE
        progressbarSelectImage.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressbarSelectImage.visibility = View.GONE
    }

    override fun captureFromCamera() {
        try {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            imageFile = selectImagePresenter.createImageFile()
            cameraFilePath = selectImagePresenter.getCameraFilePath(imageFile)
            intent.putExtra(
                MediaStore.EXTRA_OUTPUT,
                FileProvider.getUriForFile(
                    this,
                    "${BuildConfig.APPLICATION_ID}.provider",
                    imageFile
                )
            )
            startActivityForResult(intent, CAPTURE_IMAGE_CODE)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

    override fun fromGallery() {
        val pickPhoto = Intent(Intent.ACTION_PICK)
        pickPhoto.type = "image/*"
        val mimeTypes =
            arrayOf("image/jpeg", "image/png")
        pickPhoto.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)

        startActivityForResult(pickPhoto, GALLERY_IMAGE_CODE)
    }

    override fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@SelectImageActivity,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                CAPTURE_IMAGE_CODE
            )
        } else {
            selectImagePresenter.captureFromCamera()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAPTURE_IMAGE_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show()
                selectImagePresenter.captureFromCamera()
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
                selectImagePresenter.checkPermission()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_IMAGE_CODE && resultCode == Activity.RESULT_OK) {
            val selectedImage : Uri? = data?.data
            if (selectedImage != null) {
                CropImage.activity(selectedImage)
                    .start(this)
            } else {
                ivSetImage.visibility = View.GONE
            }
        } else if (requestCode == CAPTURE_IMAGE_CODE && resultCode == Activity.RESULT_OK) {
            if (cameraFilePath.isBlank()) {
                ivSetImage.visibility = View.GONE
            } else {
                CropImage.activity(Uri.parse(cameraFilePath))
                    .start(this)
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri : Uri = result.uri
                Glide.with(this@SelectImageActivity).load(resultUri).into(ivSetImage)
                selectImagePresenter.hideLoading()
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, resultUri)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                selectImagePresenter.hideLoading()
                val error = result.error
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
}
