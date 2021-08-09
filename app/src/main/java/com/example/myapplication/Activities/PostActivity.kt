package com.example.myapplication.Activities

import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.LoginActivity
import com.example.myapplication.LoginFlag
import com.example.myapplication.R

class PostActivity : AppCompatActivity() {

    lateinit var comment: ImageView
    lateinit var vedio: ImageView
    lateinit var more: TextView
    lateinit var video_post_like: ImageView
    lateinit var layoutMore: TextView
    lateinit var sharePost:ImageView
    lateinit var savePost:ImageView
    lateinit var notifyPost:ImageView
    lateinit var follow: TextView
    private var loginFlag: Boolean = false




    var click :Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.black)
        }
        sharePost=findViewById(R.id.share_post)
        savePost = findViewById(R.id.saved_post)
        layoutMore = findViewById(R.id.text_more)
        video_post_like = findViewById(R.id.video_post_like)
        notifyPost = findViewById(R.id.notify_post)
        follow = findViewById(R.id.follow)
        vedio = findViewById(R.id.vedio)
        more = findViewById(R.id.more)
        loginFlag = LoginFlag.getLoginFlag()



        comment = findViewById(R.id.comment)
        comment.setOnClickListener {
            var i = Intent(this, PostActivity2()::class.java)
            startActivity(i)
        }

        video_post_like.setOnClickListener {
            if(click == false){
            video_post_like.setColorFilter(resources.getColor(R.color.red))
                click = true
            }else if(click == true){
                video_post_like.setColorFilter(resources.getColor(R.color.white))
                click=false
            }
        }


        savePost.setOnClickListener {
            if(click == false){
                Toast.makeText(this,"Post Saved", Toast.LENGTH_SHORT).show()
                click = true
            }else if(click == true){
                Toast.makeText(this,"Post Unsaved", Toast.LENGTH_SHORT).show()
                click=false
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

        notifyPost.setOnClickListener {
            Toast.makeText(this,"Notification", Toast.LENGTH_SHORT).show()

        }

        follow.setOnClickListener {
            if(click == false){
                follow.setText("Following")
                 click = true
            }else if(click == true){
                follow.setText("+ Follow")
                click=false
            }
        }


        more.setOnClickListener {

            layoutMore.setText("Lorem ipsum dolor sit amet,consectetuar adipscing elit, sed de eimuod\\ntempor incididunt giugugguuhut dalore magna aliqua. Quis ipsum sus-\\n lacus\"Lorem ipsum dolor sit amet,consectetuar adipscing elit, sed de eimuod\\ntempor incididunt ut dalore magna aliqua. Quis ipsum sus-\\n lacus Lorem ipsum dolor sit amet,consectetuar adipscing elit, sed de eimuod\\ntempor incididunt giugugguuhut dalore magna aliqua. Quis ipsum sus-\\n lacus\"Lorem ipsum dolor sit amet,consectetuar adipscing elit, sed de eimuod\\ntempor incididunt ut dalore magna aliqua. Quis ipsum sus-\\n lacus Lorem ipsum dolor sit amet,consectetuar adipscing elit, sed de eimuod\\ntempor incididunt giugugguuhut dalore magna aliqua. Quis ipsum sus-\\n lacus\"Lorem ipsum dolor sit amet,consectetuar adipscing elit, sed de eimuod\\ntempor incididunt ut dalore magna aliqua. Quis ipsum sus-\\n lacus Lorem ipsum dolor sit amet,consectetuar adipscing elit, sed de eimuod\\ntempor incididunt giugugguuhut dalore magna aliqua. Quis ipsum sus-\\n lacus\"Lorem ipsum dolor sit amet,consectetuar adipscing elit, sed de eimuod\\ntempor incididunt ut dalore magna aliqua. Quis ipsum sus-\\n lacus")
            more.setText("")
        }

//        var path = "android.resource://com.example.myapplication/" + R.raw.vedio
//        var u: Uri = Uri.parse(path.toString())
//        vedio.setVideoURI(u)
//        vedio.start()


    }
}