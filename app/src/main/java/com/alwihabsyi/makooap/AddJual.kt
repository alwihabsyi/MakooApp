package com.alwihabsyi.makooap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.alwihabsyi.makooap.databinding.ActivityAddJualBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddJual : AppCompatActivity() {

    private lateinit var binding: ActivityAddJualBinding
    private lateinit var databaseitem: DatabaseReference
    private lateinit var databasesale: DatabaseReference
    private lateinit var databaselaporan: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddJualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddpenjualan.setOnClickListener {
            val aidi = binding.etIdjual.text
            val jujual = binding.etJumlahterjual.text
            val idjual = binding.etIdjual.text.toString()
            val jumlahbarangjual = binding.etJumlahterjual.text.toString()

            if (aidi.isNotEmpty() && jujual.isNotEmpty()) {
                //reference database
                databasesale = FirebaseDatabase.getInstance().getReference("Sale")
                databaseitem = FirebaseDatabase.getInstance().getReference("Items")
                databaselaporan = FirebaseDatabase.getInstance().getReference("Laporan")
                val jumlah = Integer.parseInt(jumlahbarangjual)

                //get data barang dari Items
                databaseitem.child(idjual).get().addOnSuccessListener {
                    if (it.exists()) {
                        databasesale.child(idjual).get().addOnSuccessListener {
                            if (it.exists()) {
                                val idjual = it.child("idjual").value.toString()
                                val barangjual = it.child("barangjual").value.toString()
                                val hargajual = it.child("hargabarangjual").value.toString()
                                val hg = Integer.parseInt(hargajual)
                                val jual =
                                    Integer.parseInt(it.child("jumlahbarangjual").value.toString())

                                val jufix = (jual).plus(jumlah)
                                val jumlahbarangjual = jufix.toString()
                                val hargap = (jumlah).times(hg)
                                val sale = DataJual(idjual, barangjual, jumlahbarangjual, hargajual)

                                databasesale.child(idjual).setValue(sale).addOnSuccessListener {
                                    binding.etIdjual.text.clear()
                                    binding.etJumlahterjual.text.clear()
                                    Toast.makeText(this, "Berhasil Menambah", Toast.LENGTH_SHORT)
                                        .show()
                                }.addOnFailureListener {
                                    Toast.makeText(this, "Gagal Menambah", Toast.LENGTH_SHORT)
                                        .show()
                                }
                                databaselaporan.child("total").get().addOnSuccessListener {
                                    val jumlahlapor =
                                        Integer.parseInt(it.child("jumlahbarang").value.toString())
                                    val hargabarang =
                                        Integer.parseInt(it.child("totalharga").value.toString())

                                    val jumlahterlapor = (jumlahlapor).plus(jumlah)
                                    val hargabaranglapor = (hargabarang).plus(hargap)

                                    val jumpor = jumlahterlapor.toString()
                                    val hapor = hargabaranglapor.toString()
                                    val total = DataLaporan(jumpor, hapor)

                                    databaselaporan.child("total").setValue(total)
                                        .addOnFailureListener {
                                            Toast.makeText(
                                                this,
                                                "Gagal Menambah",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                }
                                databaseitem.child(idjual).get().addOnSuccessListener {
                                    val jual =
                                        Integer.parseInt(it.child("jumlahbarang").value.toString())
                                    val jufixitem = (jual).minus(jumlah)
                                    val jumlahbarang = jufixitem.toString()
                                    val items =
                                        DatabaseStok(idjual, barangjual, jumlahbarang, hargajual)
                                    databaseitem.child(idjual).setValue(items)
                                        .addOnFailureListener {
                                            Toast.makeText(
                                                this,
                                                "Gagal Menambah",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    if (jufix == 0) {
                                        databaseitem.child(idjual).removeValue()
                                            .addOnFailureListener {
                                                Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT)
                                                    .show()
                                            }
                                    }
                                }
                            } else {
                                databaseitem.child(idjual).get().addOnSuccessListener {
                                    if (it.exists()) {
                                        val id = it.child("id").value.toString()
                                        val namabarang2 = it.child("namabarang").value.toString()
                                        val hargabarang = it.child("hargabarang").value.toString()
                                        val jual =
                                            Integer.parseInt(it.child("jumlahbarang").value.toString())
                                        val hg = Integer.parseInt(hargabarang)

                                        //mengurangi jumlah barang dengan barang terjual

                                        val jufix = (jual).minus(jumlah)

                                        //operasi tambah kali utk laporan
                                        val hargap = (jumlah).times(hg)
                                        val hargabaranglapor = hargap.toString()

                                        //update barang

                                        val namabarang = namabarang2
                                        val jumlahbarang = jufix.toString()
                                        val items =
                                            DatabaseStok(id, namabarang, jumlahbarang, hargabarang)
                                        val sale =
                                            DataJual(id, namabarang, jumlahbarangjual, hargabarang)
                                        val total = DataLaporan(jumlahbarangjual, hargabaranglapor)

                                        databasesale.child(id).setValue(sale).addOnSuccessListener {
                                            binding.etIdjual.text.clear()
                                            binding.etJumlahterjual.text.clear()
                                            Toast.makeText(
                                                this,
                                                "Berhasil Menambah",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }.addOnFailureListener {
                                            Toast.makeText(
                                                this,
                                                "Gagal Menambah",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        databaseitem.child(id).setValue(items)
                                            .addOnFailureListener {
                                                Toast.makeText(
                                                    this,
                                                    "Gagal Menambah",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }

                                        //untuk laporan
                                        databaselaporan.child("total").get().addOnSuccessListener {
                                            if(it.exists()){
                                                val jumlahlapor =
                                                    Integer.parseInt(it.child("jumlahbarang").value.toString())
                                                val hargabarang =
                                                    Integer.parseInt(it.child("totalharga").value.toString())

                                                val jumlahterlapor = ((jumlahlapor).plus(jumlah)).toString()
                                                val hargabaranglapor = ((hargabarang).plus(hargap)).toString()
                                                val totalfix = DataLaporan(jumlahterlapor, hargabaranglapor)

                                                databaselaporan.child("total").setValue(totalfix)
                                                    .addOnFailureListener {
                                                        Toast.makeText(
                                                            this,
                                                            "Gagal Menambah",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                            }else{
                                                databaselaporan.child("total").setValue(total)
                                                    .addOnFailureListener {
                                                        Toast.makeText(
                                                            this,
                                                            "Gagal Menambah",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                            }
                                        }

                                        //apabila barang - dari 1 maka dihapus
                                        if (jufix == 0) {
                                            databasesale.child(id).removeValue()
                                                .addOnFailureListener {
                                                    Toast.makeText(
                                                        this,
                                                        "Gagal",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            databaseitem.child(id).removeValue()
                                                .addOnFailureListener {
                                                    Toast.makeText(
                                                        this,
                                                        "Gagal",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Toast.makeText(this, "Barang Tidak Ada", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Semua Field Harus Diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}