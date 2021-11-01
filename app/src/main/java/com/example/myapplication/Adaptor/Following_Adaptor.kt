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
import com.bumptech.glide.request.BaseRequestOptions
import com.example.myapplication.R
import com.example.myapplication.customclickListner.CustomClickListner
import com.example.myapplication.entity.Response.Docs
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.Exception

class Following_Adaptor(
    var mcontext: Context,
    var listener: CustomClickListner,
    var list: ArrayList<Docs>

) : RecyclerView.Adapter<Following_Adaptor.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var followinglist = view.findViewById<LinearLayout>(R.id.chat_layout)
        var followername = view.findViewById<TextView>(R.id.followername)
        var otheruserProfile = view.findViewById<RelativeLayout>(R.id.otheruserProfile)
        var layout_logout_button = view.findViewById<RelativeLayout>(R.id.layout_logout_button)
        var image = view.findViewById<CircleImageView>(R.id.image)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Following_Adaptor.MyViewHolder {
        var item = LayoutInflater.from(parent.context).inflate(R.layout.lauout_follow, null)
        return MyViewHolder(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.followername.setText(list[position].userId.fullName.toString())

        try {
            var filedata = list[position].userId.profilePic
            Glide.with(mcontext).load(filedata).into(holder.image)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        holder.otheruserProfile.setOnClickListener {
            listener.customClick(list.get(position), "profile")
        }

        holder.layout_logout_button.setOnClickListener {
            listener.customClick(list.get(position), "Follow")
        }
    }
}


