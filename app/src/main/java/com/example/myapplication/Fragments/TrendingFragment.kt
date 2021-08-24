package com.example.myapplication.Fragments

import android.content.Intent
import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adaptor.HomeAdaptor
import com.example.myapplication.LoginActivity
import com.example.myapplication.R
import com.mobiloitte.hrms.utils.SavedPrefManager


class TrendingFragment : Fragment() {
    lateinit var Go : LinearLayout
    lateinit var textLocalPostTrending:TextView
    lateinit var textFollowingPostTrending:TextView
    var weather  : List<String> =listOf("Weather","Crime","Weater","Crime","Weather")
    var okhla  : List<String> =listOf("Okhla phase1","Okhla phase2","Okhla phase1","Okhla phase2","Okhla phase1")
    var event  : List<String> =listOf("Event","Traffic","Event","Traffic","Event")
    var lajpat  : List<String> =listOf("Lajpat Nagar","Okhla Saket","Lajpat Nagar","Saket","Lajpat Nagar")
    lateinit var recycler_view2: RecyclerView
    lateinit var trending_post_text:TextView
    lateinit var trandingBackButton: ImageView

    lateinit var filter: ImageView

    lateinit var userTrendingImg:ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v= inflater.inflate(R.layout.fragment_trending, container, false)

        Go = v.findViewById(R.id.go)



        recycler_view2 = v.findViewById(R.id.recycler_view2)
        trending_post_text=v.findViewById(R.id.trending_post_text)
        trandingBackButton=v.findViewById(R.id.back_arrow_tranding)

        trandingBackButton.setOnClickListener{
            fragmentManager?.beginTransaction()?.replace(R.id.linear_layout, TrendingFragment())?.commit()

        }

        textLocalPostTrending=v.findViewById(R.id.text_local_post_trending)
        textLocalPostTrending.setOnClickListener{
            textLocalPostTrending.setTextColor(resources.getColor(R.color.orange))
            trending_post_text.setText("Local Activity")
            textFollowingPostTrending.setTextColor(resources.getColor(R.color.white))
            trandingBackButton.visibility = View.VISIBLE
            userTrendingImg.visibility = View.GONE
            filter.visibility =View.GONE

        }

        textFollowingPostTrending=v.findViewById(R.id.text_following_post_trending)
        textFollowingPostTrending.setOnClickListener{
            textFollowingPostTrending.setTextColor(resources.getColor(R.color.orange))
            trending_post_text.setText("Following Activity")
            textLocalPostTrending.setTextColor(resources.getColor(R.color.white))
            trandingBackButton.visibility = View.VISIBLE
            userTrendingImg.visibility = View.GONE
            filter.visibility =View.GONE
        }

        userTrendingImg=v.findViewById(R.id.user_treanding_img)
        userTrendingImg.setOnClickListener{
            if(  SavedPrefManager.getStringPreferences(activity,  SavedPrefManager.KEY_IS_LOGIN).equals("true")){
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                ProfileFragment()
            )
                ?.commit()
        }else{
                val i = Intent(activity, LoginActivity::class.java)
                startActivity(i)
            }
        }
        filter=v.findViewById(R.id.filter)
        filter.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                secondFragment()
            )
                ?.commit()

        }
        var adaptor = activity?.let {
            HomeAdaptor(
                weather,
                okhla,
                event,
                lajpat,
                it
            )
        }
        val layoutManager = LinearLayoutManager(activity)
        recycler_view2.layoutManager = layoutManager
        recycler_view2.adapter = adaptor
        return v


    }
}


