package com.alwihabsyi.makooap

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.database.*

class FirstFragment : Fragment(R.layout.fragment_first) {
    lateinit var database: DatabaseReference
    lateinit var userArrayList: ArrayList<DatabaseStok>
    lateinit var userArrayList2: ArrayList<DataJual>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TV JUMLAH STOK START
        userArrayList = arrayListOf<DatabaseStok>()
        userArrayList2 = arrayListOf<DataJual>()
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

        //swipe to refresh
        view.findViewById<SwipeRefreshLayout>(R.id.swiperefreshlayout).setOnRefreshListener {
            val intent = Intent(this.context, MainActivity::class.java)
            startActivity(intent)
            activity?.overridePendingTransition(0,1)
        }

    }

    private fun tvjumlahterjual() {

        database = FirebaseDatabase.getInstance().getReference("Sale")
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(itemSnapshot in snapshot.children){
                        val items = itemSnapshot.getValue(DataJual::class.java)
                        userArrayList2.add(items!!)
                    }

                    view?.findViewById<TextView>(R.id.RP)?.text = userArrayList2.size.toString()

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