package com.putra.hydroquinoneanalyzer.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.putra.hydroquinoneanalyzer.R
import com.putra.hydroquinoneanalyzer.model.RgbModel
import com.putra.hydroquinoneanalyzer.presenter.StandardCurveCalculationResultPresenter
import com.putra.hydroquinoneanalyzer.view.StandardCurveCalculationResultView
import kotlinx.android.synthetic.main.activity_standard_curve_calculation_result.*


class StandardCurveCalculationResultActivity : AppCompatActivity(),StandardCurveCalculationResultView {
    private var rbgListModel: ArrayList<RgbModel> = ArrayList()
    private lateinit var listDataPointRedValue : Array<DataPoint?>
    private lateinit var listDataPointGreenValue : Array<DataPoint?>
    private lateinit var listDataPointBlueValue : Array<DataPoint?>
    private lateinit var standardCurveCalculationResultPresenter: StandardCurveCalculationResultPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standard_curve_calculation_result)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        rbgListModel = intent.getParcelableArrayListExtra("rgbData")
        standardCurveCalculationResultPresenter = StandardCurveCalculationResultPresenter(this)
        listDataPointRedValue = arrayOfNulls(rbgListModel.size)
        listDataPointGreenValue = arrayOfNulls(rbgListModel.size)
        listDataPointBlueValue = arrayOfNulls(rbgListModel.size)
        for(rgbData in rbgListModel.indices){
            listDataPointRedValue[rgbData] = DataPoint(rbgListModel[rgbData].concentration,rbgListModel[rgbData].redValue.toDouble())
            listDataPointGreenValue[rgbData] = DataPoint(rbgListModel[rgbData].concentration,rbgListModel[rgbData].greenValue.toDouble())
            listDataPointBlueValue[rgbData] = DataPoint(rbgListModel[rgbData].concentration,rbgListModel[rgbData].blueValue.toDouble())
        }
        standardCurveCalculationResultPresenter.initView()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun initView() {
        val redValueSeries: LineGraphSeries<DataPoint?> = LineGraphSeries(listDataPointRedValue)
        val greenValueSeries: LineGraphSeries<DataPoint?> = LineGraphSeries(listDataPointGreenValue)
        val blueValueSeries: LineGraphSeries<DataPoint?> = LineGraphSeries(listDataPointBlueValue)
        graphViewStandardCurveRed.addSeries(redValueSeries)
        redValueSeries.color = Color.RED
        graphViewStandardCurveGreen.addSeries(greenValueSeries)
        greenValueSeries.color = Color.GREEN
        graphViewStandardCurveBlue.addSeries(blueValueSeries)
        blueValueSeries.color = Color.BLUE
    }
}