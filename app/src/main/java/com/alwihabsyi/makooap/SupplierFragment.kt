package com.alwihabsyi.makooap

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class SupplierFragment : Fragment(R.layout.fragment_supplier) {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var suppArrayList: ArrayList<DataSupplier>
    private lateinit var database: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.insupp).setOnClickListener {
            val intent = Intent(this.context, AddSupplier::class.java)
            startActivity(intent)
        }

        userRecyclerView = view.findViewById(R.id.rv_supplierslist)
        userRecyclerView.layoutManager = LinearLayoutManager(this.context)
        userRecyclerView.setHasFixedSize(true)

        suppArrayList = arrayListOf<DataSupplier>()
        getSupplierData()

    }

    private fun getSupplierData(){

        database = FirebaseDatabase.getInstance().getReference("Supplier")
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(itemSnapshot in snapshot.children){
                        val items = itemSnapshot.getValue(DataSupplier::class.java)
                        suppArrayList.add(items!!)
                    }
                    userRecyclerView.adapter = SuppAdapter(suppArrayList) {

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}