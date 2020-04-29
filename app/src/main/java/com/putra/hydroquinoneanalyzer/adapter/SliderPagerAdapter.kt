package com.putra.hydroquinoneanalyzer.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.putra.hydroquinoneanalyzer.fragment.SliderItemFragment


class SliderPagerAdapter(fm: FragmentManager, behavior: Int) :
    FragmentPagerAdapter(fm, behavior) {
    override fun getItem(position: Int): Fragment {
        return SliderItemFragment.newInstance(position)!!
    }

    override fun getCount(): Int {
        return 3
    }
}