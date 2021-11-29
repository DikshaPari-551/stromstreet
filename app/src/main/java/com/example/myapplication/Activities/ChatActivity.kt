package com.example.myapplication.Activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adaptor.MessageAdaptor
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.entity.Response.Chalist
import com.example.myapplication.entity.Response.Messages
import com.example.myapplication.socket.SocketManager
import com.example.myapplication.util.SavedPrefManager
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import android.os.Looper
import android.view.View
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView


class ChatActivity : AppCompatActivity() {
    lateinit var add: EditText
    lateinit var adaptor:MessageAdaptor
    lateinit var right_arrow_Chat:ImageView
    lateinit var sendImgIcon: ImageView
    lateinit var backButtton: ImageView
    lateinit var userprofile: CircleImageView

    lateinit var user_name: TextView
    var mContext: Context = this
    lateinit var recyclerList: RecyclerView
    lateinit var chat_layout: LinearLayout
    lateinit var list_view: ListView
    var arr: ArrayList<HashMap<String, String>> = arrayListOf()
    lateinit var  socket: Socket
    var USERID:String=""
    lateinit var listdatlist: ArrayList<Messages>
    var reciver_id:String=""
    var username:String=""
    var profileimage:String=""
    var i=0;
    var  flag:Boolean=false
    lateinit var timer:Timer
    lateinit var adapter: Adapter
    private var hasConnection = false
    lateinit var socketInstance: SocketManager
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.black)
        }
        chat_layout = findViewById(R.id.chat_Activity)
        backButtton = findViewById(R.id.right_arrow)
        userprofile = findViewById(R.id.userprofile)
        recyclerList = findViewById(R.id.rcycler_list)
        user_name= findViewById(R.id.username)
        sendImgIcon = findViewById(R.id.send_img_icon)
        add = findViewById(R.id.text_add)


        socketInstance = SocketManager.getInstance(this)
        GETINTENT()
        sendImgIcon.setOnClickListener {
            if (!add.text.toString().equals("") && add.text.toString() != null) {
                var text = add.text.toString()
//
                socketInstance.Update(text, USERID, reciver_id)
                add.setText("")
                //VIEWCHAT()
            }
            else{
                Toast.makeText(mContext, "Please Input Something", Toast.LENGTH_SHORT).show()
            }
        }

        backButtton.setOnClickListener {
            timer.cancel()
            finish()

        }
        layoutManager = LinearLayoutManager(baseContext)
        recyclerList.layoutManager = layoutManager
        if(savedInstanceState != null){
            hasConnection = savedInstanceState.getBoolean("hasConnection");
        }
        // SocketManager.getInstance(this).initialize(socketList)
        initializeSocket()
        socketInstance.ONLINE_USER(USERID)
