package com.botcraft.queuecustomer.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.botcraft.queuecustomer.R
import com.botcraft.queuecustomer.adapter.SlotsListAdapter
import com.botcraft.queuecustomer.modal.Slot
import com.botcraft.queuecustomer.utils.Utils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_schedule_appointment.*

class ScheduleAppointmentActivity : AppCompatActivity(), SlotsListAdapter.OnClickListener {

    var slotsList = ArrayList<Slot>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_appointment)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        // Layout Manager for recycler view
        progressbar.setVisibility(View.VISIBLE)
        val grid = GridLayoutManager(this, 3)
        slotsRecyclerView.setHasFixedSize(true)
        slotsRecyclerView.layoutManager = grid

        val adapter = SlotsListAdapter(this, this)
        slotsRecyclerView.adapter = adapter

        val itemListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                slotsList.clear()

                dataSnapshot.children.mapNotNullTo(slotsList) {
                    it.getValue<Slot>(Slot::class.java)
                }
                progressbar.setVisibility(View.GONE)
                adapter.updateSlotsList(slotsList)

            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        FirebaseDatabase.getInstance().reference.child("slots").addValueEventListener(itemListener)
    }

    override fun onItemClickListener(slot: Slot) {

        if(slot.available){
            startActivity(
                Intent(this, BookAppointmentActivity::class.java)
                    .putExtra("slot_id", slot.id)
                    .putExtra("time", slot.time)
            )
        } else {
            Utils.showToast(this,"Slot not available")
        }

    }

}