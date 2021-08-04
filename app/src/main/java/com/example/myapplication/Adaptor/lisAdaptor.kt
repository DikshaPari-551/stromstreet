package com.example.myapplication.Adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import layout.modal_class

class lisAdaptor(
    var list: ArrayList<modal_class> = arrayListOf()

):
RecyclerView.Adapter<lisAdaptor.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var text=view.findViewById<TextView>(R.id.text_chat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    var item =LayoutInflater.from(parent.context).
    inflate(R.layout.layout_list,null)
        return  MyViewHolder(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

   holder.text.setText(list.get(position).getchat())
    }
}