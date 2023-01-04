package com.alwihabsyi.makooap

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alwihabsyi.makooap.databinding.ActivityLaporanPenjualanBinding
import com.alwihabsyi.makooap.databinding.ActivityPenjualanBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class LaporanPenjualan : AppCompatActivity() {

    private lateinit var binding: ActivityLaporanPenjualanBinding
    private lateinit var RecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<DataJual>
    private lateinit var laporanArrayList: ArrayList<DataLaporan>
    private lateinit var chartArrayList: ArrayList<DataJual>
    private lateinit var database: DatabaseReference
    private lateinit var databaselaporan: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var list: ArrayList<BarEntry>
    private lateinit var chartStyle: BarStyle
    private lateinit var barChart: BarChart
    private lateinit var dropDownList: ArrayList<DataJual>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaporanPenjualanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        //RV JUAL START
        RecyclerView = findViewById(R.id.rv_listjual)
        RecyclerView.layoutManager = LinearLayoutManager(this)
        RecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf<DataJual>()
        laporanArrayList = arrayListOf<DataLaporan>()

        //barchart
        chartStyle = BarStyle(this)
        chartArrayList = arrayListOf<DataJual>()
        list = ArrayList()
        barChart = binding.barchartLaporan
        chartStyle.styleChart(barChart)


        getDataJual()

        binding.btnClearallap.setOnClickListener {
            val dialogBinding = layoutInflater.inflate(R.layout.ays_dialog, null)
            val dialog = Dialog(this)
            dialog.setContentView(dialogBinding)

            dialog.setCancelable(true)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val yesbtn = dialogBinding.findViewById<Button>(R.id.btn_yes)
            yesbtn.setOnClickListener {
                database = FirebaseDatabase.getInstance().getReference("${auth.currentUser?.uid}+Laporan")
                database.get().addOnSuccessListener {
                    if (it.exists()) {
                        database.removeValue().addOnSuccessListener {
                            Toast.makeText(this, "Berhasil Menghapus", Toast.LENGTH_SHORT).show()
                            Refresh()
                        }.addOnFailureListener {
                            Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Tidak ada barang", Toast.LENGTH_SHORT).show()
                    }
                }
                dialog.dismiss()
            }

            val nobtn = dialogBinding.findViewById<Button>(R.id.btn_no)
            nobtn.setOnClickListener {
                dialog.dismiss()
            }
        }
        //swiperefresh
        binding.srlLappenjualan.setOnRefreshListener {
            Refresh()
        }
    }

    private fun Refresh() {
        onRestart()
        overridePendingTransition(0, 1)
    }

    private fun getDataJual() {

        database = FirebaseDatabase.getInstance().getReference("${auth.currentUser?.uid}+Sale")
        databaselaporan = FirebaseDatabase.getInstance().getReference("${auth.currentUser?.uid}+Laporan")
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
                    for ((i, itemSnapshot) in snapshot.children.withIndex()) {
                        val items = itemSnapshot.getValue(DataJual::class.java)
                        userArrayList.add(items!!)

                        val conv = Integer.parseInt(items.hargabarangjual.toString())
                        val pie = conv.toFloat()
                        list.add(BarEntry(i.toFloat(), pie))
                    }
                    RecyclerView.adapter = JualAdapter(userArrayList)

                    val barDataSet = BarDataSet(list, "Pendapatan")
                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS, 255)
                    barDataSet.valueTextSize = 15f
                    barDataSet.valueTextColor = Color.BLACK

                    val barData = BarData(barDataSet)
                    barChart.data = barData
                    barChart.animateY(2000)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    override fun onRestart() {
        super.onRestart()
        val intent = intent
        finish()
        startActivity(intent)
    }
}