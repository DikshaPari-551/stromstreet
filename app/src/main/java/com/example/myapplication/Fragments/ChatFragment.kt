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
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException


class ChatFragment : Fragment() {
    lateinit var recycler_view3:RecyclerView
    lateinit var backButton:ImageView

    lateinit var adaptor:Chat_Adaptor
    private var mSocket: Socket? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        try {
            mSocket = IO.socket("http://172.16.2.19:3027")
        } catch (e: URISyntaxException) {
        }
        var v= inflater.inflate(R.layout.fragment_chat, container, false)
        recycler_view3=v.findViewById(R.id.recycler_view3)
        backButton=v.findViewById(R.id.back_arrow_chat)

         adaptor = activity?.let { Chat_Adaptor(it) }!!
        val layoutManager = LinearLayoutManager(activity)
        recycler_view3.layoutManager = layoutManager
        recycler_view3.adapter = adaptor
        backButton.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                HomeFragment())?.commit()


        }
        mSocket!!.connect();


        return v
    }

}