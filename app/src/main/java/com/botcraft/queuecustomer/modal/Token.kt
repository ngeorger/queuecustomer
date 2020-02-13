package com.botcraft.queuecustomer.modal

class Token {

    var slotId: Int = 0
    var slotTime: String = ""
    var patientName: String = ""
    var patientMobile: String = ""
    var patientAge: Int = 0
    var patientGender: String = ""
    var status : String = ""
    var diseaseDetails : String = ""

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(NewOrder.class)
    }

    constructor(slotId: Int, slotTime: String, patientName: String, patientMobile: String,patientAge: Int,
                patientGender:String,status:String,diseaseDetails:String) {
        this.slotId = slotId
        this.slotTime = slotTime
        this.patientName = patientName
        this.patientMobile = patientMobile
        this.patientAge = patientAge
        this.patientGender = patientGender
        this.status = status
        this.diseaseDetails = diseaseDetails
    }

}
