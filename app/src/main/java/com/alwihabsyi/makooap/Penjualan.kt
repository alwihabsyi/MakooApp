package com.alwihabsyi.makooap

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alwihabsyi.makooap.databinding.ActivityPenjualanBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class Penjualan : AppCompatActivity() {

    private lateinit var binding: ActivityPenjualanBinding
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<DataJual>
    private lateinit var database: DatabaseReference
    private lateinit var databaselaporan: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPenjualanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        //RV JUAL START
        userRecyclerView = findViewById(R.id.rv_mini)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf<DataJual>()
        getDataJual()

        binding.btnClearall.setOnClickListener {
            val dialogBinding = layoutInflater.inflate(R.layout.ays_dialog, null)
            val dialog = Dialog(this)
            dialog.setContentView(dialogBinding)

            dialog.setCancelable(true)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val yesbtn = dialogBinding.findViewById<Button>(R.id.btn_yes)
            yesbtn.setOnClickListener {
                database = FirebaseDatabase.getInstance().getReference("${auth.currentUser?.uid}+Sale")
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

        binding.btnAddjual.setOnClickListener {
            val intent = Intent(this@Penjualan, AddJual::class.java)
            startActivity(intent)
        }
    }

    private fun Refresh(){
        onRestart()
        overridePendingTransition(0,1)
    }

    private fun getDataJual() {

        databaselaporan = FirebaseDatabase.getInstance().getReference("${auth.currentUser?.uid}+Laporan")
        database = FirebaseDatabase.getInstance().getReference("${auth.currentUser?.uid}+Sale")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (itemSnapshot in snapshot.children) {
                        val items = itemSnapshot.getValue(DataJual::class.java)
                        userArrayList.add(items!!)
                    }
                    userRecyclerView.adapter = MiniAdapter(userArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        databaselaporan.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(itemSnapshot in snapshot.children) {
                        val items = itemSnapshot.getValue(DataLaporan::class.java)
                        binding.tvAngkajual.text = items?.jumlahbarang
                    }
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