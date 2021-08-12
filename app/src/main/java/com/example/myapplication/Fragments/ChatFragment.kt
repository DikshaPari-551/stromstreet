package com.example.myapplication.Fragments

import android.content.Intent.getIntent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adaptor.Chat_Adaptor
import com.example.myapplication.R


class ChatFragment : Fragment() {
    lateinit var recycler_view3:RecyclerView
    lateinit var backButton:ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v= inflater.inflate(R.layout.fragment_chat, container, false)
        recycler_view3=v.findViewById(R.id.recycler_view3)
        backButton=v.findViewById(R.id.back_arrow_chat)
        backButton.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                HomeFragment())?.commit()
        }
//        var extras = getIntent("openF2").getExtras();
//        if(extras!=null && extras.containsKey("openF2"))
//            val openF2 = extras?.getBoolean("openF2");
//        if(openF2 == true){
//            //add or replace fragment F2 in container
//        }
//    }

        var adaptor = activity?.let { Chat_Adaptor(it) }
        val layoutManager = LinearLayoutManager(activity)
        recycler_view3.layoutManager = layoutManager
        recycler_view3.adapter = adaptor

        return v
    }

}