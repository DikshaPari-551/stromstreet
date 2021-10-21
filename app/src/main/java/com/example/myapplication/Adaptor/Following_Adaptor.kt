package com.example.myapplication.Adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Activities.ChatActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.entity.Response.Docs

class Following_Adaptor (
    var mcontext: Context,
    var list: ArrayList<Docs>

) : RecyclerView.Adapter<Following_Adaptor.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var followinglist = view.findViewById<LinearLayout>(R.id.chat_layout)
        var followername = view.findViewById<TextView>(R.id.followername)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Following_Adaptor.MyViewHolder {
        var item = LayoutInflater.from(parent.context).inflate(R.layout.layout_message, null)
        return MyViewHolder(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.followername.setText(list[position].followerId.fullName.toString())

        holder.followinglist.setOnClickListener {

//            (mcontext as MainActivity).startActivity(Intent(mcontext, ChatActivity::class.java))
        }
    }
}
