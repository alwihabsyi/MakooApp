package com.alwihabsyi.makooap

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

@Inject
lateinit var chartStyle: BarStyle

class FirstFragment : Fragment(R.layout.fragment_first) {

    lateinit var database: DatabaseReference
    lateinit var auth: FirebaseAuth
    lateinit var userArrayList: ArrayList<DatabaseStok>
    lateinit var userArrayList2: ArrayList<DataLaporan>
    lateinit var UAL: ArrayList<DataUser>
    lateinit var list: ArrayList<BarEntry>
    lateinit var barChart: BarChart

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        //PROFILLL
        profname()
        //TV JUMLAH STOK START
        chartStyle = BarStyle(requireContext())
        list = ArrayList()
        UAL = arrayListOf<DataUser>()
        userArrayList = arrayListOf<DatabaseStok>()
        userArrayList2 = arrayListOf<DataLaporan>()
        refresh()


        //TV JUMLAH STOK END

        val stok = view.findViewById<CardView>(R.id.cv_stok)
        stok.setOnClickListener {
            val intent = Intent(view.context, StockActivity::class.java)
            startActivity(intent)
        }

        val tst = view.findViewById<CardView>(R.id.cv_lihatstok)
        tst.setOnClickListener {
            val intent = Intent(view.context, AddStock::class.java)
            startActivity(intent)
        }

        val btnhps = view.findViewById<CardView>(R.id.cv_hapusstok)
        btnhps.setOnClickListener {
            val intent = Intent(view.context, DeleteStock::class.java)
            startActivity(intent)
        }

        val jual = view.findViewById<CardView>(R.id.cv_jual)
        jual.setOnClickListener {
            val intent = Intent(view.context, Penjualan::class.java)
            startActivity(intent)
        }

        val lapor = view.findViewById<CardView>(R.id.cv_laporan)
        lapor.setOnClickListener {
            val intent = Intent(view.context, LaporanPenjualan::class.java)
            startActivity(intent)
        }

        val btnlapor = view.findViewById<ImageView>(R.id.siv_lapor)
        btnlapor.setOnClickListener {
            val intent = Intent(view.context, LaporanPenjualan::class.java)
            startActivity(intent)
        }

        //swipe to refresh
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swiperefreshlayout)
        swipeRefreshLayout.setOnRefreshListener {
            refresh()
            swipeRefreshLayout.isRefreshing = false
        }

        //PieChart Stok
        barChart = view.findViewById(R.id.barchart_stok)
        chartStyle.styleChart(barChart)
    }

    private fun refresh() {
        userArrayList.clear()
        userArrayList2.clear()
        list.clear()
        view?.findViewById<TextView>(R.id.RP)?.text = "0"
        view?.findViewById<TextView>(R.id.jumlah)?.text = "0"
        tvjumlahterjual()
        tvjumlahstok()
    }

    private fun profname() {
        val currentUser = auth.currentUser
        if(currentUser != null){
            database = FirebaseDatabase.getInstance().getReference("User")
            database.child(auth.currentUser!!.uid).get().addOnSuccessListener {
                if(it.exists()){
                    val tvuser = it.child("uname").value.toString()
                    view?.findViewById<TextView>(R.id.profname)?.text = tvuser
                }
            }
        }else{
            view?.findViewById<TextView>(R.id.profname)?.text = "null"
        }
    }

    private fun tvjumlahterjual() {

        database = FirebaseDatabase.getInstance().getReference("Laporan")
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(itemSnapshot in snapshot.children){
                        val items = itemSnapshot.getValue(DataLaporan::class.java)
                        userArrayList2.add(items!!)
                        view?.findViewById<TextView>(R.id.RP)?.text = items.jumlahbarang
                        list.clear()

                        val conv = Integer.parseInt(items.jumlahbarang)
                        val pie = conv.toFloat()
                        list.add(BarEntry(1f, pie))

                        val barDataSet= BarDataSet(list, "Jumlah Terjual")
                        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS, 255)
                        barDataSet.valueTextSize = 15f
                        barDataSet.valueTextColor = Color.BLACK

                        val barData = BarData(barDataSet)
                        barChart.data = barData
                        barChart.animateY(2000)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun tvjumlahstok() {

        database = FirebaseDatabase.getInstance().getReference("Items")
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(itemSnapshot in snapshot.children){
                        val items = itemSnapshot.getValue(DatabaseStok::class.java)
                        userArrayList.add(items!!)
                        view?.findViewById<TextView>(R.id.jumlah)?.text = userArrayList.size.toString()
                        list.clear()

                        val bar = userArrayList.size.toFloat()
                        list.add(BarEntry(3f,bar))

                        val pieDataSet= BarDataSet(list, "Jumlah Terjual, Jenis Barang")
                        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS, 255)
                        pieDataSet.valueTextSize = 15f
                        pieDataSet.valueTextColor = Color.BLACK

                        val pieData = BarData(pieDataSet)
                        barChart.data = pieData
                        barChart.animateY(2000)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}