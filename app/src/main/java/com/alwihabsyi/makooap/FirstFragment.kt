package com.alwihabsyi.makooap

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.database.*

class FirstFragment : Fragment(R.layout.fragment_first) {
    lateinit var barList: ArrayList<BarEntry>
    lateinit var barDataSet: BarDataSet
    lateinit var barData: BarData
    lateinit var database: DatabaseReference
    private lateinit var userArrayList: ArrayList<DatabaseStok>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TV JUMLAH STOK START
        tvjumlahstok()
        //TV JUMLAH STOK END

        //CHART START//
        barList = ArrayList()
        barList.add(BarEntry(10f, 500f))
        barList.add(BarEntry(20f, 100f))
        barList.add(BarEntry(30f, 300f))
        barList.add(BarEntry(40f, 800f))
        barList.add(BarEntry(50f, 400f))
        barList.add(BarEntry(60f, 1000f))
        barList.add(BarEntry(70f, 800f))
        barDataSet = BarDataSet(barList, "Today's Stock")
        barData = BarData(barDataSet)
        view.findViewById<BarChart>(R.id.barChart).data = BarData(barDataSet)
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS, 250)
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 15f
        //CHART END//

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

    }

    private fun tvjumlahstok() {
        database = FirebaseDatabase.getInstance().getReference("Items")
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(itemSnapshot in snapshot.children){
                        val items = itemSnapshot.getValue(DatabaseStok::class.java)
                        userArrayList.add(items!!)
                    }

                    view?.findViewById<TextView>(R.id.jumlah)?.text = userArrayList.size.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}