package com.example.myapplication.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.myapplication.LoginActivity
import com.example.myapplication.LoginFlag
import com.example.myapplication.R

class PostActivity3 : AppCompatActivity() {
    lateinit var comment: ImageView
    lateinit var likePost: ImageView
    lateinit var sharePost: ImageView
    lateinit var savePost: ImageView
    lateinit var notifyPost: ImageView
    private var loginFlag: Boolean = false
    var click: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post3)

        comment = findViewById(R.id.comment)
        likePost = findViewById(R.id.like_post)
        sharePost = findViewById(R.id.share_post)
        savePost = findViewById(R.id.saved_post)
        notifyPost = findViewById(R.id.notify_post)
        loginFlag = LoginFlag.getLoginFlag()


        comment.setOnClickListener {
            var i = Intent(
                this,
                PostActivity2()::class.java
            )
            startActivity(i)
        }

        likePost.setOnClickListener {
            if (click == false) {
                likePost.setColorFilter(resources.getColor(R.color.red))
                click = true
            } else if (click == true) {
                likePost.setColorFilter(resources.getColor(R.color.white))
                click = false
            }
        }


        sharePost.setOnClickListener {
            if (loginFlag == true) {
                val i = Intent(Intent.ACTION_SEND)
                i.setType("text/plain")
                var shareBody: String = "Share Body"
                var shareSubject: String = "Share Subject"
                i.putExtra(Intent.EXTRA_SUBJECT, shareSubject)
                i.putExtra(Intent.EXTRA_TEXT, shareBody)
                startActivity(Intent.createChooser(i, "Sharing using"))
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
            }

        }

        savePost.setOnClickListener {
            if (click == false) {
                Toast.makeText(this, "Post Saved", Toast.LENGTH_SHORT).show()
                click = true
            } else if (click == true) {
                Toast.makeText(this, "Post Unsaved", Toast.LENGTH_SHORT).show()
                click = false
            }
        }

        notifyPost.setOnClickListener {
            Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show()

        }


    }
}