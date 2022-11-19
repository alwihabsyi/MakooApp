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
import com.alwihabsyi.makooap.databinding.ActivityStockBinding
import com.google.firebase.database.*

class StockActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStockBinding
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<DatabaseStok>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //RV STOK START
        userRecyclerView = findViewById(R.id.rv_stoklist)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf<DatabaseStok>()
        getItemsData()


        //INTENT
        val btnstok = findViewById<Button>(R.id.instok)
        btnstok.setOnClickListener {
            val intent = Intent(this@StockActivity, AddStock::class.java)
            startActivity(intent)
        }

    }

    private fun getItemsData(){

        database = FirebaseDatabase.getInstance().getReference("Items")
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(itemSnapshot in snapshot.children){
                        val items = itemSnapshot.getValue(DatabaseStok::class.java)
                        userArrayList.add(items!!)
                    }
                    userRecyclerView.adapter = StokAdapter(userArrayList)
                    binding.tvAngkatotal.text = userArrayList.size.toString()
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