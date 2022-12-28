package com.alwihabsyi.makooap

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alwihabsyi.makooap.databinding.ActivitySupplierDetailBinding
import com.google.firebase.database.*

class SupplierDetail : AppCompatActivity() {

    private lateinit var binding: ActivitySupplierDetailBinding
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<DatabaseStok>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupplierDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<DataSupplier>("user")

        //inisialisasi
        val namasupp = binding.namasupp
        val jenis = binding.jenisbrg
        val address = binding.supaddress
        val id = binding.supid
        val total = binding.jumlahbrg

        //movedata
        namasupp.text = user?.namasupp
        jenis.text = user?.jenisbrg
        address.text = user?.alamat
        id.text = user?.idsupp
        total.text = user?.jumlahbrg

        binding.btnHubsup.setOnClickListener {
            val Intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://wa.me/" + user?.notelp)
            )
            startActivity(Intent)
        }

        //recyclerview
        userRecyclerView = findViewById(R.id.rv_stoksuplist)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf<DatabaseStok>()
        getItemsData()
    }

    private fun getItemsData() {
        val user = intent.getParcelableExtra<DataSupplier>("user")
        database = FirebaseDatabase.getInstance().getReference("Items")
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(itemSnapshot in snapshot.children){
                        val items = itemSnapshot.getValue(DatabaseStok::class.java)
                        if(items?.idsupp == user?.idsupp){
                            userArrayList.add(items!!)
                        }
                    }
                    userRecyclerView.adapter = StokSupAdapter(userArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}