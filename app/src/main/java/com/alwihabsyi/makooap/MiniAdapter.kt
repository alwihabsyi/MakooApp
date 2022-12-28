package com.alwihabsyi.makooap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MiniAdapter(private val dataset : ArrayList<DataJual>): RecyclerView.Adapter<MiniViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiniViewHolder {
        return MiniViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_jualkecil,parent,false)
        )
    }

    override fun onBindViewHolder(holder: MiniViewHolder, position: Int) {
        holder.onBindView(dataset[position])
    }

    override fun getItemCount(): Int = dataset.size

}