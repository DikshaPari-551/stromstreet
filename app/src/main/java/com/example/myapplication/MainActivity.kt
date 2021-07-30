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
        bubble=findViewById(R.id.bubble)
        profile=findViewById(R.id.profile)
        add=findViewById(R.id.add)
        menu.setOnClickListener{
            supportFragmentManager.beginTransaction().replace(R.id.linear_layout, HomeFragment()).commit()
            profile.setColorFilter(resources.getColor(R.color.grey))
            menu.setColorFilter(resources.getColor(R.color.white))
            bubble.setColorFilter(resources.getColor(R.color.grey))
            chat.setColorFilter(resources.getColor(R.color.grey))
        }
        chat=findViewById(R.id.chat)
        chat.setOnClickListener{
            supportFragmentManager.beginTransaction().replace(R.id.linear_layout, TrendingFragment()).commit()

            profile.setColorFilter(resources.getColor(R.color.grey))
            menu.setColorFilter(resources.getColor(R.color.grey))
            bubble.setColorFilter(resources.getColor(R.color.grey))
            chat.setColorFilter(resources.getColor(R.color.white))


        }
        add.setOnClickListener{
            var bottomsheet=bottomSheetDialog()
            bottomsheet.show(supportFragmentManager,"bottomsheet")
            profile.setColorFilter(resources.getColor(R.color.grey))
            menu.setColorFilter(resources.getColor(R.color.grey))
            bubble.setColorFilter(resources.getColor(R.color.grey))
            chat.setColorFilter(resources.getColor(R.color.grey))

        }
        bubble.setOnClickListener{
            profile.setColorFilter(resources.getColor(R.color.grey))
            menu.setColorFilter(resources.getColor(R.color.grey))
            bubble.setColorFilter(resources.getColor(R.color.white))
            chat.setColorFilter(resources.getColor(R.color.grey))
            supportFragmentManager.beginTransaction().replace(R.id.linear_layout, ChatFragment()).commit()

        }
        profile.setOnClickListener{
            profile.setColorFilter(resources.getColor(R.color.white))
            menu.setColorFilter(resources.getColor(R.color.grey))
            bubble.setColorFilter(resources.getColor(R.color.grey))
            chat.setColorFilter(resources.getColor(R.color.grey))
            supportFragmentManager.beginTransaction().replace(R.id.linear_layout, ProfileFragment()).commit()


        }
        //add.setBackgroundColor(resources.getColor(R.color.orange))
        supportFragmentManager.beginTransaction().add(R.id.linear_layout, HomeFragment()).commit()




    }
}