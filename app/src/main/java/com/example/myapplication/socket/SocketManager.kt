package com.example.myapplication.socket
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.myapplication.Activities.ChatActivity
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

class SocketManager private constructor(context: Context) {

    val isConnected: Boolean get() = socket.connected()
    private lateinit var socketId: String
    lateinit var socket: Socket
    lateinit var socketListener: SocketListener
    /*Method to connect Socket */
    fun connect() {
        if (!socket.connected())
            socket.connect()
    }


    fun sendMsg(key: String, vararg args: Any)
    {
        if (socket.connected()) {
            socket.emit(key, *args)
            Log.e("browse_page_err", "ooo" +  "Socket Connect--"+key+" "+args.toString())

        }
    }
     fun ONLINE_USER(jsonObject: JSONObject)
     {
         socket!!.emit("onlineUser",jsonObject.toString());
         if(socket!!.connected()==true)
         {
             System.out.println("check"+toString())
         }
     }
    fun ONLINE_USER_LISTENER()
    {
        socket!!.on("onlineUser", ChatActivity.onNewMessage);
    }
    fun removeListener(key: String) {
        socket.off(key)
    }

    /* Add Listener to Socket*/
    fun addListener(key: String) {
        Log.e("browse_page_err", "getcheck" +  "Socket Connect--"+key)

        socket.on(key) { args ->
            Log.e("browse_page_err", "wow" +  "Socket Connect--"+key+" "+args.toString())
            Handler(Looper.getMainLooper()).post {
                if (args != null && args.isNotEmpty()) {
                    //socketMessageListener.onMessage(*args)
                }
            }
        }
    }


    fun initialize(socketList: SocketListener) {
        socketListener = socketList
    }

    /* Disconnect Socket*/
    fun disConnect() {
        if (socket.connected())
            socket.disconnect()
    }


    interface SocketListener {
        fun onConnected()
        fun onDisConnected()
    }

    /* Interface to Handle Message event of Socket*/
    interface SocketMessageListener {
        fun onMessage(vararg args: Any)
    }

    companion object {
        private val TAG = SocketManager::class.java.simpleName
        //lateinit var socket: Socket

        @get:Synchronized
        var socketManager: SocketManager? = null

        /**
         * Method to get the instance of socket class
         *
         * @param context
         * @return
         */

        fun getInstance(context: Context): SocketManager {
            if (socketManager == null) {
                socketManager = SocketManager(context)
            }
            return socketManager as SocketManager
        }
    }

    init {
        try {
            Log.e("browse_page_err", "" +  "Socket Connect--")

            val options = IO.Options()
            options.reconnection = true
            options.reconnectionDelay = 100
            options.reconnectionAttempts = 40
            options.secure = true
            options.timeout = 900000
            socket = IO.socket("https://node-stromestreet.mobiloitte.com")
            socket.on(Socket.EVENT_CONNECT) {
                Handler(Looper.getMainLooper()).post {
                    if (socket.connected()) {
                        socketId = socket.id()
                        System.out.println("Socketid="+socketId.toString())
                        Log.d(TAG, "Socket Connected :- " + socketId)
                        Log.e("browse_page_err", "" +  "Socket Connect"+socketId)
                        socketListener.onConnected()
                    }
                }
            }.on(Socket.EVENT_DISCONNECT) { args ->
                Handler(Looper.getMainLooper()).post {
                    if (args != null && args.isNotEmpty())
                    {
                        Log.d(TAG, "Socket NotConnect :- ")
                        Log.e("browse_page_err", "" +  "Socket NotConnect")
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Log.e("browse_page_err---", "" +  ex.message)

            Log.d(TAG, ex.toString())
            Log.e("browse_page_err---", "" +  ex.toString())
            Log.e("browse_page_err---", "" +  ex.message)

        }
    }

}