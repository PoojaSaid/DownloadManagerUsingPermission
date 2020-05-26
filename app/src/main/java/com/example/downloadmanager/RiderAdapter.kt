package com.example.downloadmanager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.downloadmanager.RiderAdapter.RiderViewHolder
import java.util.*
import kotlin.collections.ArrayList

class RiderAdapter(context: Context?, riderList: ArrayList<LstRider>) : RecyclerView.Adapter<RiderViewHolder>() {
    private val riderList: ArrayList<LstRider> = ArrayList()
    //var activity: ItemClicked?


    init {

        this.riderList.addAll(riderList)

    }

    interface ItemClicked {
        fun onItemClikcked(index: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiderViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rider_list_card_view, parent, false)
        return RiderViewHolder(v)
    }

    override fun onBindViewHolder(holder: RiderViewHolder, position: Int) {
        val rider = riderList[position]!!
        holder.riderName.setText(rider.riderName)
        holder.riderId.setText(rider.riderId)

    }
    override fun getItemCount(): Int {
        return riderList.size
    }
    class RiderViewHolder(itemView: View) : ViewHolder(itemView){
        val riderName = itemView.findViewById<TextView>(R.id.tv_riderName) as TextView
        val riderId = itemView.findViewById<TextView>(R.id.tv_riderID) as TextView

    }
}