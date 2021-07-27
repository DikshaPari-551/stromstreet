package com.example.myapplication

import android.os.Build
import android.os.Bundle
import android.security.identity.AccessControlProfile
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
   lateinit var menu:ImageView
    lateinit var bubble:ImageView
    lateinit var profile:ImageView
    lateinit var add:ImageView

    lateinit var chat:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        menu=findViewById(R.id.menu)
        menu.setOnClickListener{
            supportFragmentManager.beginTransaction().replace(R.id.linear_layout, HomeFragment()).commit()
            chat.setBackgroundColor(resources.getColor(R.color.white))

        }
        chat=findViewById(R.id.chat)
        chat.setOnClickListener{
            supportFragmentManager.beginTransaction().replace(R.id.linear_layout, secondFragment()).commit()

        }
        //add.setBackgroundColor(resources.getColor(R.color.orange))
        supportFragmentManager.beginTransaction().add(R.id.linear_layout, HomeFragment()).commit()




    }
}