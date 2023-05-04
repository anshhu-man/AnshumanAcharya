package com.example.anshumanacharya

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anshumanacharya.databinding.CardDesignBinding
import data

class RecyclerAdapter(private val list: ArrayList<data>): RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CardDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
    }
}

class MyViewHolder(private val binding: CardDesignBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(curr: data) {
        binding.apply {
            descp.text = curr.descp
            tCount.text = curr.count.toString()
            grsWt.text = curr.grsWt.toString()
            netWt.text = curr.netWt.toString()
            descp1.text = curr.type
            pcs1.text = curr.pcs.toString()
            netWt1.text = curr.ntWt.toString()
        }
    }

}
