package com.stormstreet.myapplication.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.stormstreet.myapplication.R
import com.stormstreet.myapplication.entity.Response.Chalist
import com.stormstreet.myapplication.entity.Response.Messages
import com.stormstreet.myapplication.socket.SocketManager

class Checksocket : AppCompatActivity() {
    lateinit var socketInstance: SocketManager
    lateinit var socketList: SocketManager.SocketListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checksocket)

        socketInstance = SocketManager.getInstance(this)
       // SocketManager.getInstance(this).initialize(socketList)
        initializeSocket()
       // ONLINE_USER()
//        try {
//            Log.e("browse_page_err", "" +  "Socket Connect--")
//
//            val options = IO.Options()
//            options.reconnection = true
//            options.reconnectionDelay = 100
//            options.reconnectionAttempts = 40
//            options.secure = true
//            options.timeout = 900000
//         var socket  = IO.socket("https://4654-182-71-75-106.ngrok.io")
//            socket.on(Socket.EVENT_CONNECT) {
//                Handler(Looper.getMainLooper()).post {
//                    if (socket.connected()) {
//                      var  socketId = socket.id()
////                        Log.d(TAG, "Socket Connected :- " + socketId)
////                        Log.e("browse_page_err", "" +  "Socket Connect"+socketId)
//                      //  socketListener.onConnected()
//                    }
//                }
//            }.on(Socket.EVENT_DISCONNECT) { args ->
//                Handler(Looper.getMainLooper()).post {
//                    if (args != null && args.isNotEmpty()) {
//                      //  Log.d(TAG, "Socket NotConnect :- ")
//                        Log.e("browse_page_err", "" +  "Socket NotConnect")
//                    }
//                }
//            }
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//            Log.e("browse_page_err---", "" +  ex.message)
//
//         //   Log.d(TAG, ex.toString())
//            Log.e("browse_page_err---", "" +  ex.toString())
//            Log.e("browse_page_err---", "" +  ex.message)
//
//        }
    }

//    private fun ONLINE_USER() {
//        val jsonObject = JSONObject()
//            .put("userId", "6177b1bc38ccc313ed3ff099")
//        //socket?.on("new message", onNewMessage);
//        socketInstance.ONLINE_USER(jsonObject)
//    }

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
    private fun onAddListeners() {

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
                TODO("Not yet implemented")
            }

            override fun oneToOneChat(listdat: Messages) {
                TODO("Not yet implemented")
            }
        })}
}