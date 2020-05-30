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
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.putra.hydroquinoneanalyzer.R
import com.putra.hydroquinoneanalyzer.activity.TakePictureActivity.Companion.BLUE
import com.putra.hydroquinoneanalyzer.activity.TakePictureActivity.Companion.GREEN
import com.putra.hydroquinoneanalyzer.activity.TakePictureActivity.Companion.RED
import com.putra.hydroquinoneanalyzer.presenter.SelectImagePresenter
import com.putra.hydroquinoneanalyzer.view.SelectImageView
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_select_image.*
import java.io.IOException

class SelectImageActivity : AppCompatActivity(), View.OnClickListener, SelectImageView {

    private var bitmap: Bitmap? = null
    private lateinit var selectPickImageDialog: Dialog
    private lateinit var selectImagePresenter: SelectImagePresenter
    private var redValue : Int = 0
    private var greenValue : Int = 0
    private var blueValue : Int = 0

    companion object {
        private const val CAPTURE_IMAGE_CODE = 0
        private const val GALLERY_IMAGE_CODE = 1
    }

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
        when (p0) {
            btnScan -> {
                if (bitmap != null)
                    selectImagePresenter.calculateFromGallery(bitmap)
                else
                    selectImagePresenter.calculateFromCamera(redValue,greenValue,blueValue)
            }
            ivScanImage -> {
                selectImagePresenter.showPopUp(ivScanImage)
            }
            ivSetImage -> {
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
        linearLayoutColors.visibility = View.GONE
        resultColor.visibility = View.GONE
        ivSetImage.visibility = View.VISIBLE
        progressbarSelectImage.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressbarSelectImage.visibility = View.GONE
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
            selectImagePresenter.intentToTakePictureActivity()
        }
    }

    override fun intentToScanResultActivityFromGallery(bitmap: Bitmap) {
        val intent =
            Intent(this@SelectImageActivity, ScanResultActivity::class.java)
        intent.putExtra("result", bitmap)
        startActivity(intent)
    }

    override fun intentToScanResultActivityFromCamera(
        redValue: Int,
        greenValue: Int,
        blueValue: Int
    ) {
        val intent =
            Intent(this@SelectImageActivity, ScanResultActivity::class.java)
        intent.putExtra(RED, redValue)
        intent.putExtra(GREEN, greenValue)
        intent.putExtra(BLUE, blueValue)
        startActivity(intent)
    }

    override fun intentToTakePictureActivity() {
        val moveForResultIntent = Intent(this@SelectImageActivity, TakePictureActivity::class.java)
        startActivityForResult(moveForResultIntent, CAPTURE_IMAGE_CODE)
    }

    override fun showMessage() {
        Toast.makeText(
            applicationContext,
            "Tolong pilih gambar yang akan di scan",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_IMAGE_CODE && resultCode == Activity.RESULT_OK) {
            val selectedImage: Uri? = data?.data
            if (selectedImage != null) {
                CropImage.activity(selectedImage)
                    .start(this)
            } else {
                ivSetImage.visibility = View.GONE
            }
        } else if (requestCode == CAPTURE_IMAGE_CODE && resultCode == TakePictureActivity.CAPTURE_IMAGE_CODE_RESULT) {
            ivSetImage.visibility = View.INVISIBLE
            linearLayoutColors.visibility = View.VISIBLE
            resultColor.visibility = View.VISIBLE
            redValue = data!!.getIntExtra(RED,0)
            greenValue = data.getIntExtra(GREEN, 0)
            blueValue = data.getIntExtra(BLUE, 0)
            resultColor.setBackgroundColor(Color.rgb(redValue, greenValue, blueValue))
            selectImagePresenter.hideLoading()
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri: Uri = result.uri
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
