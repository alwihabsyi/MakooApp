package com.alwihabsyi.makooap

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import android.view.Window
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.alwihabsyi.makooap.databinding.ActivityMainBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var database: DatabaseReference
    private lateinit var userArrayList: ArrayList<DatabaseStok>
    private lateinit var userArrayList2: ArrayList<DataLaporan>
    private lateinit var list: ArrayList<PieEntry>
    private lateinit var piechart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        list = ArrayList()
        userArrayList = arrayListOf<DatabaseStok>()
        userArrayList2 = arrayListOf<DataLaporan>()
        piechart = findViewById(R.id.piechart_stok)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        setupSmoothBottomMenu()
    }

    private fun setupSmoothBottomMenu(){
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.menu_bottom)
        val menu = popupMenu.menu
        binding.bottomNavigationView.setupWithNavController(menu, navController)
    }

    override fun onRestart() {
        super.onRestart()
        tvjumlahstok()
        tvjumlahterjual()
    }

    private fun tvjumlahterjual() {

        database = FirebaseDatabase.getInstance().getReference("Laporan")
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(itemSnapshot in snapshot.children){
                        val items = itemSnapshot.getValue(DataLaporan::class.java)
                        userArrayList2.add(items!!)
                        findViewById<TextView>(R.id.RP)?.text = items.jumlahbarang

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
                    }

                    findViewById<TextView>(R.id.jumlah)?.text = userArrayList.size.toString()
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

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}