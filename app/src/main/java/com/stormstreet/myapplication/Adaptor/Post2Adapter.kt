package com.stormstreet.myapplication.Adaptor

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stormstreet.myapplication.R
import com.stormstreet.myapplication.customclickListner.CustomCommentLikeListener
import com.stormstreet.myapplication.customclickListner.CustomReplyListener
import com.stormstreet.myapplication.entity.Response.CommentList
import com.stormstreet.myapplication.entity.Response.Replies
import com.stormstreet.myapplication.util.DateFormat
import de.hdodenhof.circleimageview.CircleImageView

class Post2Adapter(
    var mContext: Context,
    var list: ArrayList<CommentList>,
    var customReplyListener: CustomReplyListener,
    var customCommentLikeListener: CustomCommentLikeListener,
    var resources: Resources
) : RecyclerView.Adapter<Post2Adapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemUsername = view.findViewById<TextView>(R.id.itemUsername)
        var commentText = view.findViewById<TextView>(R.id.commentText)
        var reply = view.findViewById<LinearLayout>(R.id.reply)
        var replyCount = view.findViewById<TextView>(R.id.reply_count)
        var likeCount = view.findViewById<TextView>(R.id.like_count)
        var commentTime = view.findViewById<TextView>(R.id.comment_time)
        var likes = view.findViewById<LinearLayout>(R.id.likes)
        var commentLike = view.findViewById<ImageView>(R.id.comment_like)
        var timeContainer = view.findViewById<LinearLayout>(R.id.time_container)
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var commentId = list.get(position)._id
        var formatdate = DateFormat.covertTimeOtherFormat(list.get(position).createdAt)
        try
        {

            holder.commentText.setText(list[position].comment.toString())
            holder.replyCount.setText(list.get(position).replyCount.toString())

            holder.timeContainer.visibility = View.VISIBLE
            holder.commentTime.setText(formatdate)
            holder.itemUsername.setText(list[position].userId.userName.toString())
        }
        catch (e:NullPointerException)
        {

        }


        if (list.get(position).isLike == true) {
//            holder.commentLike.setColorFilter(resources.getColor(R.color.red))
            holder.commentLike.setImageDrawable(resources.getDrawable(R.drawable.heartred))
        } else {
            holder.commentLike.setImageDrawable(resources.getDrawable(R.drawable.grey_heart))
        }
        if (list.get(position).likeCount > 0) {
            holder.likes.visibility = View.VISIBLE
            holder.likeCount.setText(list.get(position).likeCount.toString())
        } else {
            holder.likes.visibility = View.GONE
        }
        try {
            var filedata = list[position].userId.profilePic.toString()

            Glide.with(mContext).load(filedata).placeholder(R.drawable.circleprofile).into(holder.profileImage);
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }
        var adaptor = RepliesCommentAdaptor(
            mContext,
            list.get(position).replies as ArrayList<Replies>, resources, customCommentLikeListener
        )
        val layoutManager = LinearLayoutManager(mContext)
        holder.commentRepliesRecyclerView.layoutManager = layoutManager
        holder.commentRepliesRecyclerView.adapter = adaptor
        holder.reply.setOnClickListener {
            customReplyListener?.replyListener(
                holder.commentRepliesRecyclerView,
                position,
                commentId
            )
        }

        holder.commentLike.setOnClickListener {
            customCommentLikeListener.commentLikeListener(commentId, holder.commentLike)
        }

    }
}