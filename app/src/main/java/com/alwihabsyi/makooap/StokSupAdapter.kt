package com.alwihabsyi.makooap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class StokSupAdapter(private val dataset : ArrayList<DatabaseStok>): RecyclerView.Adapter<StokSupViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StokSupViewHolder {
        return StokSupViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adaptersup,parent,false)
        )
    }

    override fun onBindViewHolder(holder: StokSupViewHolder, position: Int) {
        holder.onBindView(dataset[position])
    }

    override fun getItemCount(): Int = dataset.size

}