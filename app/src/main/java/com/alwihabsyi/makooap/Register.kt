package com.alwihabsyi.makooap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.alwihabsyi.makooap.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnSignup.setOnClickListener {
            startSigningUp()
        }
    }

    private fun startSigningUp() {
        val email = binding.etEmail.text
        val password = binding.etPassword.text
        val uname = binding.etUname.text

        val emInput = email.toString()
        val pwInput = password.toString()
        val user = uname.toString()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Harap Isi Semua Field", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(emInput, pwInput)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Berhasil Mendaftar", Toast.LENGTH_SHORT).show()
                    database = FirebaseDatabase.getInstance().getReference("User")
                    val uid = auth.currentUser?.uid
                    val datauser = DataUser(uid,emInput,user)
                    database.child(uid!!).setValue(datauser)

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Gagal Mendaftar", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Terjadi Error ${it.localizedMessage}", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}

data class DataUser(
    val id: String? = null,
    val em : String? = null,
    val uname: String? = null
)