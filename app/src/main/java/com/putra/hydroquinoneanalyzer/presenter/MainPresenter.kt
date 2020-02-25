package com.putra.hydroquinoneanalyzer.presenter

import com.putra.hydroquinoneanalyzer.view.MainView

class MainPresenter(private val mainView: MainView) {
    fun initView(){
        mainView.initView()
    }
}