package com.botcraft.queuecustomer.modal

class Slot {

    var id: Int = 0
    var name: String = ""
    var time: String = ""
    var available : Boolean = true

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(NewOrder.class)
    }

    constructor(itemid: Int, itemname: String,time: String,available: Boolean) {
        this.id = itemid
        this.name = itemname
        this.time = time
     }
}