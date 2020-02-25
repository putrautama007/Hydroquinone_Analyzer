package com.putra.hydroquinoneanalyzer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.putra.hydroquinoneanalyzer.R
import com.putra.hydroquinoneanalyzer.presenter.MainPresenter
import com.putra.hydroquinoneanalyzer.view.MainView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(),View.OnClickListener,MainView {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainPresenter = MainPresenter(this)

        mainPresenter.initView()
    }

    override fun onClick(p0: View?) {
        when(p0){
            cardViewCurveList ->{
                startActivity<CurveListActivity>()
            }
            cardViewGuide ->{
                startActivity<GuideActivity>()
            }
            cardViewSampleList ->{
                startActivity<SampleListActivity>()
            }
            cardViewScan ->{
                startActivity<SelectImageActivity>()
            }
        }
    }

    override fun initView() {
        cardViewCurveList.setOnClickListener(this)
        cardViewGuide.setOnClickListener(this)
        cardViewSampleList.setOnClickListener(this)
        cardViewScan.setOnClickListener(this)
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
