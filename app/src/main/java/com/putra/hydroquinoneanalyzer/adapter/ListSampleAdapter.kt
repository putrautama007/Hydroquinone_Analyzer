package com.putra.hydroquinoneanalyzer.adapter

import android.annotation.SuppressLint
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
    private val context: Context
) :
    RecyclerView.Adapter<ListSampleAdapter.ViewHolder>() {

    private var scanListModel: ArrayList<ScanModel> = ArrayList()

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

    fun filterSampleList(filteredSampleList: ArrayList<ScanModel>) {
        this.scanListModel = filteredSampleList
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
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
            "RGB : ( ${scanModel.red}, ${scanModel.green} , ${scanModel.blue} )"
        if(scanModel.status?.contains("Tidak Layak Pakai")!!){
            holder.tvSampleStatus!!.text = "Status : ${scanModel.status!!.toLowerCase()} karena konsentrasi melebihi dari 0.02 ppm"
        }else{
            holder.tvSampleStatus!!.text = "Status : ${scanModel.status!!.toLowerCase()} karena konsentrasi kurang dari 0.02 ppm"
        }

        holder.cvScanData.setOnClickListener {
            val intent = Intent(context, DetailSampleDataActivity::class.java)
            intent.putExtra("sampleId", scanModel.scanId)
            context.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val llColorSample: LinearLayout = itemView.findViewById(R.id.llColorSample)
        val tvSampleName: TextView = itemView.findViewById(R.id.tvNameSample)
        val tvSampleColorRGB: TextView = itemView.findViewById(R.id.tvRGB)
        val tvSampleStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val cvScanData: CardView = itemView.findViewById(R.id.cvScanData)
    }
}