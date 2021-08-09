package com.example.myapplication.Adaptor

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class MessageAdaptor(var arr:ArrayList<HashMap<String,String>> = arrayListOf()
): RecyclerView.Adapter<MessageAdaptor.MyViewHolder>() {
    inner class MyViewHolder(view: View):RecyclerView.ViewHolder(view){
        var mText=view.findViewById<TextView>(R.id.text_list)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdaptor.MyViewHolder {
        var v= LayoutInflater.from(parent.context).
                inflate(R.layout.layout_recycler_chat,null)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MessageAdaptor.MyViewHolder, position: Int) {
        holder.mText.setText(arr.get(position).get("Data"))
    }

    override fun getItemCount(): Int {
    return arr.size
    }
}