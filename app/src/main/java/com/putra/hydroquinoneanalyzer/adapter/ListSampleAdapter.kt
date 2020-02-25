package com.putra.hydroquinoneanalyzer.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.putra.hydroquinoneanalyzer.R
import com.putra.hydroquinoneanalyzer.model.ScanModel
import com.putra.hydroquinoneanalyzer.activity.DetailSampleDataActivity

class ListSampleAdapter(
    private val context: Context,
    private var scanListModel: ArrayList<ScanModel>
) :
    RecyclerView.Adapter<ListSampleAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_sample,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return scanListModel.size
    }

    fun setScanData(scanModels: ArrayList<ScanModel>) {
        scanListModel = scanModels
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val scanModel: ScanModel = scanListModel[position]
        holder.llColorSample!!.setBackgroundColor(
            Color.rgb(
                scanModel.red,
                scanModel.green,
                scanModel.blue
            )
        )
        holder.tvSampleName?.text = scanModel.sampleName
        holder.tvSampleColorRGB!!.text =
            "RGB : " + "(" + scanModel.red + "," + scanModel.green + "," + scanModel.blue + ")"
        holder.tvSampleStatus!!.text =
            context.resources.getString(R.string.status) + scanModel.status
        holder.cvScanData.setOnClickListener {
            val intent = Intent(context, DetailSampleDataActivity::class.java)
            intent.putExtra("sampleId", scanModel.scanId)
            context.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val llColorSample = itemView.findViewById<LinearLayout?>(R.id.llColorSample)
        val tvSampleName = itemView.findViewById<TextView?>(R.id.tvNameSample)
        val tvSampleColorRGB = itemView.findViewById<TextView?>(R.id.tvRGB)
        val tvSampleStatus = itemView.findViewById<TextView?>(R.id.tvStatus)
        val cvScanData = itemView.findViewById<CardView>(R.id.cvScanData)
    }
}