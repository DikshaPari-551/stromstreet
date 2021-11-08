package com.example.myapplication.Adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Activities.ChatActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.entity.Response.Chalist
import de.hdodenhof.circleimageview.CircleImageView
import java.util.ArrayList

class Chat_Adaptor(
        var mcontext: Context,
        var list: ArrayList<Chalist>?
) : RecyclerView.Adapter<Chat_Adaptor.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var chat = view.findViewById<LinearLayout>(R.id.chat_layout)
        var image = view.findViewById<CircleImageView>(R.id.image)
        var followername = view.findViewById<TextView>(R.id.followername)
      //  var profileImage = view.findViewById<CircleImageView>(R.id.profileImage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Chat_Adaptor.MyViewHolder
    {
        var item = LayoutInflater.from(parent.context).inflate(R.layout.layout_message, null)
        return MyViewHolder(item)
    }

    override fun getItemCount(): Int
    {
        return list!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        try {
            var filedata = list!!.get(position).receiverId.profilePic
            holder.followername.setText(list!!.get(position).receiverId.fullName)
            Glide.with(mcontext).load(filedata).into(holder.image);
        }catch (e: IndexOutOfBoundsException){
            e.printStackTrace()
        }
        catch (e: NullPointerException){
            e.printStackTrace()
        }
        holder.chat.setOnClickListener {
            (mcontext as MainActivity).startActivity(Intent(mcontext, ChatActivity::class.java))
        }
    }
}
