package com.example.myapplication.Adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Activities.ChatActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.entity.Response.Chalist
import com.example.myapplication.util.DateFormat
import de.hdodenhof.circleimageview.CircleImageView
import java.util.ArrayList

class Chat_Adaptor(
    var mcontext: Context,
    var list: ArrayList<Chalist>?,
   var USERID_data: String
) : RecyclerView.Adapter<Chat_Adaptor.MyViewHolder>() {
    lateinit var id: String
    lateinit var full_name: String

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var chat = view.findViewById<LinearLayout>(R.id.chat_layout)
        var imageset = view.findViewById<CircleImageView>(R.id.image)
        var followername = view.findViewById<TextView>(R.id.followername)
        var   tvUnreadCount = view.findViewById<TextView>(R.id.tvUnreadCount)
        var   time = view.findViewById<TextView>(R.id.time)
      //  var profileImage = view.findViewById<CircleImageView>(R.id.profileImage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Chat_Adaptor.MyViewHolder
    {
        var item = LayoutInflater.from(parent.context).inflate(R.layout.layout_message, null)
        return MyViewHolder(item)
    }

    override fun getItemCount(): Int
    {
        return list!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //try {
            if(list!!.get(position).totalUnreadMsg==0)
            {
                holder.tvUnreadCount.visibility=View.GONE
            }
            else
                if(list!!.get(position).totalUnreadMsg>0)
            {
                holder.tvUnreadCount.visibility=View.VISIBLE
                holder.tvUnreadCount.setText(list!!.get(position).totalUnreadMsg.toString())
                holder.time.setText(DateFormat.getDateOfhourminute(list!!.get(position).updatedAt.toString()))
            }

            if(USERID_data.equals(list!!.get(position).receiverId._id))
            {
                holder.followername.setText(list!!.get(position).senderId.fullName)
                id=list!!.get(position).senderId._id
                full_name=list!!.get(position).senderId.fullName
                var filedata = list!!.get(position).senderId.profilePic

                if(filedata!=null)
                {
                    Glide.with(mcontext).load(filedata).placeholder(R.drawable.circleprofile).into(holder.imageset);
                }

            }
            else{
                holder.followername.setText(list!!.get(position).receiverId.fullName)
                full_name= list!!.get(position).receiverId.fullName
                id=list!!.get(position).receiverId._id

                var filedata = list!!.get(position).receiverId.profilePic
                if(filedata!=null)
                {

                Glide.with(mcontext).load(filedata).placeholder(R.drawable.circleprofile).into(holder.imageset);
                }
            }




//        }catch (e: IndexOutOfBoundsException){
//            e.printStackTrace()
//        }
//        catch (e: NullPointerException){
//            e.printStackTrace()
//        }
        holder.chat.setOnClickListener {
            try {
                if(USERID_data.equals(list!!.get(position).receiverId._id))
                {
                    (mcontext as MainActivity).startActivity(Intent(mcontext, ChatActivity::class.java).putExtra("reciver_id",list!!.get(position).senderId._id).putExtra("username",list!!.get(position).senderId.fullName).putExtra("profileimage",list!!.get(position).senderId.profilePic))


                }
                else{
                    (mcontext as MainActivity).startActivity(Intent(mcontext, ChatActivity::class.java).putExtra("reciver_id",list!!.get(position).receiverId._id).putExtra("username",list!!.get(position).receiverId.fullName).putExtra("profileimage",list!!.get(position).receiverId.profilePic))

                }

            }catch (e: IndexOutOfBoundsException){
                e.printStackTrace()
            }
            catch (e: NullPointerException){
                e.printStackTrace()
            }
                 }
    }
}
