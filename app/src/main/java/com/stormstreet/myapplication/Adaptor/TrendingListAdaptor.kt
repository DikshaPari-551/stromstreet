package com.stormstreet.myapplication.Adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stormstreet.myapplication.Fragments.TrendingFragment
import com.stormstreet.myapplication.R
import com.stormstreet.myapplication.customclickListner.CustomClickListnerdelete
import com.stormstreet.myapplication.entity.Response.Docss
import com.stormstreet.sleeponcue.extension.diasplay_toast


class TrendingListAdaptor(

    var context: TrendingFragment,
    var list: ArrayList<Docss>,
    var listener: CustomClickListnerdelete

) : RecyclerView.Adapter<TrendingListAdaptor.MyViewHolder>()
{
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var layout_vedio=view.findViewById<RelativeLayout>(R.id.layout_vedio)
        var postView=view.findViewById<ImageView>(R.id.postView)
        var name = view.findViewById<TextView>(R.id.name)
        var bio = view.findViewById<TextView>(R.id.bio)
        var text_okhla = view.findViewById<TextView>(R.id.text_okhla)
        var mainlayout = view.findViewById<LinearLayout>(R.id.mainlayout)
        var text_weather = view.findViewById<TextView>(R.id.text_weather)
        var videoIcon = view.findViewById<ImageView>(R.id.video_icon)
        var imageIcon = view.findViewById<ImageView>(R.id.image_icon)
        var usernameMain = view.findViewById<RelativeLayout>(R.id.usernameMain)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingListAdaptor.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TrendingListAdaptor.MyViewHolder, position: Int) {
        try {
            for(i in 0 until list.size) {
                holder.name.setText(list[position].userDetails.userName.toString())
                holder.bio.setText(list[position].description.toString())
                holder.text_okhla.setText(list[position].address)
                holder.text_weather.setText(list[position].categoryDetails.get(i).categoryName)
                if (list[position].mediaType.toLowerCase().equals("video")){
                    holder.videoIcon.visibility = View.VISIBLE
                    var filedata = list[position].thumbNail
                    Glide.with(context).load(filedata).into(holder.postView);
                }
                else{
                    if(list.get(position).imageLinks.size > 1) {
                        holder.imageIcon.visibility = View.VISIBLE
                        var filedata = list[position].imageLinks[0]
                        Glide.with(context).load(filedata).into(holder.postView);
                    } else {
                        var filedata = list[position].imageLinks[0]
                        Glide.with(context).load(filedata).into(holder.postView);
                    }
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
        }


        holder.mainlayout.setOnClickListener {
            try {
            listener.customClick(list.get(position),"profile",position)
            }
            catch (e:IndexOutOfBoundsException)
            {
                context.diasplay_toast("oops something wrong!!.")
            }
        }

        holder.usernameMain.setOnClickListener {
            try {
                listener.customClick(list.get(position),"userid",position)
            }
            catch (e:IndexOutOfBoundsException)
            {
                context.diasplay_toast("oops something wrong!!.")
            }
        }
    }


}