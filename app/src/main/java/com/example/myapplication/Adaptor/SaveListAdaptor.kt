package com.example.myapplication.Adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Fragments.SeconddFragment
import com.example.myapplication.R
import com.example.myapplication.entity.Response.Docs

class SaveListAdaptor(

    var context: SeconddFragment,
    var list: ArrayList<Docs>
) : RecyclerView.Adapter<SaveListAdaptor.MyViewHolder>()
{
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var item = view.findViewById<ImageView>(R.id.item)

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
        var filedata = list[position].postId.thumbNail

        Glide.with(context).load(filedata).into(holder.item);
    }
}