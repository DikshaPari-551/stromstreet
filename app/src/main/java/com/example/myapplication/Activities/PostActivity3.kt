package com.example.myapplication.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.myapplication.R

class PostActivity3 : AppCompatActivity() {
    lateinit var comment:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post3)

    comment=findViewById(R.id.comment)
        comment.setOnClickListener{
            var i = Intent(this,
                PostActivity2()::class.java)
            startActivity(i)
        }
    }
}