package com.alwihabsyi.makooap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class JualAdapter(private val dataset: ArrayList<DataJual>): RecyclerView.Adapter<JualViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JualViewHolder {
        return JualViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_lv,parent,false)
        )
    }

    override fun onBindViewHolder(holder: JualViewHolder, position: Int) {
        holder.onBindView(dataset[position])
    }

    override fun getItemCount(): Int = dataset.size

}