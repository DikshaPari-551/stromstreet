package com.example.myapplication.Adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Fragments.HomeFragment
import com.example.myapplication.R
import com.example.myapplication.customclickListner.CustomClickListner
import com.example.myapplication.customclickListner.CustomClickListner2
import com.example.myapplication.entity.Response.Docss

class HomeAdaptor(

    var context: HomeFragment,
    var list: ArrayList<Docss>,
    var listener: CustomClickListner2

    ) : RecyclerView.Adapter<HomeAdaptor.MyViewHolder>()
{
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var layout_vedio=view.findViewById<RelativeLayout>(R.id.layout_vedio)
        var postView=view.findViewById<ImageView>(R.id.postView)
        var name = view.findViewById<TextView>(R.id.name)
        var bio = view.findViewById<TextView>(R.id.bio)
        var mainlayout = view.findViewById<LinearLayout>(R.id.mainlayout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdaptor.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HomeAdaptor.MyViewHolder, position: Int) {
        holder.name.setText(list[position].userDetails.userName.toString())
        holder.name.setText(list[position].userDetails.bio.toString())
        holder.name.setText(list[position].userDetails.userName.toString())

        var filedata = list[position].thumbNail
        Glide.with(context).load(filedata).into(holder.postView);

        holder.mainlayout.setOnClickListener {

            listener.customClick(list.get(position),"profile")
        }
    }


}