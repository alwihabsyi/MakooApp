package com.alwihabsyi.makooap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.alwihabsyi.makooap.databinding.ActivityAddJualBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class AddJual : AppCompatActivity() {

    private lateinit var binding: ActivityAddJualBinding
    private lateinit var databaseitem: DatabaseReference
    private lateinit var databasesale: DatabaseReference
    private lateinit var databaselaporan: DatabaseReference
    private lateinit var databasesupplier: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddJualBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.btnAddpenjualan.setOnClickListener {
            val aidi = binding.etIdjual.text
            val jujual = binding.etJumlahterjual.text
            val idjual = binding.etIdjual.text.toString()
            val jumlahbarangjual = binding.etJumlahterjual.text.toString()

            if (aidi.isNotEmpty() && jujual.isNotEmpty()) {
                //reference database
                databasesale = FirebaseDatabase.getInstance().getReference("${auth.currentUser?.uid}+Sale")
                databaseitem = FirebaseDatabase.getInstance().getReference("${auth.currentUser?.uid}+Items")
                databaselaporan = FirebaseDatabase.getInstance().getReference("${auth.currentUser?.uid}+Laporan")
                databasesupplier = FirebaseDatabase.getInstance().getReference("${auth.currentUser?.uid}+Supplier")
                val jumlah = Integer.parseInt(jumlahbarangjual)

                //get data barang dari Items
                databaseitem.child(idjual).get().addOnSuccessListener {
                    if (it.exists()) {
                        databasesale.child(idjual).get().addOnSuccessListener {
                            if (it.exists()) {
                                val idjual = it.child("idjual").value.toString()
                                val barangjual = it.child("barangjual").value.toString()
                                val hargajual = it.child("hargabarangjual").value.toString()
                                val idsupp = it.child("idsupp").value.toString()
                                val hg = Integer.parseInt(hargajual)
                                val jual =
                                    Integer.parseInt(it.child("jumlahbarangjual").value.toString())

                                val jufix = (jual).plus(jumlah)
                                val jumlahbarangjual = jufix.toString()
                                val hargap = (jumlah).times(hg)
                                val sale = DataJual(idjual, barangjual, jumlahbarangjual, hargajual, idsupp)

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
                                        DatabaseStok(idjual, barangjual, jumlahbarang, hargajual, idsupp)
                                    databaseitem.child(idjual).setValue(items)
                                        .addOnFailureListener {
                                            Toast.makeText(
                                                this,
                                                "Gagal Menambah",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    databasesupplier.child(idsupp).get().addOnSuccessListener{
                                        val supply =
                                            Integer.parseInt(it.child("jumlahbrg").value.toString())
                                        val namasupp = it.child("namasupp").value.toString()
                                        val jenisbrg = it.child("jenisbrg").value.toString()
                                        val alamat = it.child("alamat").value.toString()
                                        val notelp = it.child("notelp").value.toString()
                                        val jufixsup = supply - jumlah

                                        val supplier = DataSupplier(idsupp, namasupp, jenisbrg, alamat, notelp, jufixsup.toString())
                                        databasesupplier.child(idsupp).setValue(supplier).addOnFailureListener {
                                            Toast.makeText(this, "Gagal Update Data Supplier", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                    if (jufixitem == 0) {
                                        databaseitem.child(idjual).removeValue()
                                            .addOnSuccessListener {
                                                Toast.makeText(
                                                    this,
                                                    "Berhasil Menambah",
                                                    Toast.LENGTH_SHORT
                                                )
                                                    .show()
                                            }.addOnFailureListener {
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
                                        val idsupp = it.child("idsupp").value.toString()
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
                                            DatabaseStok(id, namabarang, jumlahbarang, hargabarang, idsupp)
                                        val sale =
                                            DataJual(id, namabarang, jumlahbarangjual, hargabarang, idsupp)
                                        val total = DataLaporan(jumlahbarangjual, hargabaranglapor)

                                        if (jufix > 0) {
                                            databasesale.child(id).setValue(sale)
                                                .addOnSuccessListener {
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
                                            databaselaporan.child("total").get()
                                                .addOnSuccessListener {
                                                    if (it.exists()) {
                                                        val jumlahlapor =
                                                            Integer.parseInt(it.child("jumlahbarang").value.toString())
                                                        val hargabarang =
                                                            Integer.parseInt(it.child("totalharga").value.toString())

                                                        val jumlahterlapor =
                                                            ((jumlahlapor).plus(jumlah)).toString()
                                                        val hargabaranglapor =
                                                            ((hargabarang).plus(hargap)).toString()
                                                        val totalfix = DataLaporan(
                                                            jumlahterlapor,
                                                            hargabaranglapor
                                                        )

                                                        databaselaporan.child("total")
                                                            .setValue(totalfix)
                                                            .addOnFailureListener {
                                                                Toast.makeText(
                                                                    this,
                                                                    "Gagal Menambah",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                            }
                                                    } else {
                                                        databaselaporan.child("total")
                                                            .setValue(total)
                                                            .addOnFailureListener {
                                                                Toast.makeText(
                                                                    this,
                                                                    "Gagal Menambah",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                            }
                                                    }
                                                }
                                            databasesupplier.child(idsupp).get().addOnSuccessListener{
                                                val supply =
                                                    Integer.parseInt(it.child("jumlahbrg").value.toString())
                                                val namasupp = it.child("namasupp").value.toString()
                                                val jenisbrg = it.child("jenisbrg").value.toString()
                                                val alamat = it.child("alamat").value.toString()
                                                val notelp = it.child("notelp").value.toString()
                                                val jufixsup = supply - jumlah

                                                val supplier = DataSupplier(idsupp, namasupp, jenisbrg, alamat, notelp, jufixsup.toString())
                                                databasesupplier.child(idsupp).setValue(supplier).addOnFailureListener {
                                                    Toast.makeText(this, "Gagal Update Data Supplier", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        } else if (jufix == 0) {
                                            databasesale.child(id).removeValue()
                                                .addOnSuccessListener {
                                                    Toast.makeText(
                                                        this,
                                                        "Berhasil Menambah",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                        .show()
                                                }.addOnFailureListener {
                                                    Toast.makeText(
                                                        this,
                                                        "Gagal",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                        .show()
                                                }
                                            databaseitem.child(id).removeValue()
                                                .addOnSuccessListener {
                                                    Toast.makeText(
                                                        this,
                                                        "Berhasil Menambah",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                        .show()
                                                }.addOnFailureListener {
                                                    Toast.makeText(
                                                        this,
                                                        "Gagal",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                        .show()
                                                }
                                        } else if (jufix < 0) {
                                            Toast.makeText(this, "Input Salah", Toast.LENGTH_SHORT)
                                                .show()
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