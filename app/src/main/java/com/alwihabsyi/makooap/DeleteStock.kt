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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeleteStock : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteStockBinding
    private lateinit var database: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteStockBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                    database = FirebaseDatabase.getInstance().getReference("Items")
                    database.child(id).get().addOnSuccessListener {
                        if(it.exists()){
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
