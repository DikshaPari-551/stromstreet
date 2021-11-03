package com.example.myapplication.Adaptor

import android.content.Context
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
import com.example.myapplication.util.SavedPrefManager
import java.lang.Exception

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
        var text_weather = view.findViewById<TextView>(R.id.text_weather)
        var text_okhla = view.findViewById<TextView>(R.id.text_okhla)
        var mainlayout = view.findViewById<LinearLayout>(R.id.mainlayout)
        var videoIcon = view.findViewById<ImageView>(R.id.video_icon)
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
        try {
            holder.name.setText(list[position].userDetails.userName.toString())
            holder.bio.setText(list[position].userDetails.bio.toString())
            holder.text_okhla.setText(list[position].address)
        }catch (e: Exception){
            e.printStackTrace()
        }

        try {
//            var filedata = list[position].imageLinks[0]
//            Glide.with(context).load(filedata).into(holder.postView);
            if (list[position].mediaType.toLowerCase().equals("video")){
                holder.videoIcon.visibility = View.VISIBLE
                var filedata = list[position].thumbNail
                Glide.with(context).load(filedata).into(holder.postView);
            }
            else{
                var filedata = list[position].imageLinks[0]
                Glide.with(context).load(filedata).into(holder.postView);
            }

        }catch (e: Exception){
            e.printStackTrace()
        }


        holder.mainlayout.setOnClickListener {

            listener.customClick(list.get(position),"profile")
        }
    }


}