package com.putra.hydroquinoneanalyzer.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.putra.hydroquinoneanalyzer.R
import com.putra.hydroquinoneanalyzer.adapter.SliderPagerAdapter
import kotlinx.android.synthetic.main.activity_user_guide.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor


class UserGuideActivity : AppCompatActivity() {

    private lateinit var adapter: SliderPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_guide)
        supportActionBar?.hide()

        adapter = SliderPagerAdapter(
            supportFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )

        pagerUserGuideSlider.adapter =  adapter
        userGuideTabLayout.setupWithViewPager(pagerUserGuideSlider)

        userGuideButton.setOnClickListener {
            if(pagerUserGuideSlider.currentItem == 2){
                startActivity(intentFor<MainActivity>().clearTop().clearTask())
            } else {
                pagerUserGuideSlider.currentItem = pagerUserGuideSlider.currentItem + 1
            }
        }

        backUserGuide.setOnClickListener{
            startActivity(intentFor<MainActivity>().clearTop().clearTask())
        }

        pagerUserGuideSlider.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (position == adapter.count - 1) {
                    userGuideButton.setText(R.string.get_started)
                } else {
                    userGuideButton.setText(R.string.next)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

}
