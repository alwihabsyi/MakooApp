package com.alwihabsyi.makooap

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference

class StokViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
    private lateinit var database: DatabaseReference
    val nomor = itemview.findViewById<TextView>(R.id.nomor)
    val namabarang = itemview.findViewById<TextView>(R.id.tv_namabarang)
    val jumlahbarang = itemview.findViewById<TextView>(R.id.tv_jumlahbarang)

    fun onBindView(data: DatabaseStok) {
        nomor.text= data.id
        namabarang.text = data.namabarang
        jumlahbarang.text = data.jumlahbarang
    }

}