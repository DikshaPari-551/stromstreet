package com.example.myapplication.socket

import android.app.Application
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

private const val URL = "https://975b-182-71-75-106.ngrok.io"
class SocketInstance : Application() {
    //socket.io connection url
    private var mSocket: Socket? = null

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
    fun getMSocket(): Socket? {
        return mSocket
    }
}