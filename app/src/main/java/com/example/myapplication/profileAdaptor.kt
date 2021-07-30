package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class profileAdaptor(

):  RecyclerView.Adapter<profileAdaptor.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): profileAdaptor.MyViewHolder {
        var v = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_profile,null)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }
}