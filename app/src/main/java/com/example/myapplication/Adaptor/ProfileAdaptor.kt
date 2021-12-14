package com.example.myapplication.Adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Fragments.FirstFragment
import com.example.myapplication.Fragments.SeconddFragment
import com.example.myapplication.R
import com.example.myapplication.customclickListner.CustomClickListner
import com.example.myapplication.customclickListner.CustomClickListner2
import com.example.myapplication.customclickListner.CustomClickListner3
import com.example.myapplication.customclickListner.CustomClickListneruserpost
import com.example.myapplication.entity.Response.Docs
import com.example.myapplication.entity.Response.Docss
import com.example.myapplication.entity.Response.UserPostDocs

class ProfileAdaptor(
    var context: FirstFragment,
    var list: ArrayList<UserPostDocs>,
    var listener: CustomClickListneruserpost

) :  RecyclerView.Adapter<ProfileAdaptor.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var item = view.findViewById<ImageView>(R.id.item)
        var videoIcon = view.findViewById<ImageView>(R.id.video_icon)
        var imageIcon = view.findViewById<ImageView>(R.id.image_icon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_profile, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        try {
            if (list[position].mediaType.toLowerCase().equals("video")){
                holder.videoIcon.visibility = View.VISIBLE
                var thumbnail = list[position].thumbNail
                Glide.with(context).load(thumbnail).into(holder.item);
            }
            else{
                if (list.get(position).imageLinks.size > 1) {
                    holder.imageIcon.visibility = View.VISIBLE
                    var filedata = list[position].imageLinks[0]
                    Glide.with(context).load(filedata).into(holder.item);
                } else {
                    var filedata = list[position].imageLinks[0]
                    Glide.with(context).load(filedata).into(holder.item);
                }
            }

        }catch (e: IndexOutOfBoundsException){
            e.printStackTrace()
        }

        holder.item.setOnClickListener {

            listener.customClick(list.get(position),"profile",position)
        }
    }
}