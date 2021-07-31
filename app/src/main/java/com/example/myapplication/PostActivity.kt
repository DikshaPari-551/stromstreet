package com.example.myapplication

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.VideoView

class PostActivity : AppCompatActivity() {
    lateinit var vedio: VideoView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        vedio=findViewById(R.id.vedio)


        var path="android.resource://com.example.myapplication/"+R.raw.vedio
        var u:Uri= Uri.parse(path.toString())
        vedio.setVideoURI(u)
        vedio.start()



    }
}