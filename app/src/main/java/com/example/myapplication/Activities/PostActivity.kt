package com.example.myapplication.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class PostActivity : AppCompatActivity() {

    lateinit var comment:ImageView
    lateinit var vedio: VideoView
    lateinit var more:TextView
    lateinit var layoutMore:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

       layoutMore=findViewById(R.id.text_more)
       comment=findViewById(R.id.comment)
        comment.setOnClickListener{
            var i = Intent(this, PostActivity2()::class.java)
            startActivity(i)
        }

        vedio=findViewById(R.id.vedio)
        more=findViewById(R.id.more)
        more.setOnClickListener{

            layoutMore.setText("Lorem ipsum dolor sit amet,consectetuar adipscing elit, sed de eimuod\\ntempor incididunt giugugguuhut dalore magna aliqua. Quis ipsum sus-\\n lacus\"Lorem ipsum dolor sit amet,consectetuar adipscing elit, sed de eimuod\\ntempor incididunt ut dalore magna aliqua. Quis ipsum sus-\\n lacus")
            more.setText("")
        }

        var path="android.resource://com.example.myapplication/"+ R.raw.vedio
        var u:Uri= Uri.parse(path.toString())
        vedio.setVideoURI(u)
        vedio.start()



    }
}