package com.example.myapplication

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
lateinit var list_view:ListView
    lateinit var adapter: Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


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