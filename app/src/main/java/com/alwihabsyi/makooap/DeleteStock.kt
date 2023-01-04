package com.alwihabsyi.makooap

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.alwihabsyi.makooap.databinding.ActivityDeleteStockBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeleteStock : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteStockBinding
    private lateinit var database: DatabaseReference
    private lateinit var databasesup: DatabaseReference
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteStockBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.btnDeletestock.setOnClickListener{
            val id = binding.etId.text.toString()
            if(id.isNotEmpty()){
                val dialogBinding = layoutInflater.inflate(R.layout.ays_dialog, null)
                val dialog = Dialog(this)
                dialog.setContentView(dialogBinding)

                dialog.setCancelable(true)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()

                val yesbtn = dialogBinding.findViewById<Button>(R.id.btn_yes)
                yesbtn.setOnClickListener {
                    database = FirebaseDatabase.getInstance().getReference("${auth.currentUser?.uid}+Items")
                    databasesup = FirebaseDatabase.getInstance().getReference("${auth.currentUser?.uid}+Supplier")
                    database.child(id).get().addOnSuccessListener {
                        if(it.exists()){
                            val jumlah = Integer.parseInt(it.child("jumlahbarang").value.toString())
                            val idsup = it.child("idsupp").value.toString()

                            databasesup.child(idsup).get().addOnSuccessListener {
                                val alamat = it.child("alamat").value.toString()
                                val jenis = it.child("jenisbrg").value.toString()
                                val nama = it.child("namasupp").value.toString()
                                val notelp = it.child("notelp").value.toString()
                                val jumlahsup = Integer.parseInt(it.child("jumlahbrg").value.toString())

                                val jumupdate = jumlahsup - jumlah

                                val supplier = DataSupplier(idsup, nama, jenis, alamat, notelp, jumupdate.toString())
                                databasesup.child(idsup).setValue(supplier).addOnFailureListener {
                                    Toast.makeText(this, "Gagal Update Data Supplier", Toast.LENGTH_SHORT).show()
                                }


                            }

                            database.child(id).removeValue().addOnSuccessListener {
                                binding.etId.text.clear()
                                Toast.makeText(this, "Berhasil Menghapus", Toast.LENGTH_SHORT).show()
                            }.addOnFailureListener {
                                Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(this, "Barang Tidak Ada", Toast.LENGTH_SHORT).show()
                        }
                        dialog.dismiss()
                    }
                }

                val nobtn = dialogBinding.findViewById<Button>(R.id.btn_no)
                nobtn.setOnClickListener {
                    dialog.dismiss()
                }
            }else{
                Toast.makeText(this, "please insert id", Toast.LENGTH_SHORT).show()
            }
        }

        binding.kliktam.setOnClickListener {
            val intent = Intent(this@DeleteStock, AddStock::class.java)
            startActivity(intent)
        }

    }
}
