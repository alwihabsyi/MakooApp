package com.alwihabsyi.makooap

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alwihabsyi.makooap.databinding.FragmentSecondBinding
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class SecondFragment : Fragment(R.layout.fragment_second) {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var userArrayList: ArrayList<DatabaseStok>
    private lateinit var tempArrayList: ArrayList<DatabaseStok>
    private lateinit var database: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //RV STOK START
        userRecyclerView = view.findViewById(R.id.rv_listsearch)
        userRecyclerView.layoutManager = LinearLayoutManager(this.context)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf<DatabaseStok>()
        tempArrayList = arrayListOf<DatabaseStok>()
        getListData()
        //RV STOK END

        //search view
        searchView = view.findViewById(R.id.search_view)

        //intents
        val btnupdate = view.findViewById<Button>(R.id.ic_edit)
        val btndelete = view.findViewById<Button>(R.id.ic_del)
        btnupdate.setOnClickListener {
            val intent = Intent(view.context, AddStock::class.java)
            startActivity(intent)
        }
        btndelete.setOnClickListener {
            val intent = Intent(view.context, DeleteStock::class.java)
            startActivity(intent)
        }
    }

    private fun getListData() {

        database = FirebaseDatabase.getInstance().getReference("Items")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (itemSnapshot in snapshot.children) {
                        val items = itemSnapshot.getValue(DatabaseStok::class.java)
                        userArrayList.add(items!!)
                        tempArrayList.add(items!!)
                    }
                    userRecyclerView.adapter = StokAdapter(userArrayList)
                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(p0: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(p0: String?): Boolean {
                            tempArrayList.clear()
                            val searchText = p0!!.lowercase(Locale.getDefault())
                            if (searchText.isNotEmpty()) {
                                userArrayList.forEach {
                                    if (it.id!!.lowercase(Locale.getDefault())!!
                                            .contains(searchText)
                                    ) {
                                        tempArrayList.add(it)
                                    }
                                }
                                userRecyclerView.adapter = StokAdapter(tempArrayList)
                            }else{
                                tempArrayList.clear()
                                tempArrayList.addAll(userArrayList)
                                userRecyclerView.adapter = StokAdapter(userArrayList)
                            }
                            return false
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}