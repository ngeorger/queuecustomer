package com.botcraft.queuecustomer.fragment


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.botcraft.queuecustomer.R
import com.botcraft.queuecustomer.activity.ScheduleAppointmentActivity
import com.botcraft.queuecustomer.modal.Token
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_booking.*
import kotlinx.android.synthetic.main.fragment_booking.view.*
import kotlinx.android.synthetic.main.fragment_live_track.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private var PRIVATE_MODE = 0
private val PREF_NAME = "queue_app"

class BookingFragment : Fragment() {

    var tokensList = ArrayList<Token>()
    lateinit var mobileNo: String
    var itemListener: ValueEventListener? = null
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_booking, container, false)

        view.progressBarView.visibility = View.VISIBLE

        view.scheduleAppointment.setOnClickListener(View.OnClickListener {
            context!!.startActivity(Intent(context, ScheduleAppointmentActivity::class.java))
        })

        databaseReference = FirebaseDatabase.getInstance().reference.child("appointments")

        return view
    }


    override fun onStart() {
        super.onStart()

        val itemListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                tokensList.clear()
                dataSnapshot.children.forEach {
                    it.children.mapNotNullTo(tokensList) {
                        it.getValue<Token>(Token::class.java)
                    }
                }

                if (progressBarView != null)
                    progressBarView.setVisibility(View.GONE)

                Log.d("balaji", "on call back")

                updateTokenDetails(getTokenDetails(tokensList))
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        databaseReference.addValueEventListener(itemListener)
        this.itemListener = itemListener

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val sharedPref: SharedPreferences = context.getSharedPreferences(
            PREF_NAME,
            PRIVATE_MODE
        )
        mobileNo = sharedPref.getString("mobile", "").toString()

    }

    fun getTokenDetails(tokenList: List<Token>): Token? {

        tokenList.forEach {
            if (it.patientMobile.equals(mobileNo)) {
                return it
            }
        }
        return null
    }

    fun updateTokenDetails(token: Token?) {
        if (token != null) {
            booking_detail_view.visibility = View.VISIBLE
            token_number.text = getString(R.string.token_no, token.slotId.toString())
            booking_date.text = getString(R.string.date_slot, token.bookedForDate, token.slotTime)
        }
    }

    override fun onStop() {
        super.onStop()
        itemListener?.let {
            databaseReference.removeEventListener(it)
        }
    }
}
