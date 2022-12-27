package com.alwihabsyi.makooap

import android.content.Intent
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
    private lateinit var database2: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSavenewstock.setOnClickListener {
            val aidi = binding.etId.text
            val nabar = binding.etNamabarang.text
            val jubar = binding.etJumlah.text
            val habar = binding.etHarga.text
            val idsup = binding.etIdsupp.text
            val id = binding.etId.text.toString()
            val namabarang = binding.etNamabarang.text.toString()
            val jumlahbarang = binding.etJumlah.text.toString()
            val idsupplier = binding.etIdsupp.text.toString()
            val hargabarang = binding.etHarga.text.toString()

            if(aidi.isNotEmpty() && nabar.isNotEmpty() && jubar.isNotEmpty() && habar.isNotEmpty() && idsup.isNotEmpty()){
                database2 = FirebaseDatabase.getInstance().getReference("Supplier")
                database = FirebaseDatabase.getInstance().getReference("Items")
                database2.child(idsupplier).get().addOnSuccessListener {
                    if(it.exists()){
                        val namasupp = it.child("namasupp").value.toString()
                        val jenisbrg = it.child("jenisbrg").value.toString()
                        val alamat = it.child("alamat").value.toString()
                        val notelp = it.child("notelp").value.toString()

                        val supplier = DataSupplier(idsupplier, namasupp, jenisbrg, alamat, notelp, jumlahbarang)
                        database2.child(idsupplier).setValue(supplier).addOnFailureListener {
                            Toast.makeText(this, "Gagal Update Data Supplier", Toast.LENGTH_SHORT).show()
                        }

                        val items = DatabaseStok(id,namabarang, jumlahbarang,hargabarang, idsupplier)
                        database.child(id).setValue(items).addOnSuccessListener {
                            binding.etId.text.clear()
                            binding.etNamabarang.text.clear()
                            binding.etJumlah.text.clear()
                            binding.etHarga.text.clear()
                            binding.etIdsupp.text.clear()

                            Toast.makeText(this, "Berhasil Tersimpan", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(this, "Gagal Menyimpan", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this, "Supplier Tidak Terdaftar",Toast.LENGTH_SHORT).show()
                    }
                }
            }else {
                Toast.makeText(this, "Semua Field Harus Diisi", Toast.LENGTH_SHORT).show()
            }
        }

        binding.klikup.setOnClickListener {
            val intent = Intent(this@AddStock, DeleteStock::class.java)
            startActivity(intent)
        }
    }
}