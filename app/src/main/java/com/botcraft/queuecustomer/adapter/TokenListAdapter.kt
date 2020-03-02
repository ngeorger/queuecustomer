package com.botcraft.queuecustomer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.botcraft.queuecustomer.R
import com.botcraft.queuecustomer.modal.Token

class TokenListAdapter() : RecyclerView.Adapter<TokenListAdapter.ViewHolder>() {

    private lateinit var context: Context
    private lateinit var mLayoutInflater: LayoutInflater
    private lateinit var mListener: OnClickListener
    var tokensList: List<Token>? = emptyList()

    constructor(context: Context, listener: OnClickListener) : this() {
        mLayoutInflater = LayoutInflater.from(context)
        this.mListener = listener
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = mLayoutInflater.inflate(R.layout.token_item_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (tokensList != null) {
            holder.mTokenId.text = context.getString(R.string.token_number, (position + 1).toString())
            holder.mTokenStatus.text = tokensList!!.get(position).status
            if (tokensList!!.get(position).status.equals("Completed")) {
                holder.mCheckBox.setImageResource(R.drawable.checkbox_green)
                holder.mTokenStatus.setTextColor(ContextCompat.getColor(context, R.color.color_green))
            } else {
                holder.mCheckBox.setImageResource(R.drawable.checkbox_black)
                holder.mTokenStatus.setTextColor(ContextCompat.getColor(context, R.color.color_dark_gray))
            }
        }
    }

    fun updateTokensList(tokensList: List<Token>) {
        this.tokensList = tokensList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return tokensList?.size ?: 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal val mTokenId: TextView
        internal val mTokenStatus: TextView
        internal val mCheckBox: ImageView


        init {
            mTokenId = itemView.findViewById(R.id.token_id)
            mTokenStatus = itemView.findViewById(R.id.token_status)
            mCheckBox = itemView.findViewById(R.id.checkbox)

        }
    }

    interface OnClickListener {
        fun onItemClickListener()
    }
}