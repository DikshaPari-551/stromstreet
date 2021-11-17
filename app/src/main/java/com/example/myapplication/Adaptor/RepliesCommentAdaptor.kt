package com.example.myapplication.Adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.entity.Response.CommentList
import com.example.myapplication.entity.Response.Replies
import de.hdodenhof.circleimageview.CircleImageView

class RepliesCommentAdaptor(var mcontext: Context, var list: ArrayList<Replies>) : RecyclerView.Adapter<RepliesCommentAdaptor.RepliesCommentViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepliesCommentAdaptor.RepliesCommentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.replies_comment_list, parent, false)
        return RepliesCommentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RepliesCommentAdaptor.RepliesCommentViewHolder, position: Int) {
        holder.replierName.setText(list.get(position).userId.userName)
        holder.replierComment.setText(list.get(position).comment)
        if(list.get(position).likeCount > 0) {
            holder.likes.visibility = View.VISIBLE
            holder.like_count.setText(list.get(position).likeCount.toString())
        }


        try {
            var filedata = list[position].userId.profilePic
            Glide.with(mcontext).load(filedata).placeholder(R.drawable.circleprofile).into(holder.replierImage);
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class RepliesCommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var replierName = view.findViewById<TextView>(R.id.repliersName)
        var replierComment = view.findViewById<TextView>(R.id.repliersComment)
        var replierImage = view.findViewById<CircleImageView>(R.id.repliersImage)
        var likes = view.findViewById<LinearLayout>(R.id.likes)
        var like_count = view.findViewById<TextView>(R.id.like_count)
    }
}