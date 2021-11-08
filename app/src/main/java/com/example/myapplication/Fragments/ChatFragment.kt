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
import com.example.myapplication.Singleton.ArraySingleton
import com.example.myapplication.entity.Response.Chalist
import com.example.myapplication.socket.SocketManager
import com.example.myapplication.util.SavedPrefManager.Companion._id
import com.example.myapplication.util.SavedPrefManager.Companion.getStringPreferences
import java.util.ArrayList


class ChatFragment : Fragment() {
    lateinit var recycler_view3:RecyclerView
    lateinit var backButton:ImageView
    var USERID: String=""
    lateinit var adaptor:Chat_Adaptor
    lateinit var socketInstance: SocketManager
    private var list: ArrayList<Chalist>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var v= inflater.inflate(R.layout.fragment_chat, container, false)
        USERID = getStringPreferences(context!!, _id).toString()

        recycler_view3=v.findViewById(R.id.recycler_view3)
        backButton=v.findViewById(R.id.back_arrow_chat)
        socketInstance = SocketManager.getInstance(context!!)
        socketInstance.CHAT_LIST(USERID)
        list = ArraySingleton.getInstance().array

        adaptor = activity?.let { Chat_Adaptor(it, list ) }!!
        val layoutManager = LinearLayoutManager(activity)
        recycler_view3.layoutManager = layoutManager
        recycler_view3.adapter = adaptor
        backButton.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                HomeFragment())?.commit()


        }



        return v
    }

}