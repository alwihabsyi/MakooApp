package com.alwihabsyi.makooap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class StokAdapter(private val dataset : ArrayList<DatabaseStok>): RecyclerView.Adapter<StokViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StokViewHolder {
        return StokViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_lv,parent,false)
        )
    }

    override fun onBindViewHolder(holder: StokViewHolder, position: Int) {
        holder.onBindView(dataset[position])
    }

    override fun getItemCount(): Int = dataset.size

}