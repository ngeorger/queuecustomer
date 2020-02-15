package com.botcraft.queuecustomer.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.botcraft.queuecustomer.R
import com.botcraft.queuecustomer.modal.Slot

class SlotsListAdapter() : RecyclerView.Adapter<SlotsListAdapter.ViewHolder>() {

    private lateinit var context: Context
    var slotsList: List<Slot>? = emptyList()
    private lateinit var mLayoutInflater: LayoutInflater
    private lateinit var mListener: OnClickListener

    constructor(context: Context, listener: OnClickListener) : this() {
        mLayoutInflater = LayoutInflater.from(context)
        this.mListener = listener
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = mLayoutInflater.inflate(R.layout.slots_item_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (slotsList != null) {
            holder.mslotTime.text = slotsList?.get(position)?.time
        }

        if(slotsList!!.get(position).available){
            holder.mslotTime.setBackgroundResource(R.drawable.bt_green_background)
        } else {
            holder.mslotTime.setBackgroundResource(R.drawable.bt_gray_background)
        }

        holder.itemView.setOnClickListener {
            mListener.onItemClickListener(slotsList!!.get(position))
        }
    }

    override fun getItemCount(): Int {
        return slotsList?.size ?: 0
    }

    fun updateSlotsList(slotsList: List<Slot>) {
        this.slotsList = slotsList
         notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal val mslotTime: TextView

        init {
            mslotTime = itemView.findViewById(R.id.slot_time)
        }
    }

    interface OnClickListener {
        fun onItemClickListener(slot: Slot)
    }
 }
