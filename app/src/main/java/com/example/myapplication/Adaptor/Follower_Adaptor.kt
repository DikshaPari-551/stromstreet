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
import com.example.myapplication.R
import com.example.myapplication.entity.Response.Docss
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.Exception

class Follower_Adaptor(
    var mcontext: Context,
    var list: ArrayList<Docss>
) : RecyclerView.Adapter<Follower_Adaptor.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var followerlist = view.findViewById<LinearLayout>(R.id.chat_layout)
        var followername = view.findViewById<TextView>(R.id.followername)
        var followinglist = view.findViewById<LinearLayout>(R.id.chat_layout)
        var otheruserProfile = view.findViewById<RelativeLayout>(R.id.otheruserProfile)
        var layout_logout_button = view.findViewById<RelativeLayout>(R.id.layout_logout_button)
        var image = view.findViewById<CircleImageView>(R.id.image)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Follower_Adaptor.MyViewHolder {
        var item = LayoutInflater.from(parent.context).inflate(R.layout.lauout_follow, null)
        return MyViewHolder(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.followername.setText(list[position].followerId.fullName.toString())
        holder.layout_logout_button.visibility=View.GONE
        try {
            var filedata = list[position].followerId.profilePic.toString()
            Glide.with(mcontext).load(filedata).into(holder.image)
        }catch (e: Exception){
            e.printStackTrace()
        }
        holder.followerlist.setOnClickListener {

//            (mcontext as MainActivity).startActivity(Intent(mcontext, ChatActivity::class.java))
        }

    }
}