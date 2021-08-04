package com.example.myapplication.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adaptor.Post2Adapter
import com.example.myapplication.Fragments.ProfileFragment
import com.example.myapplication.LoginActivity
import com.example.myapplication.LoginFlag
import com.example.myapplication.R

class PostActivity2 : AppCompatActivity() {
    private lateinit var post2recycler : RecyclerView
    private var loginFlag : Boolean = false
    lateinit var follow1 : TextView
    lateinit var like : ImageView
    lateinit var comment : ImageView
    lateinit var share : ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post2)
        post2recycler=findViewById(R.id.post2recyclerview)
        loginFlag = LoginFlag.getLoginFlag()

        follow1 = findViewById(R.id.follow1)
        like = findViewById(R.id.like)
        comment = findViewById(R.id.comment1)
        share = findViewById(R.id.share1)

        var adaptor = Post2Adapter()
        val layoutManager = LinearLayoutManager(this)
        post2recycler.layoutManager = layoutManager
        post2recycler.adapter = adaptor


        follow1.setOnClickListener{
            if (loginFlag == true) {
                follow1.setText("Following")
                follow1.setTextColor(resources.getColor(R.color.red))
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
            }

        }

        like.setOnClickListener{
            if (loginFlag == true) {
                like.setColorFilter(resources.getColor(R.color.red))
            } else {
               like.setColorFilter(resources.getColor(R.color.grey))
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
            }

        }

        comment.setOnClickListener{
            if (loginFlag == true) {
               Toast.makeText(this,"Please comment",Toast.LENGTH_LONG).show()
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
            }

        }

        share.setOnClickListener{
            if (loginFlag == true) {
                share.setColorFilter(resources.getColor(R.color.red))
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
            }

        }


    }
}