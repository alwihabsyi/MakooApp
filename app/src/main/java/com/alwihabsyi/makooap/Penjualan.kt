package com.alwihabsyi.makooap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alwihabsyi.makooap.databinding.ActivityPenjualanBinding
import com.google.firebase.database.*

class Penjualan : AppCompatActivity() {

    private lateinit var binding: ActivityPenjualanBinding
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<DataJual>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPenjualanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //RV JUAL START
        userRecyclerView = findViewById(R.id.rv_listjual)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf<DataJual>()
        getDataJual()
    }

    private fun getDataJual() {

        database = FirebaseDatabase.getInstance().getReference("Items")
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(itemSnapshot in snapshot.children){
                        val items = itemSnapshot.getValue(DataJual::class.java)
                        userArrayList.add(items!!)
                    }
                    userRecyclerView.adapter = JualAdapter(userArrayList)
                    binding.tvAngkajual.text = userArrayList.size.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}