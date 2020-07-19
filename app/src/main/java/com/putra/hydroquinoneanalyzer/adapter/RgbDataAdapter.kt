package com.putra.hydroquinoneanalyzer.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.putra.hydroquinoneanalyzer.R
import com.putra.hydroquinoneanalyzer.model.RgbModel

class RgbDataAdapter(
private val context: Context
) :
RecyclerView.Adapter<RgbDataAdapter.ViewHolder>() {
    private var rbgListModel: ArrayList<RgbModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return RgbDataAdapter.ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_standart_curve_sample,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return rbgListModel.size
    }

    fun setScanData(rgbModels: ArrayList<RgbModel>) {
        rbgListModel = rgbModels
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.llColorSampleRGB!!.setBackgroundColor(
            Color.rgb(
                rbgListModel[position].redValue,
                rbgListModel[position].greenValue,
                rbgListModel[position].blueValue
            )
        )
        holder.rgbColor!!.text =
            "RGB : ( ${rbgListModel[position].redValue}, ${rbgListModel[position].greenValue} , ${rbgListModel[position].blueValue} )"
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val llColorSampleRGB: LinearLayout = itemView.findViewById(R.id.llColorSampleRgb)
        val rgbColor: TextView = itemView.findViewById(R.id.tvRGBStandardCurve)
    }

}