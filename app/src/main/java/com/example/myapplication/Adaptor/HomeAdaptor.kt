package com.example.myapplication.Adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Activities.PostActivity
import com.example.myapplication.Activities.PostActivity3
import com.example.myapplication.MainActivity
import com.example.myapplication.R

class HomeAdaptor(
    var weather: List<String> =listOf("Weather","Crime","Weater","Crime","Weather"),
    var okhla: List<String> =listOf("Okhla phase1","Okhla phase2","Okhla phase1","Okhla phase2","Okhla phase1"),
    var event: List<String> =listOf("Event","Traffic","Event","Traffic","Event"),
    var lajpat: List<String> =listOf("Lajpat Nagar"," Saket","Lajpat Nagar","Saket","Lajpat Nagar"),
    var mcontext: Context
):
    RecyclerView.Adapter<HomeAdaptor.MyViewHolder>() {
        inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
      var mweather=view.findViewById<TextView>(R.id.text_weather)
        var mokhla=view.findViewById<TextView>(R.id.text_okhla)
        var mevent=view.findViewById<TextView>(R.id.event_text)
        var mlajpat=view.findViewById<TextView>(R.id.lajpat_nagar_text)
            var layout_vedio=view.findViewById<RelativeLayout>(R.id.layout_vedio)
            var layout_post_img=view.findViewById<LinearLayout>(R.id.layout_post_img)

    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_layout, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
     holder.mokhla.setText(okhla[position])
        holder.mweather.setText(weather[position])
        holder.mevent.setText(event[position])
        holder.mlajpat.setText(lajpat[position])
        //(mcontexttt as MainActivity).startActivity(Intent(mcontexttt,PostActivity::class.java))
        holder.layout_vedio.setOnClickListener{
        (mcontext as MainActivity).startActivity(Intent(mcontext,
            PostActivity::class.java))
    }
        holder.layout_post_img.setOnClickListener{
            (mcontext as MainActivity).startActivity(Intent(mcontext,
                PostActivity3::class.java))
        }
    }
    override fun getItemCount(): Int {
        return 5
    }
}