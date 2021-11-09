package com.example.myapplication.Fragments

import android.os.Bundle
import android.util.Log
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
import com.example.myapplication.entity.Response.Messages
import com.example.myapplication.socket.SocketManager
import com.example.myapplication.util.SavedPrefManager.Companion._id
import com.example.myapplication.util.SavedPrefManager.Companion.getStringPreferences
import kotlin.collections.ArrayList


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
        initializeSocket()
       // list = ArraySingleton.getInstance().array


        backButton.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                HomeFragment())?.commit()


        }



        return v
    }

    private fun initializeSocket()
    {
        onAddListeners()
        if (!socketInstance.isConnected) {
            socketInstance.connect()
        } else {
            //   onlineStatus()

        }

    }

    private fun onAddListeners()
    {
        socketInstance.initialize(object : SocketManager.SocketListener {
            override fun onConnected() {
                Log.e("browse_page_err", "omd " + "onConnected")

                // onlineStatus()
            }
            override fun onDisConnected() {
                socketInstance.connect()
            }

            override fun chatlist(listdat: ArrayList<Chalist>)
            {if(listdat!=null)
            {
                adaptor = activity?.let { Chat_Adaptor(it, listdat ) }!!
                val layoutManager = LinearLayoutManager(activity)
                recycler_view3.layoutManager = layoutManager
                recycler_view3.adapter = adaptor
            }
               }

            override fun viewchat(listdat: ArrayList<Messages>) {
                TODO("Not yet implemented")
            }

            override fun oneToOneChat(listdat: Messages)
            {

            }
        })}


}