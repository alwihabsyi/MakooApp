package com.alwihabsyi.makooap

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.alwihabsyi.makooap.databinding.ActivityProfileEditBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class ProfileEdit : AppCompatActivity() {

    var pickedPhoto: Uri? = null
    var pickedBitmap: Bitmap? = null
    private lateinit var binding: ActivityProfileEditBinding
    private lateinit var storageRef: StorageReference
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("User")
        profnamepro()

        val pickPhoto = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                binding.profup.setImageURI(it)
                uri = it!!
            })

        binding.profup.setOnClickListener {
            pickPhoto.launch("image/*")
        }

        binding.btnUpdate.setOnClickListener {
            val uname = binding.etUname.text
            val uid = auth.currentUser?.uid

            if (uname.isNotEmpty()) {
                database.child(uid!!).get().addOnSuccessListener {
                    val email = it.child("em").value.toString()
                    val user = it.child("uname").value.toString()

                    val datauser = DataUser(uid, email, uname.toString())
                    database.child(uid).setValue(datauser).addOnSuccessListener {
                        uploadProfPic()
                    }
                }
            } else {
                Toast.makeText(this, "Harap Isi Username", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun uploadProfPic() {

        storageRef = FirebaseStorage.getInstance().getReference("User/" + auth.currentUser?.uid)
        storageRef.putFile(uri).addOnSuccessListener {

            it.metadata!!.reference!!.downloadUrl
                .addOnSuccessListener {
                    val userId = auth.currentUser?.uid
                    val mapImage = mapOf(
                        "url" to it.toString()
                    )
                    val datalink = FirebaseDatabase.getInstance().getReference("userImages")
                    datalink.child(userId!!).setValue(mapImage)
                }

            Toast.makeText(this, "Berhasil Update Profil", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
        }

    }

    private fun profnamepro() {
        val currentUser = auth.currentUser
        if(currentUser != null){
            val profil = binding.profup
            val datalink = FirebaseDatabase.getInstance().getReference("userImages")
            datalink.child(auth.currentUser!!.uid).get().addOnSuccessListener {
                if(it.exists()){
                    val url = it.child("url").value.toString()
                    Picasso
                        .get()
                        .load(url)
                        .into(profil)
                }
            }
        }
    }
}