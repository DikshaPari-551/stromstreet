package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    var weather: List<String> = listOf("Weather", "Crime", "Weater", "Crime", "Weather")
    var okhla: List<String> =
        listOf("Okhla phase1", "Okhla phase2", "Okhla phase1", "Okhla phase2", "Okhla phase1")
    var event: List<String> = listOf("Event", "Traffic", "Event", "Traffic", "Event")
    var lajpat: List<String> =
        listOf("Lajpat Nagar", "Okhla Saket", "Lajpat Nagar", "Saket", "Lajpat Nagar")
    lateinit var recycler_view2: RecyclerView
    lateinit var localpost: TextView
    lateinit var followingPost: TextView

    lateinit var home_text: TextView
    lateinit var recycler_view1: RecyclerView
    lateinit var filter: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_home, container, false)
        recycler_view1 = v.findViewById(R.id.recycler_view1)
        home_text=v.findViewById(R.id.home_text)
        localpost = v.findViewById(R.id.text_local_post)
        followingPost = v.findViewById(R.id.text_following_post)
     followingPost.setOnClickListener{
         followingPost.setTextColor(resources.getColor(R.color.orange))
         home_text.setText("Following Post")
         localpost.setTextColor(resources.getColor(R.color.white))
     }
        localpost.setOnClickListener{
            followingPost.setTextColor(resources.getColor(R.color.white))
            home_text.setText("Local Post")
            localpost.setTextColor(resources.getColor(R.color.orange))
        }

        filter = v.findViewById(R.id.filter)
        filter.setOnClickListener {
            getFragmentManager()?.beginTransaction()?.replace(R.id.linear_layout, secondFragment())
                ?.commit()

        }
        var adaptor = HomeAdaptor(weather, okhla, event, lajpat)
        val layoutManager = LinearLayoutManager(activity)
        recycler_view1.layoutManager = layoutManager
        recycler_view1.adapter = adaptor

        return v
    }


}