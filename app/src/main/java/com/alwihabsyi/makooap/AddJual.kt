package com.alwihabsyi.makooap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.alwihabsyi.makooap.databinding.ActivityAddJualBinding
import com.alwihabsyi.makooap.databinding.ActivityAddStockBinding
import com.alwihabsyi.makooap.databinding.ActivityPenjualanBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue

class AddJual : AppCompatActivity() {

    private lateinit var binding: ActivityAddJualBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddJualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddpenjualan.setOnClickListener {
            val idjual = binding.etIdjual.text.toString()
            val jumlahbarangjual = binding.etJumlahterjual.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Sale")
            val sale = DataJual(idjual,jumlahbarangjual)
            database.child(idjual).setValue(sale).addOnSuccessListener {
                binding.etIdjual.text.clear()
                binding.etJumlahterjual.text.clear()

                Toast.makeText(this, "Berhasil Menambah", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Gagal Menambah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}