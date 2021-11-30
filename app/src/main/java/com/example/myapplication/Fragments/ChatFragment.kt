package com.example.myapplication.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adaptor.Chat_Adaptor
import com.example.myapplication.R
import com.example.myapplication.entity.Response.Chalist
import com.example.myapplication.entity.Response.Messages
import com.example.myapplication.socket.SocketManager
import com.example.myapplication.util.SavedPrefManager
import com.example.myapplication.util.SavedPrefManager.Companion.USERID
import com.example.myapplication.util.SavedPrefManager.Companion.getStringPreferences
import kotlin.collections.ArrayList


class ChatFragment : Fragment() {
    lateinit var recycler_view3:RecyclerView
    lateinit var backButton:ImageView

    var USERID_data: String=""
    lateinit var search:EditText
    private val datalist: java.util.ArrayList<Chalist> = java.util.ArrayList<Chalist>()
     var adaptor:Chat_Adaptor? = null
    lateinit var socketInstance: SocketManager
    private var list: ArrayList<Chalist>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var v= inflater.inflate(R.layout.fragment_chat, container, false)
        recycler_view3=v.findViewById(R.id.recycler_view3)
        backButton=v.findViewById(R.id.back_arrow_chat)
        search=v.findViewById(R.id.search)

//




        backButton.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                HomeFragment())?.commit()


        }
        search.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
                //setTags(search_edittext,s.toString());
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
//                String styledText = "<font color=\"#FF00A0EB\">"+s+"</font>";
//                search_edittext.setText(styledText);
                FILTER_LIST(s.toString())
            }
        })


        return v
    }

    private fun FILTER_LIST(text: String)
    {
        val filteredList: java.util.ArrayList<Chalist> = java.util.ArrayList<Chalist>()

        for (item in datalist) {
            if(USERID_data.equals(item.receiverId._id))
            {
                if (item.senderId.fullName.toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item)
                }
            }
            else
            {
                if (item.receiverId.fullName.toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item)
                }
            }

        }
        try {
            adaptor!!.filterList(filteredList)
        }
        catch (e:NullPointerException)
        {

        }

    }

    private fun SocketInitalize() {
        USERID_data = getStringPreferences(context, USERID).toString()
        socketInstance = SocketManager.getInstance(context!!)
        socketInstance.CHAT_LIST(USERID_data)
        initializeSocket()
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
                datalist.clear()
                datalist.addAll(listdat)
                adaptor = activity?.let { Chat_Adaptor(it, datalist, USERID_data ) }!!
                val layoutManager = LinearLayoutManager(activity)
                recycler_view3.layoutManager = layoutManager
                recycler_view3.adapter = adaptor
            }
            else
            {
//                Toast.makeText(
//                    this, ,
//                    Toast.LENGTH_SHORT
//                ).show()
            }
            }

            override fun viewchat(listdat: ArrayList<Messages>) {
            }

            override fun oneToOneChat(listdat: Messages)
            {

            }
        })}

    override fun onResume() {
        super.onResume()
        SocketInitalize()
    }

}