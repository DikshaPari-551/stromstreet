package com.example.myapplication.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.myapplication.R
import com.example.myapplication.socket.SocketManager

import io.socket.client.IO
import io.socket.client.Socket

class Checksocket : AppCompatActivity() {
    lateinit var socketInstance: SocketManager
    lateinit var socketList: SocketManager.SocketListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checksocket)
        SocketManager.getInstance(this).initialize(socketList)

        socketInstance = SocketManager.getInstance(this)
        SocketManager.getInstance(this).connect()
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
}