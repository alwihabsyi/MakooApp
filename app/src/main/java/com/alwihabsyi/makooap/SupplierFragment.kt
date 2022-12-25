package com.alwihabsyi.makooap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference

class SupplierFragment : Fragment(R.layout.fragment_supplier) {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<DatabaseStok>
    private lateinit var database: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}