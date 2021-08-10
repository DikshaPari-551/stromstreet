package com.example.myapplication.Activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adaptor.MessageAdaptor
import com.example.myapplication.Fragments.ChatFragment
import com.example.myapplication.R


class ChatActivity : AppCompatActivity() {
    lateinit var add: EditText
    lateinit var sendImgIcon: ImageView
    var list: ArrayList<String> = arrayListOf()
    lateinit var backButton: ImageView
    lateinit var recyclerList: RecyclerView
    lateinit var chat_layout: LinearLayout
    lateinit var list_view: ListView
    var arr: ArrayList<HashMap<String, String>> = arrayListOf()

    lateinit var adapter: Adapter
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
        backButton = findViewById(R.id.right_arrow_chat)
        recyclerList = findViewById(R.id.rcycler_list)
//
        sendImgIcon = findViewById(R.id.send_img_icon)
        add = findViewById(R.id.text_add)

        val layoutManager = LinearLayoutManager(this)
        var adaptor = MessageAdaptor(arr)
        recyclerList.layoutManager = layoutManager
        recyclerList.adapter = adaptor

        sendImgIcon.setOnClickListener {
            var data = add.text.toString()
            var hash: HashMap<String, String> = HashMap()
            hash.put("Data", data)
            arr.add(hash)

//            list_view.setBackgroundResource(R.drawable.drawable_chat)
        }

        backButton.setOnClickListener {
//            supportFragmentManager.beginTransaction().replace(R.id.linear_layout,ChatFragment()).commit()
        }


    }
}