//        Handler(Looper.getMainLooper()).postDelayed({
//
//        }, 1500)
        //VIEWCHAT()
        CHATTIMER()

        initScrollListener()

    }

    private fun CHATTIMER() {
        timer= Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                VIEWCHAT()
            }
        }, 0, 1500)
    }

    private fun initScrollListener()
    {
        recyclerList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (dy < 0) {
                    flag=true
                }
            }
        })
        recyclerList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerList.canScrollVertically(1)) {
                    flag=false
                }
            }
        })
    }

    private fun GETINTENT()
    {
        if (getIntent() != null)
        {
            if (intent.getStringExtra("reciver_id") != null) {
                reciver_id = intent.getStringExtra("reciver_id")!!
            }
            if (intent.getStringExtra("username") != null) {
                username = intent.getStringExtra("username")!!
                user_name.setText(username)
            }
            if (intent.getStringExtra("profileimage") != null) {
                profileimage = intent.getStringExtra("profileimage")!!
                Glide.with(this).load(profileimage).placeholder(R.drawable.circleprofile).into(userprofile);
            }


        }
        USERID = SavedPrefManager.getStringPreferences(this,SavedPrefManager.USERID).toString()
//        socketInstance.VIEWcHAT(
//            USERID,reciver_id)
//        Thread { runOnUiThread {
//            socketInstance.VIEWcHAT(
//                USERID,reciver_id) } }.start()
        //     VIEWCHAT()



    }

    private fun VIEWCHAT() {
        socketInstance.VIEWcHAT(
            USERID,reciver_id)
        System.out.println("USERID="+USERID+"reciver_id"+reciver_id)
    }


    private fun ONLINE_USER() {

        //socketInstance.socket!!.on("onlineUser",OnlineUser);
    }

    private fun initializeSocket() {
        // if (ConnectionDetector.getInstance(this).isNetworkAvailable) {
        onAddListeners()
        if (!socketInstance.isConnected) {
            socketInstance.connect()
        } else {
            //   onlineStatus()

        }

//        } else {
//            // showData()
//        }
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

            override fun chatlist(listdat: ArrayList<Chalist>) {

            }

            override fun viewchat(listdat: ArrayList<Messages>) {
                if(listdat!=null)
                {listdatlist=listdat
                    if(i==0)
                    {
                        adaptor = MessageAdaptor(listdatlist,USERID)
                        recyclerList.adapter = adaptor
                        adaptor.notifyDataSetChanged()
                    }
                    else{

//                        listdatlist.add(removeDuplocatElemts(listdatlist).get(listdatlist.size-1))
//
//                        recyclerList.adapter!!.notifyDataSetChanged()
//                        System.out.println("listdatsize="+listdat.size)
                    }

                    if(flag)
                    {

                    }
                    else{
                        System.out.println("listdatsize="+listdat.size)
                        adaptor = MessageAdaptor(listdatlist,USERID)
                        recyclerList.adapter = adaptor
                        adaptor.notifyDataSetChanged()
                        recyclerList.scrollToPosition(removeDuplocatElemts(listdatlist).size - 1)

                    }

                }
                i++
            }

            override fun oneToOneChat(listdatset: Messages) {
//                listdatlist.add(listdatset)
//                recyclerList.adapter!!.notifyDataSetChanged()
//                recyclerList.smoothScrollToPosition(listdatlist.count());
            }
        }


        )
        //socketInstance.addListener("viewChat",SocketManager.SocketMessageListener)
        socketInstance.addListener("viewChat", object : SocketManager.SocketMessageListener {
            override fun onMessage(vararg args: Any) {
                val data = args[0] as JSONObject
                Log.e("browse_page_err", "sendMessage " + data.toString())

            }
        })

    }

    private fun removeDuplocatElemts(listdatlist: ArrayList<Messages>):  ArrayList<Messages>
    {
        var copyChatList: java.util.ArrayList<Messages> = ArrayList()
        for (i in 0..listdatlist.size - 1) {
            if (!copyChatList.contains(listdatlist[i])) {
                copyChatList.add(listdatlist[i])
            }
        }

        return copyChatList
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("hasConnection", hasConnection)
    }




    override fun onDestroy() {
        super.onDestroy()
        socketInstance.socket.disconnect()

        //socket!!.off("oneToOneChat", onNewMessage);
    }
//    object onNewMessage : Emitter.Listener {
//        override fun call(vararg args: Any?)
//        {
//
//            val jsonObject = JSONObject()
//                    .put("userId", "616dccdab83a9818f8080f3c")
//            socket!!.emit("oneToOneChat", jsonObject);
//        }
//
//
//    }


    object onConnectError : Emitter.Listener
    {
        override fun call(vararg args: Any?)
        {
            Log.d("checks",args.toString())

        }

    }
    object onConnect : Emitter.Listener {
        override fun call(vararg args: Any?) {
            Log.d("chseck",args.toString())
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        timer.cancel()
        finish()
    }

    override fun onResume() {
        super.onResume()
        CHATTIMER()
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
    }
}





