
package com.example.myapplication.Adaptor

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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.customclickListner.CustomCommentLikeListener
import com.example.myapplication.entity.Response.Replies
import com.example.myapplication.util.DateFormat
import de.hdodenhof.circleimageview.CircleImageView

class RepliesCommentAdaptor(
    var mcontext: Context,
    var list: ArrayList<Replies>,
    var resources: Resources,
    var customCommentLikeListener: CustomCommentLikeListener
) : RecyclerView.Adapter<RepliesCommentAdaptor.RepliesCommentViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepliesCommentAdaptor.RepliesCommentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.replies_comment_list, parent, false)
        return RepliesCommentViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RepliesCommentAdaptor.RepliesCommentViewHolder, position: Int) {
        var replyCommentId = list.get(position)._id
        var formatdate = DateFormat.covertTimeOtherFormat(list.get(position).createdAt)
        holder.replierName.setText(list.get(position).userId.userName)
        holder.replierComment.setText(list.get(position).comment)
        holder.timeContainer.visibility = View.VISIBLE
        holder.commentTime.setText(formatdate)
        if (list.get(position).isLike == true) {
            holder.repliersLikeIcon.setImageDrawable(resources.getDrawable(R.drawable.heartred))
//            holder.repliersLikeIcon.setColorFilter(resources.getColor(R.color.red))
        } else {
            holder.repliersLikeIcon.setImageDrawable(resources.getDrawable(R.drawable.grey_heart))
        }
        if(list.get(position).likeCount > 0) {
            holder.likes.visibility = View.VISIBLE
            holder.like_count.setText(list.get(position).likeCount.toString())
        } else {
            holder.likes.visibility = View.GONE
        }


        try {
            var filedata = list[position].userId.profilePic
            Glide.with(mcontext).load(filedata).placeholder(R.drawable.circleprofile).into(holder.replierImage);
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }

        holder.repliersLikeIcon.setOnClickListener {
            customCommentLikeListener.commentLikeListener(replyCommentId,holder.repliersLikeIcon)
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
        var repliersLikeIcon = view.findViewById<ImageView>(R.id.repliersLikeIcon)
        var commentTime = view.findViewById<TextView>(R.id.comment_time)
        var timeContainer = view.findViewById<LinearLayout>(R.id.comment_time_container)

    }
}