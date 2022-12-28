package com.alwihabsyi.makooap

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ThirdFragment : Fragment(R.layout.fragment_third) {

    lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        view.findViewById<CardView>(R.id.cvlogout).apply {
            setOnClickListener {
                auth.signOut()
                startActivity(Intent(context, AuthActivity::class.java))
                activity?.finish()
            }
        }
    }
}