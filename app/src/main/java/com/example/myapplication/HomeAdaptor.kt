package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import layout.modal_class
import org.w3c.dom.Text

class HomeAdaptor(
    var weather: List<String> =listOf("Weather","Crime","Weater","Crime","Weather"),
    var okhla: List<String> =listOf("Okhla phase1","Okhla phase2","Okhla phase1","Okhla phase2","Okhla phase1"),
    var event: List<String> =listOf("Event","Traffic","Event","Traffic","Event"),
    var lajpat: List<String> =listOf("Lajpat Nagar"," Saket","Lajpat Nagar","Saket","Lajpat Nagar")):
    RecyclerView.Adapter<HomeAdaptor.MyViewHolder>() {
        inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
      var mweather=view.findViewById<TextView>(R.id.text_weather)
        var mokhla=view.findViewById<TextView>(R.id.text_okhla)
        var mevent=view.findViewById<TextView>(R.id.event_text)
        var mlajpat=view.findViewById<TextView>(R.id.lajpat_nagar_text)

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

    }
    override fun getItemCount(): Int {
        return 5
    }
}