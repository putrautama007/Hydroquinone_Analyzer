package com.putra.hydroquinoneanalyzer.presenter

import android.content.Context
import com.putra.hydroquinoneanalyzer.view.GuideView

class GuidePresenter(private val guideView: GuideView) {
    fun showGuideSlides(context: Context){
        guideView.showGuideSlides(context)
    }

    fun intentToMain(){
        guideView.intentToMain()
    }
}