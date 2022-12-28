package com.alwihabsyi.makooap

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference

class MiniViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
    val namabarang = itemview.findViewById<TextView>(R.id.tv_nambar)

    fun onBindView(data: DataJual) {
        namabarang.text = data.barangjual
    }

}