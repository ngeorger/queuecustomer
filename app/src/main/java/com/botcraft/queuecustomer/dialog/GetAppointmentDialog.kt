package com.botcraft.queuecustomer.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.botcraft.queuecustomer.R
import kotlinx.android.synthetic.main.get_appointment_dialog.*

class GetAppointmentDialog(var mListener: OnAppointmentGetListener) : DialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.get_appointment_dialog, container)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        get_appointment.setOnClickListener {
            mListener.onAppointmentGet(
                patient_mob_no.text.toString()
            )
            dismiss()

        }
    }

    interface OnAppointmentGetListener {
        fun onAppointmentGet(mobileNo: String)
    }

}