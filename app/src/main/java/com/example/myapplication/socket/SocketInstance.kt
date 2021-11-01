package com.example.myapplication.socket

import android.app.Application
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

private const val URL = "http://node-stromestreet.mobiloitte.com"
class SocketInstance : Application() {
    //socket.io connection url
      private lateinit var mSocket: Socket

    override fun onCreate() {
        super.onCreate()
        try {
//creating socket instance
            mSocket = IO.socket(URL)
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }
    }

    //return socket instance
    fun getMSocket(): Socket {
        return mSocket
    }
}