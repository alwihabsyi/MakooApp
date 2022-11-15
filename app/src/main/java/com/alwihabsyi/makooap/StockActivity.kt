package com.alwihabsyi.makooap

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StockActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)

        //RV STOK START
        val stokList = mutableListOf<DatabaseStok>()

        val rvStok = findViewById<RecyclerView>(R.id.rv_stoklist)
        val rvAdapter = StokAdapter()

        rvStok.layoutManager = LinearLayoutManager(this)
        rvStok.adapter = rvAdapter
        rvAdapter.setNewData(stokList)

        findViewById<TextView>(R.id.tv_angkatotal).text = stokList.size.toString()

        //INTENT
        val btnstok = findViewById<Button>(R.id.instok)
        btnstok.setOnClickListener {
            val intent = Intent(this@StockActivity, AddStock::class.java)
            startActivity(intent)
        }

    }
}