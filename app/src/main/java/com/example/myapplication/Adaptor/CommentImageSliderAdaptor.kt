package com.example.myapplication.Adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Activities.PostActivity2
import com.example.myapplication.R

class CommentImageSliderAdaptor(var imageList: List<String>, var mContext: PostActivity2) :
    RecyclerView.Adapter<CommentImageSliderAdaptor.MyCommentImageViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentImageSliderAdaptor.MyCommentImageViewHolder {
        var view: View? = LayoutInflater.from(parent.context).inflate(R.layout.comment_muti_image, null)
        view?.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )!!
        return CommentImageSliderAdaptor.MyCommentImageViewHolder(view!!)
    }

    override fun onBindViewHolder(
        holder: CommentImageSliderAdaptor.MyCommentImageViewHolder,
        position: Int
    ) {

        Glide.with(mContext).load(imageList.get(position)).into(holder.imagePost)

    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class MyCommentImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imagePost = itemView.findViewById<ImageView>(R.id.comment_screen_post)
    }

}