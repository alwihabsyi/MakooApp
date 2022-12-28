package com.alwihabsyi.makooap

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference

class StokSupViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
    val nomor = itemview.findViewById<TextView>(R.id.nomorsup)
    val namabarang = itemview.findViewById<TextView>(R.id.tv_namabarangsup)
    val jumlahbarang = itemview.findViewById<TextView>(R.id.tv_jumlahbarangsup)
    val hargajual = itemview.findViewById<TextView>(R.id.tv_hargabarangsup)

    fun onBindView(data: DatabaseStok) {
        nomor.text= data.id
        namabarang.text = data.namabarang
        jumlahbarang.text = data.jumlahbarang
        hargajual.text = data.hargabarang
    }

}