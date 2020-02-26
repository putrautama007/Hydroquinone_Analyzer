package com.putra.hydroquinoneanalyzer.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.paolorotolo.appintro.AppIntro
import com.github.paolorotolo.appintro.AppIntro2
import com.github.paolorotolo.appintro.AppIntro2Fragment
import com.github.paolorotolo.appintro.model.SliderPagerBuilder
import com.putra.hydroquinoneanalyzer.R
import com.putra.hydroquinoneanalyzer.presenter.GuidePresenter
import com.putra.hydroquinoneanalyzer.view.GuideView

class GuideActivity : AppIntro2(),GuideView {

    private lateinit var guidePresenter: GuidePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        guidePresenter = GuidePresenter(this)
        guidePresenter.showGuideSlides(this)
    }
    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        guidePresenter.intentToMain()
    }

    override fun intentToMain() {
        val intentToMain = Intent(this, MainActivity::class.java)
        intentToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intentToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intentToMain)
    }

    override fun showGuideSlides(context: Context) {
        val choosePhoto = SliderPagerBuilder()
            .title(getString(R.string.choose_picture))
            .titleColor(ContextCompat.getColor(context,R.color.colorPrimary))
            .description(getString(R.string.choose_photo_info))
            .descColor(ContextCompat.getColor(context,R.color.colorPrimary))
            .imageDrawable(R.drawable.ic_choose_photo)
            .bgColor(ContextCompat.getColor(context,R.color.colorBackgroundGrey))
            .build()
        val calculation = SliderPagerBuilder()
            .title(getString(R.string.calculation_prosess))
            .titleColor(ContextCompat.getColor(context,R.color.colorPrimary))
            .description(getString(R.string.calculation_info))
            .descColor(ContextCompat.getColor(context,R.color.colorPrimary))
            .imageDrawable(R.drawable.ic_calculator)
            .bgColor(ContextCompat.getColor(context,R.color.colorBackgroundGrey))
            .build()
        val result = SliderPagerBuilder()
            .title(getString(R.string.show_result))
            .titleColor(ContextCompat.getColor(context,R.color.colorPrimary))
            .description(getString(R.string.result_info))
            .descColor(ContextCompat.getColor(context,R.color.colorPrimary))
            .imageDrawable(R.drawable.ic_result)
            .bgColor(ContextCompat.getColor(context,R.color.colorBackgroundGrey))
            .build()

        addSlide(AppIntro2Fragment.newInstance(choosePhoto))
        addSlide(AppIntro2Fragment.newInstance(calculation))
        addSlide(AppIntro2Fragment.newInstance(result))

        showSkipButton(false)

//        setBarColor(ContextCompat.getColor(context,R.color.colorPrimaryDark))
//        setSeparatorColor(ContextCompat.getColor(context,R.color.colorPrimaryDark))
//        setColorDoneText(ContextCompat.getColor(context,R.color.colorPrimary))
        setIndicatorColor(ContextCompat.getColor(context,R.color.colorPrimary),ContextCompat.getColor(context,R.color.colorHint))
        showStatusBar(false)
        setFadeAnimation()
    }
}
