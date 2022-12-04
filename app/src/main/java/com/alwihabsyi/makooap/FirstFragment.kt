package com.alwihabsyi.makooap

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.google.firebase.database.*

class FirstFragment : Fragment(R.layout.fragment_first) {

    lateinit var database: DatabaseReference
    lateinit var userArrayList: ArrayList<DatabaseStok>
    lateinit var userArrayList2: ArrayList<DataLaporan>
    lateinit var list: ArrayList<PieEntry>
    lateinit var piechart: PieChart

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TV JUMLAH STOK START
        list = ArrayList()
        userArrayList = arrayListOf<DatabaseStok>()
        userArrayList2 = arrayListOf<DataLaporan>()
        tvjumlahstok()
        tvjumlahterjual()


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
            userArrayList.clear()
            userArrayList2.clear()
            list.clear()
            view.findViewById<TextView>(R.id.RP)?.text = "0"
            view.findViewById<TextView>(R.id.jumlah)?.text = "0"
            tvjumlahterjual()
            tvjumlahstok()
            swipeRefreshLayout.isRefreshing = false
        }

        //PieChart Stok
        piechart = view.findViewById(R.id.piechart_stok)
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
                        list.add(PieEntry(pie,"Terjual"))

                        val pieDataSet= PieDataSet(list, "")
                        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS, 255)
                        pieDataSet.valueTextSize = 15f
                        pieDataSet.valueTextColor = Color.BLACK

                        val pieData = PieData(pieDataSet)
                        piechart.data = pieData
                        piechart.description.text = ""
                        piechart.centerText = "Stok"
                        piechart.animateY(2000)
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

                        val pie = userArrayList.size.toFloat()
                        list.add(PieEntry(pie,"Jenis"))

                        val pieDataSet= PieDataSet(list, "")
                        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS, 255)
                        pieDataSet.valueTextSize = 15f
                        pieDataSet.valueTextColor = Color.BLACK

                        val pieData = PieData(pieDataSet)
                        piechart.data = pieData
                        piechart.description.text = ""
                        piechart.centerText = "Stok"
                        piechart.animateY(2000)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}