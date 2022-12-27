package com.alwihabsyi.makooap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.alwihabsyi.makooap.databinding.ActivityAddSupplierBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddSupplier : AppCompatActivity() {

    private lateinit var binding: ActivityAddSupplierBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSupplierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSavenewsupp.setOnClickListener {
            val aidi = binding.etId.text
            val nabar = binding.etNamasupplier.text
            val jubar = binding.etJenisbrg.text
            val habar = binding.etAlamat.text
            val idsup = binding.etNotelp.text
            val id = binding.etId.text.toString()
            val namasupp = binding.etNamasupplier.text.toString()
            val jenisbrg = binding.etJenisbrg.text.toString()
            val alamatsupp = binding.etAlamat.text.toString()
            val notlp = binding.etNotelp.text.toString()

            if(aidi.isNotEmpty() && nabar.isNotEmpty() && jubar.isNotEmpty() && habar.isNotEmpty() && idsup.isNotEmpty()){
                database = FirebaseDatabase.getInstance().getReference("Supplier")
                val supplier = DataSupplier(id, namasupp, jenisbrg, alamatsupp, notlp, "0")
                database.child(id).setValue(supplier).addOnSuccessListener {
                    binding.etId.text.clear()
                    binding.etNamasupplier.text.clear()
                    binding.etJenisbrg.text.clear()
                    binding.etAlamat.text.clear()
                    binding.etNotelp.text.clear()

                    Toast.makeText(this, "Berhasil Tersimpan", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Gagal Menyimpan", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Semua Field Harus Diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}