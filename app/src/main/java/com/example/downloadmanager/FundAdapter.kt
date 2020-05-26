package com.example.downloadmanager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FundAdapter(context: Context?, fundList: ArrayList<LstFund>) : RecyclerView.Adapter<FundAdapter.FundViewHolder>() {
    private val fundList: ArrayList<LstFund> = ArrayList()

    init {
        this.fundList.addAll(fundList)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FundViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_fund, parent, false)
        return FundViewHolder(v)
    }

    override fun onBindViewHolder(holder:FundViewHolder, position: Int) {
        val fund = fundList[position]!!
        holder.fundName.setText(fund.fundCode)
        holder.fundId.setText(fund.fundID)
    }

    override fun getItemCount(): Int {
        return fundList.size
    }

    class FundViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val fundName = itemView.findViewById<TextView>(R.id.tv_fundName) as TextView
        val fundId = itemView.findViewById<TextView>(R.id.tv_fundId) as TextView
    }


}