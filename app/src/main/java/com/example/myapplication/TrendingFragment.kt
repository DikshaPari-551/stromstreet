package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class TrendingFragment : Fragment() {
    lateinit var textLocalPostTrending:TextView
    lateinit var textFollowingPostTrending:TextView
    var weather  : List<String> =listOf("Weather","Crime","Weater","Crime","Weather")
    var okhla  : List<String> =listOf("Okhla phase1","Okhla phase2","Okhla phase1","Okhla phase2","Okhla phase1")
    var event  : List<String> =listOf("Event","Traffic","Event","Traffic","Event")
    var lajpat  : List<String> =listOf("Lajpat Nagar","Okhla Saket","Lajpat Nagar","Saket","Lajpat Nagar")
    lateinit var recycler_view2: RecyclerView
    lateinit var trending_post_text:TextView

    lateinit var filter: ImageView

    lateinit var userTrendingImg:ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v= inflater.inflate(R.layout.fragment_trending, container, false)

        recycler_view2 = v.findViewById(R.id.recycler_view2)
        trending_post_text=v.findViewById(R.id.trending_post_text)
        textLocalPostTrending=v.findViewById(R.id.text_local_post_trending)
        textLocalPostTrending.setOnClickListener{
            textLocalPostTrending.setTextColor(resources.getColor(R.color.orange))
            trending_post_text.setText("Local Post")
            textFollowingPostTrending.setTextColor(resources.getColor(R.color.white))
        }

        textFollowingPostTrending=v.findViewById(R.id.text_following_post_trending)
        textFollowingPostTrending.setOnClickListener{
            textFollowingPostTrending.setTextColor(resources.getColor(R.color.orange))
            trending_post_text.setText("Following Post")
            textLocalPostTrending.setTextColor(resources.getColor(R.color.white))
        }

        userTrendingImg=v.findViewById(R.id.user_treanding_img)
        userTrendingImg.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(R.id.linear_layout,ProfileFragment())
                ?.commit()
        }
        filter=v.findViewById(R.id.filter)
        filter.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(R.id.linear_layout,secondFragment())
                ?.commit()

        }
        var adaptor = activity?.let { HomeAdaptor(weather,okhla,event,lajpat,it) }
        val layoutManager = LinearLayoutManager(activity)
        recycler_view2.layoutManager = layoutManager
        recycler_view2.adapter = adaptor

        return v
    }
}


