package com.example.myapplication.Fragments

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
    lateinit var userChatImg:ImageView
    lateinit var filterChatFrag:ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v= inflater.inflate(R.layout.fragment_chat, container, false)
        recycler_view3=v.findViewById(R.id.recycler_view3)
        userChatImg=v.findViewById(R.id.user_chat_frag)
        userChatImg.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                ProfileFragment())?.commit()
        }
        filterChatFrag=v.findViewById(R.id.filter_chat_frag)
        filterChatFrag.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                secondFragment())?.commit()
        }
        var adaptor = activity?.let { Chat_Adaptor(it) }
        val layoutManager = LinearLayoutManager(activity)
        recycler_view3.layoutManager = layoutManager
        recycler_view3.adapter = adaptor

        return v
    }

}