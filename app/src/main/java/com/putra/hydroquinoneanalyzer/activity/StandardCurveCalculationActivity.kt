package com.putra.hydroquinoneanalyzer.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.putra.hydroquinoneanalyzer.R
import com.putra.hydroquinoneanalyzer.adapter.RgbDataAdapter
import com.putra.hydroquinoneanalyzer.model.RgbModel
import com.putra.hydroquinoneanalyzer.presenter.StandardCurveCalculationPresenter
import com.putra.hydroquinoneanalyzer.view.StandardCurveCalculationView
import kotlinx.android.synthetic.main.activity_standard_curve_calculation.*
import org.jetbrains.anko.startActivity


class StandardCurveCalculationActivity : AppCompatActivity(),StandardCurveCalculationView {

    private var redValue : Int = 0
    private var greenValue : Int = 0
    private var blueValue : Int = 0
    private var rbgListModel: ArrayList<RgbModel> = ArrayList()
    private lateinit var standardCurveCalculationPresenter : StandardCurveCalculationPresenter
    private lateinit var rgbDataAdapter: RgbDataAdapter

    companion object {
        private const val CAPTURE_IMAGE_CODE = 0
        private const val GALLERY_IMAGE_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standard_curve_calculation)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        standardCurveCalculationPresenter = StandardCurveCalculationPresenter(this)
        standardCurveCalculationPresenter.initView()
        btnAddData.setOnClickListener {
            standardCurveCalculationPresenter.checkPermission()
        }
        btnMakeStandardCurve.setOnClickListener {
            if (rbgListModel.size < 5){
                Toast.makeText(this, "Jumlah data minimal 5 buah", Toast.LENGTH_LONG).show()
            }else{
                startActivity<StandardCurveCalculationResultActivity>("rgbData" to rbgListModel)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAPTURE_IMAGE_CODE && resultCode == TakePictureActivity.CAPTURE_IMAGE_CODE_RESULT) {
            redValue = data!!.getIntExtra(TakePictureActivity.RED,0)
            greenValue = data.getIntExtra(TakePictureActivity.GREEN, 0)
            blueValue = data.getIntExtra(TakePictureActivity.BLUE, 0)
            standardCurveCalculationPresenter.showPopup(redValue, greenValue, blueValue)
        }
    }

    override fun intentToTakePictureActivity() {
        val moveForResultIntent = Intent(this, TakePictureActivity::class.java)
        startActivityForResult(moveForResultIntent, CAPTURE_IMAGE_CODE)
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
                this,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                CAPTURE_IMAGE_CODE
            )
        } else {
            standardCurveCalculationPresenter.intentToTakePictureActivity()
        }
    }

    override fun initView() {
        recyclerViewStandardCurveRGB.layoutManager = LinearLayoutManager(this)
        rgbDataAdapter = RgbDataAdapter(this)
        recyclerViewStandardCurveRGB.adapter = rgbDataAdapter
    }

    override fun showPopup(redValue : Int, greenValue : Int, blueValue : Int) {
        val dialogBuilder: AlertDialog = AlertDialog.Builder(this).create()
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.view_rbg_data, null)
        val btnAddRgbData : Button = dialogView.findViewById(R.id.btnAddRgbData)
        val etConcentration : EditText = dialogView.findViewById(R.id.etConcentration)
        val llColorSampleRGB: LinearLayout = dialogView.findViewById(R.id.llColorRgb)
        val rgbColor: TextView = dialogView.findViewById(R.id.tvRgbData)
        llColorSampleRGB.setBackgroundColor(
            Color.rgb(
               redValue,
                greenValue,
                blueValue
            )
        )
        rgbColor.text =
            "RGB : ( $redValue, $greenValue , $blueValue )"
        btnAddRgbData.setOnClickListener {
            if(etConcentration.text.toString().isEmpty()){
                Toast.makeText(this, "Harap isi nilai konsentrasi", Toast.LENGTH_LONG).show()
            }else {
                rbgListModel.add(
                    RgbModel(
                        redValue,
                        greenValue,
                        blueValue,
                        etConcentration.text.toString().toDouble()
                    )
                )
                rgbDataAdapter.setScanData(rbgListModel)
                rgbDataAdapter.notifyDataSetChanged()
                dialogBuilder.dismiss()
            }
        }
        dialogBuilder.setView(dialogView)
        dialogBuilder.show()
    }
}