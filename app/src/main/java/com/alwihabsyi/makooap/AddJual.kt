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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddJualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddpenjualan.setOnClickListener {
            val aidi = binding.etIdjual.text
            val jujual = binding.etJumlahterjual.text
            val idjual = binding.etIdjual.text.toString()
            val jumlahbarangjual = binding.etJumlahterjual.text.toString()
            val jumlah = Integer.parseInt(jumlahbarangjual)

            if (aidi.isNotEmpty() && jujual.isNotEmpty()) {
                //reference database
                databasesale = FirebaseDatabase.getInstance().getReference("Sale")
                databaseitem = FirebaseDatabase.getInstance().getReference("Items")

                //get data barang dari Items
                databaseitem.child(idjual).get().addOnSuccessListener {
                    if(it.exists()){
                        databasesale.child(idjual).get().addOnSuccessListener {
                            if(it.exists()){
                                val idjual = it.child("idjual").value.toString()
                                val barangjual = it.child("barangjual").value.toString()
                                val jual = Integer.parseInt(it.child("jumlahbarangjual").value.toString())

                                val jufix = (jual).plus(jumlah)
                                val jumlahbarangjual = jufix.toString()
                                val sale = DataJual(idjual,barangjual, jumlahbarangjual)

                                databasesale.child(idjual).setValue(sale).addOnSuccessListener {
                                    binding.etIdjual.text.clear()
                                    binding.etJumlahterjual.text.clear()
                                    Toast.makeText(this, "Berhasil Menambah", Toast.LENGTH_SHORT).show()
                                }.addOnFailureListener {
                                    Toast.makeText(this, "Gagal Menambah", Toast.LENGTH_SHORT).show()
                                }
                                databaseitem.child(idjual).get().addOnSuccessListener{
                                    val jual = Integer.parseInt(it.child("jumlahbarang").value.toString())
                                    val jufixitem = (jual).minus(jumlah)
                                    val id = idjual
                                    val namabarang = barangjual
                                    val jumlahbarang = jufixitem.toString()
                                    val items = DatabaseStok(id, namabarang, jumlahbarang)
                                    databaseitem.child(idjual).setValue(items).addOnFailureListener {
                                        Toast.makeText(this, "Gagal Menambah", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                            else{
                                databaseitem.child(idjual).get().addOnSuccessListener{
                                    if(it.exists()){
                                        val id = it.child("id").value.toString()
                                        val namabarang2 = it.child("namabarang").value.toString()
                                        val jual = Integer.parseInt(it.child("jumlahbarang").value.toString())

                                        //mengurangi jumlah barang dengan barang terjual

                                        val jufix = (jual).minus(jumlah)

                                        //update barang

                                        val namabarang = namabarang2
                                        val jumlahbarang = jufix.toString()
                                        val items = DatabaseStok(id, namabarang, jumlahbarang)
                                        val sale = DataJual(id, namabarang, jumlahbarangjual)

                                        databasesale.child(id).setValue(sale).addOnSuccessListener {
                                            binding.etIdjual.text.clear()
                                            binding.etJumlahterjual.text.clear()
                                            Toast.makeText(this, "Berhasil Menambah", Toast.LENGTH_SHORT).show()
                                        }.addOnFailureListener {
                                            Toast.makeText(this, "Gagal Menambah", Toast.LENGTH_SHORT).show()
                                        }
                                        databaseitem.child(id).setValue(items).addOnFailureListener {
                                            Toast.makeText(this, "Gagal Menambah", Toast.LENGTH_SHORT).show()
                                        }

                                        //apabila barang - dari 1 maka dihapus
                                        if (jufix == 0) {
                                            databasesale.child(id).removeValue().addOnFailureListener {
                                                Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                                            }
                                            databaseitem.child(id).removeValue().addOnFailureListener {
                                                Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }else{
                        Toast.makeText(this, "Barang Tidak Ada", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Semua Field Harus Diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}