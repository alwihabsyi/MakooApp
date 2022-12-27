package com.alwihabsyi.makooap

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference

class StokViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
    val nomor = itemview.findViewById<TextView>(R.id.nomor)
    val namabarang = itemview.findViewById<TextView>(R.id.tv_namabarang)
    val jumlahbarang = itemview.findViewById<TextView>(R.id.tv_jumlahbarang)
    val hargajual = itemview.findViewById<TextView>(R.id.tv_hargabarang)
    val idsupp = itemview.findViewById<TextView>(R.id.idsuppst)

    fun onBindView(data: DatabaseStok) {
        nomor.text= data.id
        namabarang.text = data.namabarang
        jumlahbarang.text = data.jumlahbarang
        hargajual.text = data.hargabarang
        idsupp.text = data.idsupp
    }

}