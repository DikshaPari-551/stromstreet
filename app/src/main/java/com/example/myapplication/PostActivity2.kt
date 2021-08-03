package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PostActivity2 : AppCompatActivity() {
    private lateinit var post2recycler : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post2)
        post2recycler=findViewById(R.id.post2recyclerview)


        var adaptor = Post2Adapter()
        val layoutManager = LinearLayoutManager(this)
        post2recycler.layoutManager = layoutManager
        post2recycler.adapter = adaptor


    }
}