package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    var weather  : List<String> =listOf("Weather","Crime","Weater","Crime","Weather")
    var okhla  : List<String> =listOf("Okhla phase1","Okhla phase2","Okhla phase1","Okhla phase2","Okhla phase1")
    var event  : List<String> =listOf("Event","Traffic","Event","Traffic","Event")
    var lajpat  : List<String> =listOf("Lajpat Nagar","Okhla Saket","Lajpat Nagar","Saket","Lajpat Nagar")
    lateinit var recycler_view2: RecyclerView

    lateinit var recycler_view1: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var v= inflater.inflate(R.layout.fragment_home, container, false)
        recycler_view1 = v.findViewById(R.id.recycler_view1)

        var filter=v.findViewById<ImageView>(R.id.filter)
        filter.setColorFilter(resources.getColor(R.color.white))
        var adaptor = HomeAdaptor(weather,okhla,event,lajpat)
        val layoutManager = LinearLayoutManager(activity)
        recycler_view1.layoutManager = layoutManager
        recycler_view1.adapter = adaptor

        return v
    }


}