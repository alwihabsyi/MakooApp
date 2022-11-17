package com.alwihabsyi.makooap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.widget.Toast
import com.alwihabsyi.makooap.databinding.ActivityAddStockBinding
import com.alwihabsyi.makooap.databinding.ActivityMainBinding
import com.alwihabsyi.makooap.databinding.ActivityStockBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddStock : AppCompatActivity() {

    private lateinit var binding: ActivityAddStockBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSavenewstock.setOnClickListener {
            val id = binding.etId.text.toString()
            val namabarang = binding.etNamabarang.text.toString()
            val jumlahbarang = binding.etJumlah.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Items")
            val items = DatabaseStok(id,namabarang, jumlahbarang)
            database.child(id).setValue(items).addOnSuccessListener {
                binding.etId.text.clear()
                binding.etNamabarang.text.clear()
                binding.etJumlah.text.clear()

                Toast.makeText(this, "Berhasil Tersimpan", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Gagal Menyimpan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}