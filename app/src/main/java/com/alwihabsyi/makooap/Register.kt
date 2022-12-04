package com.alwihabsyi.makooap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.alwihabsyi.makooap.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
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

        val emInput = email.toString()
        val pwInput = password.toString()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Harap Isi Semua Field", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(emInput, pwInput)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Berhasil Mendaftar", Toast.LENGTH_SHORT).show()

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