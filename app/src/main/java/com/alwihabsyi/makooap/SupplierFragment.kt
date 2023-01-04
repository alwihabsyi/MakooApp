package com.alwihabsyi.makooap

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SupplierFragment : Fragment(R.layout.fragment_supplier) {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var suppArrayList: ArrayList<DataSupplier>
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        view.findViewById<Button>(R.id.insupp).setOnClickListener {
            val intent = Intent(this.context, AddSupplier::class.java)
            startActivity(intent)
        }

        userRecyclerView = view.findViewById(R.id.rv_supplierslist)
        userRecyclerView.layoutManager = LinearLayoutManager(this.context)
        userRecyclerView.setHasFixedSize(true)

        suppArrayList = arrayListOf<DataSupplier>()
        getSupplierData()

        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.srl_supp)
        swipeRefreshLayout.setOnRefreshListener {
            refresh()
            swipeRefreshLayout.isRefreshing = false
        }

    }

    private fun refresh() {
        suppArrayList.clear()
        getSupplierData()
//        tvjumlahsupplier()
    }

    private fun getSupplierData(){

        database = FirebaseDatabase.getInstance().getReference("${auth.currentUser?.uid}+Supplier")
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(itemSnapshot in snapshot.children){
                        val items = itemSnapshot.getValue(DataSupplier::class.java)
                        suppArrayList.add(items!!)
                    }
                    userRecyclerView.adapter = SuppAdapter(suppArrayList) {
                        Intent(view?.context, SupplierDetail::class.java).apply {
                            putExtra("user", it)
                            startActivity(this)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}