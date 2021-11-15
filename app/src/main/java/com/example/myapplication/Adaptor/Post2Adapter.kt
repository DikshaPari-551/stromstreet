package com.example.myapplication.Adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.customclickListner.CustomReplyListener
import com.example.myapplication.entity.Response.CommentList
import com.example.myapplication.util.SavedPrefManager
import de.hdodenhof.circleimageview.CircleImageView

class Post2Adapter(
    var mcontext: Context,
    var list: ArrayList<CommentList>,
    var customReplyListener: CustomReplyListener?
) : RecyclerView.Adapter<Post2Adapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemUsername = view.findViewById<TextView>(R.id.itemUsername)
        var commentText = view.findViewById<TextView>(R.id.commentText)
        var reply = view.findViewById<TextView>(R.id.reply)
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
        holder.itemUsername.setText(list[position].userId.userName.toString())
        holder.commentText.setText(list[position].comment.toString())
        try {
            var filedata = list[position].userId.profilePic.toString()

            Glide.with(mcontext).load(filedata).into(holder.profileImage);
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }
        holder.reply.setOnClickListener {
            SavedPrefManager.saveStringPreferences(
                mcontext,
                SavedPrefManager.COMMENT_ID,
                list.get(position)._id
            )
            customReplyListener?.replyListener(holder.commentRepliesRecyclerView)
        }

    }
}