package com.example.myapplication.Adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R

class ImageListAdaptor:  RecyclerView.Adapter<ImageListAdaptor.MyViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageListAdaptor.MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.add_post_list, null)
        return ImageListAdaptor.MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {


    }

}