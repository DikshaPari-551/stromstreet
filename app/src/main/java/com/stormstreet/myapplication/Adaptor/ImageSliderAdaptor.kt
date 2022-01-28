package com.stormstreet.myapplication.Adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stormstreet.myapplication.Activities.PostActivity
import com.stormstreet.myapplication.R

class ImageSliderAdaptor(
    var imageList: List<String>,
    var postActivity: PostActivity
) : RecyclerView.Adapter<ImageSliderAdaptor.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        var view : View? =  LayoutInflater.from(parent.context).inflate(R.layout.slider_image, null)
        view?.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)!!
        return ImageSliderAdaptor.MyViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: ImageSliderAdaptor.MyViewHolder, position: Int) {
        Glide.with(postActivity).load(imageList.get(position)).into(holder.image)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }


    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageView>(R.id.img)

    }
}