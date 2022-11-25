package com.alwihabsyi.makooap

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference

class JualViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
    val nomor = itemview.findViewById<TextView>(R.id.nomor)
    val namabarang = itemview.findViewById<TextView>(R.id.tv_namabarang)
    val jumlahbarang = itemview.findViewById<TextView>(R.id.tv_jumlahbarang)
    val hargajual = itemview.findViewById<TextView>(R.id.tv_hargabarang)

    fun onBindView(data: DataJual) {
        nomor.text= data.idjual
        namabarang.text = data.barangjual
        jumlahbarang.text = data.jumlahbarangjual
        hargajual.text = data.hargabarangjual
    }

}