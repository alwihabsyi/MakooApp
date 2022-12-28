package com.alwihabsyi.makooap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.renderscript.Sampler.Value
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.alwihabsyi.makooap.databinding.ActivityAddStockBinding
import com.alwihabsyi.makooap.databinding.ActivityMainBinding
import com.alwihabsyi.makooap.databinding.ActivityStockBinding
import com.google.firebase.database.*

class AddStock : AppCompatActivity() {

    private lateinit var binding: ActivityAddStockBinding
    private lateinit var database: DatabaseReference
    private lateinit var database2: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStockBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance().getReference("Items")

        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (itemSnapshot in snapshot.children){
                        val items = itemSnapshot.getValue(DatabaseStok::class.java)
                        binding.etId.addTextChangedListener{
                            if(binding.etId.text.toString() == items?.id.toString()){
                                binding.etNamabarang.setText("${items?.namabarang}")
                                binding.etHarga.setText("${items?.hargabarang}")
                                binding.etJumlah.setText("${items?.jumlahbarang}")
                                binding.etIdsupp.setText("${items?.idsupp}")
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

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

            if (aidi.isNotEmpty() && nabar.isNotEmpty() && jubar.isNotEmpty() && habar.isNotEmpty() && idsup.isNotEmpty()) {
                database2 = FirebaseDatabase.getInstance().getReference("Supplier")
                database = FirebaseDatabase.getInstance().getReference("Items")
                database.child(id).get().addOnSuccessListener {
                    if (it.exists()) {
                        val jumlahbrang =
                            Integer.parseInt(it.child("jumlahbarang").value.toString())
                        database2.child(idsupplier).get().addOnSuccessListener {
                            if (it.exists()) {
                                val namasupp = it.child("namasupp").value.toString()
                                val jenisbrg = it.child("jenisbrg").value.toString()
                                val alamat = it.child("alamat").value.toString()
                                val notelp = it.child("notelp").value.toString()
                                val idsupp = it.child("idsupp").value.toString()
                                val jumlahsup =
                                    Integer.parseInt(it.child("jumlahbrg").value.toString())

                                val jufix = jumlahsup - jumlahbrang + Integer.parseInt(jumlahbarang)

                                val supplier = DataSupplier(
                                    idsupplier,
                                    namasupp,
                                    jenisbrg,
                                    alamat,
                                    notelp,
                                    jufix.toString()
                                )
                                database2.child(idsupplier).setValue(supplier)
                                    .addOnFailureListener {
                                        Toast.makeText(
                                            this,
                                            "Gagal Update Data Supplier",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                val items = DatabaseStok(
                                    id,
                                    namabarang,
                                    jumlahbarang,
                                    hargabarang,
                                    idsupplier
                                )
                                database.child(id).setValue(items).addOnSuccessListener {
                                    binding.etId.text.clear()
                                    binding.etNamabarang.text.clear()
                                    binding.etJumlah.text.clear()
                                    binding.etHarga.text.clear()
                                    binding.etIdsupp.text.clear()

                                    Toast.makeText(this, "Berhasil Tersimpan", Toast.LENGTH_SHORT)
                                        .show()
                                }.addOnFailureListener {
                                    Toast.makeText(this, "Gagal Menyimpan", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            } else {
                                Toast.makeText(this, "Supplier Tidak Terdaftar", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    } else {
                        database2.child(idsupplier).get().addOnSuccessListener {
                            if (it.exists()) {
                                val namasupp = it.child("namasupp").value.toString()
                                val jenisbrg = it.child("jenisbrg").value.toString()
                                val alamat = it.child("alamat").value.toString()
                                val notelp = it.child("notelp").value.toString()
                                val idsupp = it.child("idsupp").value.toString()
                                val jumlahsup =
                                    Integer.parseInt(it.child("jumlahbrg").value.toString())

                                val jufix = jumlahsup + Integer.parseInt(jumlahbarang)

                                val supplier = DataSupplier(
                                    idsupplier,
                                    namasupp,
                                    jenisbrg,
                                    alamat,
                                    notelp,
                                    jufix.toString()
                                )
                                database2.child(idsupplier).setValue(supplier)
                                    .addOnFailureListener {
                                        Toast.makeText(
                                            this,
                                            "Gagal Update Data Supplier",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                val items = DatabaseStok(
                                    id,
                                    namabarang,
                                    jumlahbarang,
                                    hargabarang,
                                    idsupplier
                                )
                                database.child(id).setValue(items).addOnSuccessListener {
                                    binding.etId.text.clear()
                                    binding.etNamabarang.text.clear()
                                    binding.etJumlah.text.clear()
                                    binding.etHarga.text.clear()
                                    binding.etIdsupp.text.clear()

                                    Toast.makeText(this, "Berhasil Tersimpan", Toast.LENGTH_SHORT)
                                        .show()
                                }.addOnFailureListener {
                                    Toast.makeText(this, "Gagal Menyimpan", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            } else {
                                Toast.makeText(this, "Supplier Tidak Terdaftar", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Semua Field Harus Diisi", Toast.LENGTH_SHORT).show()
            }
        }

        binding.klikup.setOnClickListener {
            val intent = Intent(this@AddStock, DeleteStock::class.java)
            startActivity(intent)
        }
    }
}