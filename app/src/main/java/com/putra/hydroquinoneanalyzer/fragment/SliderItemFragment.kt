package com.putra.hydroquinoneanalyzer.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.putra.hydroquinoneanalyzer.R
import kotlinx.android.synthetic.main.fragment_slider_item.*


class SliderItemFragment : Fragment() {


    private var position : Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            position = arguments?.getInt(ARG_POSITION);
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slider_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvTitle.setText(PAGE_TITLES[position!!]);
        // set page sub title text
        tvDescription.setText(PAGE_TEXT[position!!]);
        // set page image
        ivIllustration.setImageResource(PAGE_IMAGE[position!!]);
    }
    companion object{
        private val ARG_POSITION = "slider-position"
        @StringRes
        private val PAGE_TITLES =
            intArrayOf(R.string.pick_photo, R.string.calculation_proses, R.string.show_result)
        @StringRes
        private val PAGE_TEXT = intArrayOf(
            R.string.choose_photo_info, R.string.calculation_info, R.string.result_info)

        @StringRes
        private val PAGE_IMAGE = intArrayOf(
            R.drawable.ic_choose_photo, R.drawable.ic_calculator, R.drawable.ic_result
        )

        fun newInstance(position: Int): Fragment? {
            val fragment = SliderItemFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }
}
