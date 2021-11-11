package com.example.myapplication.Adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Activities.UserProfile
import com.example.myapplication.Fragments.FirstFragment
import com.example.myapplication.R
import com.example.myapplication.customclickListner.CustomClickListner
import com.example.myapplication.customclickListner.CustomClickListner2
import com.example.myapplication.customclickListner.CustomClickListner3
import com.example.myapplication.entity.Response.Docs
import com.example.myapplication.entity.Response.UserPostDocs

class UserProfilePostAdaptor(
    var context: UserProfile,
    var list: ArrayList<UserPostDocs>,
    var listener: CustomClickListner3

):  RecyclerView.Adapter<UserProfilePostAdaptor.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var item = view.findViewById<ImageView>(R.id.item)
        var videoIcon = view.findViewById<ImageView>(R.id.video_icon)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserProfilePostAdaptor.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_profile, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UserProfilePostAdaptor.MyViewHolder, position: Int) {
        try {
            if (list[position].mediaType.toLowerCase().equals("video")){
                holder.videoIcon.visibility = View.VISIBLE
                var filedata = list[position].thumbNail
                Glide.with(context).load(filedata).into(holder.item);
            }else{
                var filedata = list[position].imageLinks[0]
                Glide.with(context).load(filedata).into(holder.item);
            }

        }catch (e: IndexOutOfBoundsException){
            e.printStackTrace()
        }
        holder.item.setOnClickListener {

            listener.customClick(list.get(position),"profile")
        }
    }
}