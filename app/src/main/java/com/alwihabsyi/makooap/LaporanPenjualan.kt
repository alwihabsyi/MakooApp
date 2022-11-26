package com.alwihabsyi.makooap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alwihabsyi.makooap.databinding.ActivityLaporanPenjualanBinding
import com.alwihabsyi.makooap.databinding.ActivityPenjualanBinding
import com.google.firebase.database.*

class LaporanPenjualan : AppCompatActivity() {

    private lateinit var binding: ActivityLaporanPenjualanBinding
    private lateinit var RecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<DataJual>
    private lateinit var laporanArrayList: ArrayList<DataLaporan>
    private lateinit var database: DatabaseReference
    private lateinit var databaselaporan: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaporanPenjualanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //RV JUAL START
        RecyclerView = findViewById(R.id.rv_listjual)
        RecyclerView.layoutManager = LinearLayoutManager(this)
        RecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf<DataJual>()
        laporanArrayList = arrayListOf<DataLaporan>()
        getDataJual()
    }

    private fun getDataJual() {

        database = FirebaseDatabase.getInstance().getReference("Sale")
        databaselaporan = FirebaseDatabase.getInstance().getReference("Laporan")
        databaselaporan.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (itemSnapshot in snapshot.children) {
                        val items = itemSnapshot.getValue(DataLaporan::class.java)
                        laporanArrayList.add(items!!)
                        binding.tvTotalterjual.text = items.jumlahbarang
                        binding.tvTotalfix.text = items.totalharga
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (itemSnapshot in snapshot.children) {
                        val items = itemSnapshot.getValue(DataJual::class.java)
                        userArrayList.add(items!!)
                    }
                    RecyclerView.adapter = JualAdapter(userArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}