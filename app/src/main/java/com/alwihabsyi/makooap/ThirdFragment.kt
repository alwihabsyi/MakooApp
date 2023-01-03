package com.alwihabsyi.makooap

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class ThirdFragment : Fragment(R.layout.fragment_third) {

    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        profnamepro()

        view.findViewById<CardView>(R.id.cvlogout).apply {
            setOnClickListener {
                auth.signOut()
                startActivity(Intent(context, AuthActivity::class.java))
                activity?.finish()
            }
        }

        view.findViewById<ImageView>(R.id.btnprof).setOnClickListener {
            val intent = Intent(view.context, ProfileEdit::class.java)
            startActivity(intent)
        }
    }

    private fun profnamepro() {
        val currentUser = auth.currentUser
        if(currentUser != null){
            database = FirebaseDatabase.getInstance().getReference("User")
            database.child(auth.currentUser!!.uid).get().addOnSuccessListener {
                if(it.exists()){
                    val tvuser = it.child("uname").value.toString()
                    view?.findViewById<TextView>(R.id.namapro)?.text = tvuser
                }
            }
            val profil = view?.findViewById<ImageView>(R.id.profpro)
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
        }else{
            view?.findViewById<TextView>(R.id.profname)?.text = "null"
        }
    }
}