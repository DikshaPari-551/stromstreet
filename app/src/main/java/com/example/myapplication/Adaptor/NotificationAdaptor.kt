package com.example.myapplication.Adaptor

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Activities.NotificationActivity
import com.example.myapplication.R
import com.example.myapplication.customclickListner.CustomClickListner2
import com.example.myapplication.entity.Response.Docss
import com.example.myapplication.util.DateFormat
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.Exception

class NotificationAdaptor(
    var mcontext: Context,
    var list: ArrayList<Docss>,
    var customClickListner2: CustomClickListner2
) : RecyclerView.Adapter<NotificationAdaptor.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var user_image = view.findViewById<CircleImageView>(R.id.user_image)
        var postTitle = view.findViewById<TextView>(R.id.post_title)
        var postBody = view.findViewById<TextView>(R.id.post_body)
        var postTime = view.findViewById<TextView>(R.id.post_time)
        var postImage = view.findViewById<ImageView>(R.id.post_image)
        var notificatiClick = view.findViewById<RelativeLayout>(R.id.notificati_click)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationAdaptor.MyViewHolder {
        var item = LayoutInflater.from(parent.context).inflate(R.layout.notification_list, null)
        return MyViewHolder(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        try {
            if (list != null) {
                holder.postTitle.setText(list[position].title)
                holder.postBody.setText(list[position].body)
                holder.postTime.setText(DateFormat.covertTimeOtherFormat(list.get(position).createdAt.toString()))
                var filedata = list[position].notificationBy.profilePic
                Glide.with(mcontext).load(filedata).placeholder(R.drawable.circleprofile)
                    .into(holder.user_image)
                if (list.get(position).postId.mediaType.equals("IMAGE")) {
                    Glide.with(mcontext).load(list.get(position).postId.imageLinks.get(0))
                        .into(holder.postImage)
                } else {
                    Glide.with(mcontext).load(list.get(position).postId.thumbNail)
                        .into(holder.postImage)
                }

            } else {
                Toast.makeText(mcontext, "No notification available", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        holder.notificatiClick.setOnClickListener {
            customClickListner2.customClick(list.get(position),"notification")
        }
    }
}