package com.stormstreet.myapplication.Adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stormstreet.myapplication.Activities.BlockActivity
import com.stormstreet.myapplication.R
import com.stormstreet.myapplication.customclickListner.CustomClickListner2
import com.stormstreet.myapplication.entity.Response.Docss
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.Exception

class Blockadapter(var mcontext: Context,var list: ArrayList<Docss>,var listener: CustomClickListner2) : RecyclerView.Adapter<Blockadapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var followerlist = view.findViewById<LinearLayout>(R.id.chat_layout)
        var followername = view.findViewById<TextView>(R.id.followername)
        var followinglist = view.findViewById<LinearLayout>(R.id.chat_layout)
        var otheruserProfile = view.findViewById<RelativeLayout>(R.id.otheruserProfile)
        var layout_logout_button = view.findViewById<RelativeLayout>(R.id.layout_logout_button)
        var image = view.findViewById<CircleImageView>(R.id.image)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Blockadapter.MyViewHolder {
        var item = LayoutInflater.from(parent.context).inflate(R.layout.lauout_follow, null)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: Blockadapter.MyViewHolder, position: Int) {
        holder.followername.setText(list[position].fullName.toString())
        holder.layout_logout_button.visibility=View.VISIBLE
        try {
            var filedata = list[position].profilePic.toString()
            Glide.with(mcontext).load(filedata).placeholder(R.drawable.circleprofile).into(holder.image)
        }catch (e: Exception){
            e.printStackTrace()
        }
        holder.followerlist.setOnClickListener {

//            (mcontext as MainActivity).startActivity(Intent(mcontext, ChatActivity::class.java))
        }
        holder.otheruserProfile.setOnClickListener {
            listener.customClick(list.get(position), "block")
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }
}


