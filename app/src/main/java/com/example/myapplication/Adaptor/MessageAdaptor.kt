package com.example.myapplication.Adaptor

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.entity.Response.Messages

class MessageAdaptor(var listdat: ArrayList<Messages>, var USERID: String) : RecyclerView.Adapter<MessageAdaptor.MyViewHolder>() {
    inner class MyViewHolder(view: View):RecyclerView.ViewHolder(view){
        var sendertext=view.findViewById<TextView>(R.id.sendertext)
        var recivertext=view.findViewById<TextView>(R.id.recivertext)
        var sender=view.findViewById<LinearLayout>(R.id.sender)
        var reciver=view.findViewById<LinearLayout>(R.id.reciver)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdaptor.MyViewHolder {
        var v= LayoutInflater.from(parent.context).
                inflate(R.layout.layout_recycler_chat,null)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MessageAdaptor.MyViewHolder, position: Int)
    {if(listdat.get(position).receiverId.equals(USERID))
    {holder.sender.visibility=View.VISIBLE
        holder.reciver.visibility=View.GONE
        holder.sendertext.setText(listdat.get(position).message)
//        val params = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.MATCH_PARENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT
//        ).apply {
//            weight = 1.0f
//            gravity = Gravity.RIGHT
//        }
//        holder.sender.setLayoutParams(params);
    }
        else
    {
        holder.sender.visibility=View.GONE
        holder.reciver.visibility=View.VISIBLE
        holder.recivertext.setText(listdat.get(position).message)
    }

    }

    override fun getItemCount(): Int {
    return listdat.size
    }
}