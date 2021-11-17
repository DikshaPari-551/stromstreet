package com.example.myapplication.Adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.customclickListner.CustomCommentLikeListener
import com.example.myapplication.customclickListner.CustomReplyListener
import com.example.myapplication.entity.Response.CommentList
import com.example.myapplication.entity.Response.Replies
import de.hdodenhof.circleimageview.CircleImageView

class Post2Adapter(
    var mContext: Context,
    var list: ArrayList<CommentList>,
    var customReplyListener : CustomReplyListener,
    var customCommentLikeListener : CustomCommentLikeListener
) : RecyclerView.Adapter<Post2Adapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemUsername = view.findViewById<TextView>(R.id.itemUsername)
        var commentText = view.findViewById<TextView>(R.id.commentText)
        var reply = view.findViewById<LinearLayout>(R.id.reply)
        var replyCount = view.findViewById<TextView>(R.id.reply_count)
        var likeCount = view.findViewById<TextView>(R.id.like_count)
        var likes = view.findViewById<LinearLayout>(R.id.likes)
        var commentLike = view.findViewById<ImageView>(R.id.comment_like)
        var profileImage = view.findViewById<CircleImageView>(R.id.profileImage)
        var commentRepliesRecyclerView = view.findViewById<RecyclerView>(R.id.commentReplies_RV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Post2Adapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.post2items, parent, false)
        return MyViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var commentId = list.get(position)._id
        holder.itemUsername.setText(list[position].userId.userName.toString())
        holder.commentText.setText(list[position].comment.toString())
        holder.replyCount.setText(list.get(position).replyCount.toString())
        if(list.get(position).likeCount > 0) {
            holder.likes.visibility = View.VISIBLE
            holder.likeCount.setText(list.get(position).likeCount.toString())
        }
        try {
            var filedata = list[position].userId.profilePic.toString()

            Glide.with(mContext).load(filedata).into(holder.profileImage);
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }
        var adaptor = RepliesCommentAdaptor(mContext,
            list.get(position).replies as ArrayList<Replies>
        )
        val layoutManager = LinearLayoutManager(mContext)
        holder.commentRepliesRecyclerView.layoutManager = layoutManager
        holder.commentRepliesRecyclerView.adapter = adaptor
        holder.reply.setOnClickListener {
            customReplyListener?.replyListener(holder.commentRepliesRecyclerView,position,commentId)
        }

        holder.commentLike.setOnClickListener {
            customCommentLikeListener.commentLikeListener(commentId)
        }

    }
}