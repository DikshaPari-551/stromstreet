package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import layout.modal_class


class ChatActivity : AppCompatActivity() {
    lateinit var add: EditText
    lateinit var sendImgIcon: ImageView
    var list: ArrayList<String> = arrayListOf()
    lateinit var rightArrowChat:ImageView
    lateinit var chat_layout:LinearLayout
lateinit var list_view:ListView
    lateinit var adapter: Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
       chat_layout=findViewById(R.id.chat_Activity)
//       rightArrowChat=findViewById(R.id.right_arrow_chat)
//        rightArrowChat.setOnClickListener{
//            getFragmentManager()?.beginTransaction()?.replace(R.id.linear_layout, ChatFragment())
//                ?.commit()
//        }
       list_view=findViewById(R.id.list_view)
        adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list)
        list_view.adapter= adapter as ArrayAdapter<String>
        sendImgIcon = findViewById(R.id.send_img_icon)
        add = findViewById(R.id.text_add)
        sendImgIcon.setOnClickListener {
            var data = add.text.toString()


            list.add(0, data)

            (adapter as ArrayAdapter<String>).notifyDataSetChanged()
             add.setText("")
//            list_view.setBackgroundResource(R.drawable.drawable_chat)
        }


    }
}