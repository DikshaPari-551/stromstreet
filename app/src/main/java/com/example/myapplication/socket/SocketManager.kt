package com.example.myapplication.socket
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.myapplication.entity.Response.Chalist
import com.example.myapplication.entity.Response.Chatlist
import com.example.myapplication.entity.Response.Messages
import com.example.myapplication.entity.Response.Responce
import com.google.gson.Gson
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
     fun ONLINE_USER(s: String)
     {


         val jsonObject = JSONObject()
                     .put("userId", "616dccdab83a9818f8080f3c")
             socket!!.emit("onlineUser", jsonObject);


     }
     fun Update(text: String, USERID: String, reciver_id: String) {
        val data = JSONObject()
        data.put("senderId", USERID);
        data.put("receiverId", reciver_id);
        data.put("message", text);
        socket!!.emit("oneToOneChat", data);
        // socket?.emit("new message", "message");
    }
    fun VIEWcHAT(sender: String, reciver_id: String) {
        val data = JSONObject()
        data.put("senderId", sender);
        data.put("receiverId", reciver_id);
        socket!!.emit("viewChat", data);
        // socket?.emit("new message", "message");
    }
    fun CHAT_LIST(USERID: String)
    {
        val data = JSONObject()
        data.put("senderId", USERID);

        socket!!.emit("chatHistory", data);
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
        fun chatlist(listdat:ArrayList<Chalist>)
        fun viewchat(listdat:ArrayList<Messages>)
        fun oneToOneChat(listdat:Messages)

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
                    .on("onlineUser") { args ->
                Handler(Looper.getMainLooper()).post {
                    if (args != null && args.isNotEmpty())
                    {
                        System.out.println("onlineUser="+socketId.toString())
                    }
                }

            }
                    .on("oneToOneChat") { args ->
                        Handler(Looper.getMainLooper()).post {
                            if (args != null && args.isNotEmpty())
                            {System.out.println("oneToOneChat="+args[0].toString())
                                try {
                                    val gson = Gson()
                                    val fcmResponse: Responce = gson.fromJson(args[0].toString(), Responce::class.java)

                                    socketListener.oneToOneChat(fcmResponse.result.messages.get(0))
                                    //  ArraySingleton.getInstance().addToArray(fcmResponse.categoryResult)
                                } catch (e: java.lang.Exception) {
                                }
                            }
                        }

                    }
                    .on("chatHistory") { args ->
                        Handler(Looper.getMainLooper()).post {
                            if (args != null && args.isNotEmpty())
                            {
                                try {
                                    val gson = Gson()
                                   val fcmResponse: Chatlist = gson.fromJson(args[0].toString(), Chatlist::class.java)
                                    System.out.println("chatHistory="+fcmResponse.toString())
                                    socketListener.chatlist(fcmResponse.categoryResult)
                                  //  ArraySingleton.getInstance().addToArray(fcmResponse.categoryResult)
                                } catch (e: java.lang.Exception) {
                                }
                                System.out.println("chatHistory="+args[0])
                            }
                        }

                    }
                .on("viewChat") { args ->
                    Handler(Looper.getMainLooper()).post {
                        if (args != null && args.isNotEmpty())
                        {
                            try {
                                System.out.println("viewChat="+args[0])

                                val gson = Gson()
                                val fcmResponse: Responce = gson.fromJson(args[0].toString(), Responce::class.java)
                                System.out.println("viewChat="+fcmResponse.toString())
                               // socketListener.chatlist(fcmResponse.result.messages)
                                socketListener.viewchat(fcmResponse.result.messages)

                                //  ArraySingleton.getInstance().addToArray(fcmResponse.categoryResult)
                            } catch (e: java.lang.Exception) {
                            }
                            System.out.println("viewChat="+args[0])
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




