package com.example.myapplication.Adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Fragments.SeconddFragment
import com.example.myapplication.R
import com.example.myapplication.entity.Response.Docs

class ProfileAdaptor() :  RecyclerView.Adapter<ProfileAdaptor.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_profile, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        Glide.with(context).load(file).into(binding.ivcirclecamera);
    }
}