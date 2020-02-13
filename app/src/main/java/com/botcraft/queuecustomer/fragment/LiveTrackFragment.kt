package com.botcraft.queuecustomer.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.botcraft.queuecustomer.R
import com.botcraft.queuecustomer.adapter.TokenListAdapter
import com.botcraft.queuecustomer.modal.Token
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_live_track.*

class LiveTrackFragment : Fragment(),TokenListAdapter.OnClickListener {

    var tokensList = ArrayList<Token>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_track, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
    }

    private fun setRecyclerView() {
        // Layout Manager for recycler view
        progressbar.setVisibility(View.VISIBLE)
        val grid = GridLayoutManager(context, 2)
        tokensRecyclerView.setHasFixedSize(true)
        tokensRecyclerView.layoutManager = grid

        val adapter = TokenListAdapter(context!!, this)
        tokensRecyclerView.adapter = adapter

        val itemListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    it.children.mapNotNullTo(tokensList) {
                        it.getValue<Token>(Token::class.java)
                    }
                }

                progressbar.setVisibility(View.GONE)
                adapter.updateTokensList(tokensList)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        FirebaseDatabase.getInstance().reference.child("appointments").addValueEventListener(itemListener)
    }

    override fun onItemClickListener() {

    }

}
