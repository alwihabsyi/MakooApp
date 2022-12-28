package com.alwihabsyi.makooap

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SuppViewHolder(itemview: View, val eventHandling: (DataSupplier) -> Unit): RecyclerView.ViewHolder(itemview) {
    val id = itemview.findViewById<TextView>(R.id.idsupp)
    val namasupplier = itemview.findViewById<TextView>(R.id.tv_namasupp)
    val jenisbarang = itemview.findViewById<TextView>(R.id.tv_jenisbrg)
    val jumlahbarang = itemview.findViewById<TextView>(R.id.tv_jumlahsupp)

    fun onBindView(data: DataSupplier) {
        id.text= data.idsupp
        namasupplier.text = data.namasupp
        jumlahbarang.text = data.jumlahbrg
        jenisbarang.text = data.jenisbrg

        itemView.setOnClickListener { eventHandling(data) }
    }

}