package com.alwihabsyi.makooap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SuppAdapter(private val dataset : ArrayList<DataSupplier>, val eventHandling: (DataSupplier) -> Unit): RecyclerView.Adapter<SuppViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuppViewHolder {
        return SuppViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_stoklv,parent,false), eventHandling
        )
    }

    override fun onBindViewHolder(holder: SuppViewHolder, position: Int) {
        holder.onBindView(dataset[position])
    }

    override fun getItemCount(): Int = dataset.size
}