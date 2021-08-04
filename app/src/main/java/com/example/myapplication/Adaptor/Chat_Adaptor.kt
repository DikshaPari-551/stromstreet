package com.example.myapplication.Adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Activities.ChatActivity
import com.example.myapplication.Activities.MainActivity
import com.example.myapplication.R

class Chat_Adaptor(
    var mcontext: Context
): RecyclerView.Adapter<Chat_Adaptor.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var chat=view.findViewById<LinearLayout>(R.id.chat_layout)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Chat_Adaptor.MyViewHolder {
        var item = LayoutInflater.from(parent.context).
        inflate(R.layout.layout_message,null)
        return MyViewHolder(item)
    }

    override fun getItemCount(): Int {
        return 15
    }

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
        holder.chat.setOnClickListener {

            (mcontext as MainActivity).startActivity(Intent(mcontext, ChatActivity::class.java))
        }
    }
}
