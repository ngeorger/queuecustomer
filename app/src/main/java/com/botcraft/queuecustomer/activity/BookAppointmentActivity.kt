package com.botcraft.queuecustomer.activity

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.botcraft.queuecustomer.R
import com.botcraft.queuecustomer.modal.Token
import com.botcraft.queuecustomer.utils.Utils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_book_appointment.*

class BookAppointmentActivity : AppCompatActivity() {

    var slotId: Int = -1
    var slotTime: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_appointment)

        slotId = getIntent().getIntExtra("slot_id", -1)
        slotTime = getIntent().getStringExtra("time")

        radiogroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)

            if (radio.text.equals("For Others")) {
                othersLayout.setVisibility(View.VISIBLE)
            } else if (radio.text.equals("For Self")) {
                othersLayout.setVisibility(View.GONE)
            }
        })

        confirmClick()
    }

    private fun confirmClick() {

        btn_confirm.setOnClickListener {
            var id: Int = radiogroup.checkedRadioButtonId
            if (id != -1) {
                val radio: RadioButton = findViewById(id)

                if (radio.text.equals("For Others")) {
                    if (name.text.isEmpty()) {
                        Utils.showToast(this, "Please enter Name")
                        return@setOnClickListener
                    }

                    if (age.text.isEmpty()) {
                        Utils.showToast(this, "Please enter Age")
                        return@setOnClickListener
                    }

                    if (gender.text.isEmpty()) {
                        Utils.showToast(this, "Please enter Gender")
                        return@setOnClickListener
                    }

                    if (disease_details.text.isEmpty()) {
                        Utils.showToast(this, "Please enter Disease details")
                        return@setOnClickListener
                    }

                    bookAppointment(false)

                } else if (radio.text.equals("For Self")) {
                    if (disease_details.text.isEmpty()) {
                        Utils.showToast(this, "Please enter Disease details")
                        return@setOnClickListener
                    }

                    bookAppointment(true)
                }

            } else {
                Utils.showToast(this, "Please Select For Self or Others")
            }
        }
    }

    private fun bookAppointment(forSelf: Boolean) {

        var strname = ""
        var strmobile = Utils.getPrefValue(this,"mobile")!!
        var strage = ""
        var strgender = ""
        var strdiseasedetails = disease_details.text.toString()

        if(forSelf){
            strname = Utils.getPrefValue(this,"name")!!
            strage = Utils.getPrefValue(this,"age")!!
            strgender = Utils.getPrefValue(this,"gender")!!
        } else {
            strname =  name.text.toString()
            strage = age.text.toString()
            strgender = gender.text.toString()
        }

        val token = Token(slotId, slotTime, strname!!,strmobile!!, strage.toInt(),strgender!!,"Upcoming",strdiseasedetails)

        FirebaseDatabase.getInstance().reference.child("appointments").child(slotId.toString())
            .child(strmobile).setValue(token).addOnCompleteListener(
                OnCompleteListener {
                    Utils.showToast(this, "Appointment Booked")
                    finish()
                })

        val itemListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if(dataSnapshot.childrenCount>=3) {
                    FirebaseDatabase.getInstance().reference.child("slots")
                        .child((slotId-1).toString()).child("available").setValue(false)

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }

        FirebaseDatabase.getInstance().reference.child("appointments")
            .child(slotId.toString()).addValueEventListener(itemListener)
    }
}