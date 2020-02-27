package com.botcraft.queuecustomer.fragment


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.botcraft.queuecustomer.R
import com.botcraft.queuecustomer.activity.ScheduleAppointmentActivity
import com.botcraft.queuecustomer.modal.Token
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_booking.*
import kotlinx.android.synthetic.main.fragment_booking.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var PRIVATE_MODE = 0
private val PREF_NAME = "queue_app"

class BookingFragment : Fragment() {

    var tokensList = ArrayList<Token>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_booking, container, false)
        val sharedPref: SharedPreferences = activity!!.getSharedPreferences(
            PREF_NAME,
            PRIVATE_MODE
        )

        view.progressBarView.visibility = View.VISIBLE

        view.scheduleAppointment.setOnClickListener(View.OnClickListener {
            context!!.startActivity(Intent(context, ScheduleAppointmentActivity::class.java))
        })


        val itemListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                tokensList.clear()
                dataSnapshot.children.forEach {
                    it.children.mapNotNullTo(tokensList) {
                        it.getValue<Token>(Token::class.java)
                    }
                }

                if (view.progressBarView != null)
                    view.progressBarView.setVisibility(View.GONE)

                updateTokenDetails(getBookedToken(tokensList))
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        FirebaseDatabase.getInstance().reference.child("appointments").addValueEventListener(itemListener)

        return view
    }

    fun getBookedToken(tokenList: List<Token>): Token? {

        val sharedPref: SharedPreferences = activity!!.getSharedPreferences(
            PREF_NAME,
            PRIVATE_MODE
        )

        tokenList.forEach {
            if (it.patientMobile.equals(sharedPref.getString("mobile", null))) {
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

}
