package com.alwihabsyi.makooap

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SuppViewHolder(itemview: View, val eventHandling: (DataSupplier) -> Unit): RecyclerView.ViewHolder(itemview) {
    val id = itemview.findViewById<TextView>(R.id.idsupp)
    val namasupplier = itemview.findViewById<TextView>(R.id.tv_namasupp)
    val jumlahbarang = itemview.findViewById<TextView>(R.id.tv_jumlahsupp)

    fun onBindView(data: DataSupplier) {
        id.text= data.idsupp
        namasupplier.text = data.namasupp
        jumlahbarang.text = data.jumlahbrg

        itemView.setOnClickListener { eventHandling(data) }
    }

}