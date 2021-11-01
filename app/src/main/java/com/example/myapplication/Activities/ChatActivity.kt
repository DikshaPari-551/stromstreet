package com.example.myapplication.Activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adaptor.MessageAdaptor
import com.example.myapplication.Fragments.ChatFragment
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.socket.SocketInstance
import com.google.gson.JsonObject
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import java.net.URISyntaxException
import org.json.JSONException





class ChatActivity : AppCompatActivity() {
    lateinit var add: EditText
    lateinit var right_arrow_Chat:ImageView
    lateinit var sendImgIcon: ImageView
    lateinit var backButtton: ImageView
    lateinit var recyclerList: RecyclerView
    lateinit var chat_layout: LinearLayout
    lateinit var list_view: ListView
    var arr: ArrayList<HashMap<String, String>> = arrayListOf()
    lateinit var  socket: Socket
    lateinit var adapter: Adapter
    private var hasConnection = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val rmApplication: SocketInstance = applicationContext as SocketInstance
        socket= rmApplication.getMSocket()

        val options = IO.Options()
        options.reconnection = true //reconnection
        options.forceNew = true

        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.black)
        }
        chat_layout = findViewById(R.id.chat_Activity)
        backButtton = findViewById(R.id.right_arrow)
        recyclerList = findViewById(R.id.rcycler_list)
//
        sendImgIcon = findViewById(R.id.send_img_icon)
        add = findViewById(R.id.text_add)

        val layoutManager = LinearLayoutManager(this)
        var adaptor = MessageAdaptor(arr)
        recyclerList.layoutManager = layoutManager
        recyclerList.adapter = adaptor

        sendImgIcon.setOnClickListener {

            var text = add.text.toString()
            var hash: HashMap<String, String> = HashMap()
            hash.put("Data", text)
            arr.add(hash)
            adaptor.notifyDataSetChanged()
            add.setText("")
            Update(text)

//            list_view.setBackgroundResource(R.drawable.drawable_chat)
        }

        backButtton.setOnClickListener {
            var fragment : ChatFragment = ChatFragment()
//            supportFragmentManager.beginTransaction().replace(R.id.chat_Activity,HomeFragment()).commit()
//            val i = Intent(this, ChatFragment::class.java)
//            startActivity(i)

//        }
            var intent =  Intent(this,MainActivity::class.java)
            intent .putExtra("openF2",true)
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            startActivity(intent);
        }

        if(savedInstanceState != null){
            hasConnection = savedInstanceState.getBoolean("hasConnection");
        }

        if(hasConnection){

        }else {

//            socket!!.connect()
//            socket!!.on("oneToOneChat", onNewMessage)
//

            socket= rmApplication.getMSocket()
            socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
            socket.on(Socket.EVENT_CONNECT, onConnect);
            socket.connect();
            ONLINE()
        }
        try {
            val jsonObject = JSONObject("{\"userId\":\"616e08960a9d5515902f7ae2\"}")
            System.out.println("")
        } catch (err: JSONException) {
            Log.d("Error", err.toString())
        }

    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("hasConnection", hasConnection)
    }
    private fun ONLINE() {

      val jsonObject = JSONObject()
          .put("userId", "6177b1bc38ccc313ed3ff099")
        //socket?.on("new message", onNewMessage);
          socket!!.emit("onlineUser",jsonObject.toString());
      if(socket!!.connected()==true)
      {
         System.out.println("check"+toString())
      }
       // socket?.on("onlineUser", `onNewMessage`);

    }

    private fun Update(text: String) {
        val data = JSONObject()
        data.put("senderId", "616dccdab83a9818f8080f3c");
        data.put("receiverId", "616e08960a9d5515902f7ae2");
        data.put("message", text);
        socket!!.emit("oneToOneChat", data);
       // socket?.emit("new message", "message");
    }

    override fun onDestroy() {
        super.onDestroy()
        socket!!.disconnect()
        socket!!.off("oneToOneChat", onNewMessage);
    }
    object onNewMessage : Emitter.Listener {
        override fun call(vararg args: Any?)
        {

            Log.d("check",args.toString())
        }


    }

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
}



