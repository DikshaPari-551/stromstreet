package com.example.myapplication.Activities

import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.LoginActivity
import com.example.myapplication.LoginFlag
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.SavedPrefManager
import okhttp3.ResponseBody
import java.lang.Exception

class PostActivity : AppCompatActivity() , ApiResponseListener<Responce> {

    lateinit var comment: ImageView
    lateinit var vedio: ImageView
    lateinit var more: TextView
    lateinit var video_post_like: ImageView
    lateinit var sharePost:ImageView
    lateinit var savePost:ImageView
    lateinit var notifyPost:ImageView
    lateinit var follow: TextView
    lateinit var backPostButton : ImageView
    var mContext: Context = this
    lateinit var username:TextView
    lateinit var layoutMore: TextView
    lateinit var eventType: TextView
    lateinit var totalLike: TextView
    lateinit var commentcount: TextView
//    lateinit var totalshare: TextView

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
        postdetails()
        backPostButton = findViewById(R.id.back_arrow_post)
        sharePost=findViewById(R.id.share_post)
        savePost = findViewById(R.id.saved_post)
        video_post_like = findViewById(R.id.video_post_like)
        notifyPost = findViewById(R.id.notify_post)
        follow = findViewById(R.id.follow)
        vedio = findViewById(R.id.vedio)
        more = findViewById(R.id.more)
        loginFlag = LoginFlag.getLoginFlag()
        username = findViewById(R.id.username)
        layoutMore = findViewById(R.id.text_more)
        eventType = findViewById(R.id.eventType)
        totalLike = findViewById(R.id.totalLike)
        commentcount = findViewById(R.id.commentcount)
//        totalshare = findViewById(R.id.totalshare)


        backPostButton.setOnClickListener{
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
//            finish()
        }


        comment = findViewById(R.id.comment)
        comment.setOnClickListener {
            var i = Intent(this, PostActivity2()::class.java)
            startActivity(i)
        }

        video_post_like.setOnClickListener {
            likeunlike()
//            if(click == false){
//            video_post_like.setColorFilter(resources.getColor(R.color.red))
//                click = true
//            }else if(click == true){
//                video_post_like.setColorFilter(resources.getColor(R.color.white))
//                click=false
//            }
        }


        savePost.setOnClickListener {
            saveunsave()
            if(click == false){
//                Toast.makeText(this,"Post Saved", Toast.LENGTH_SHORT).show()
                click = true
            }else if(click == true){
//                Toast.makeText(this,"Post Unsaved", Toast.LENGTH_SHORT).show()
                click=false
            }
        }

        sharePost.setOnClickListener {
            if (  SavedPrefManager.getStringPreferences(this,  SavedPrefManager.KEY_IS_LOGIN).equals("true")) {
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
            followunfollow()
//            if(click == false){
//                follow.setText("Following")
//                 click = true
//            }else if(click == true){
//                follow.setText("+ Follow")
//                click=false
//            }
        }


//        more.setOnClickListener {
//
////            layoutMore.setText("Lorem ipsum dolor sit amet,consectetuar adipscing elit, sed de eimuod\\ntempor incididunt giugugguuhut dalore magna aliqua. Quis ipsum sus-\\n lacus\"Lorem ipsum dolor sit amet,consectetuar adipscing elit, sed de eimuod\\ntempor incididunt ut dalore magna aliqua. Quis ipsum sus-\\n lacus Lorem ipsum dolor sit amet,consectetuar adipscing elit, sed de eimuod\\ntempor incididunt giugugguuhut dalore magna aliqua. Quis ipsum sus-\\n lacus\"Lorem ipsum dolor sit amet,consectetuar adipscing elit, sed de eimuod\\ntempor incididunt ut dalore magna aliqua. Quis ipsum sus-\\n lacus Lorem ipsum dolor sit amet,consectetuar adipscing elit, sed de eimuod\\ntempor incididunt giugugguuhut dalore magna aliqua. Quis ipsum sus-\\n lacus\"Lorem ipsum dolor sit amet,consectetuar adipscing elit, sed de eimuod\\ntempor incididunt ut dalore magna aliqua. Quis ipsum sus-\\n lacus Lorem ipsum dolor sit amet,consectetuar adipscing elit, sed de eimuod\\ntempor incididunt giugugguuhut dalore magna aliqua. Quis ipsum sus-\\n lacus\"Lorem ipsum dolor sit amet,consectetuar adipscing elit, sed de eimuod\\ntempor incididunt ut dalore magna aliqua. Quis ipsum sus-\\n lacus")
//            more.setText("")
//        }

//        var path = "android.resource://com.example.myapplication/" + R.raw.vedio
//        var u: Uri = Uri.parse(path.toString())
//        vedio.setVideoURI(u)
//        vedio.start()


    }

    private fun postdetails() {
//        val Token = SavedPrefManager.getStringPreferences(this,SavedPrefManager.TOKEN).toString()
        val userId ="61711c7ec473b124b7369219"
        if (androidextention.isOnline(this)) {
            androidextention.showProgressDialog(this)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(this, "PostDetails", mContext)


            try {
                serviceManager.getPostDetails(callBack, userId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun saveunsave()  {
        val Token = SavedPrefManager.getStringPreferences(this,SavedPrefManager.TOKEN).toString()
        val userId ="61711c7ec473b124b7369219"
        if (androidextention.isOnline(this)) {
            androidextention.showProgressDialog(this)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(this, "SaveUnsave", mContext)
//            val apiRequest = Api_Request()
//            apiRequest.email = emailSignUp_et.getText().toString().trim()


            try {
                serviceManager.getSavepost(callBack, userId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun likeunlike() {
//        val Token = SavedPrefManager.getStringPreferences(this,SavedPrefManager.TOKEN).toString()
        val userId ="617119fbd830986dd4a453a5"
        if (androidextention.isOnline(this)) {
            androidextention.showProgressDialog(this)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(this, "LikeUnlike", mContext)



            try {
                serviceManager.getLikeunlike(callBack, userId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun followunfollow() {
//        val Token = SavedPrefManager.getStringPreferences(this,SavedPrefManager.TOKEN).toString()
        val userId ="617119fbd830986dd4a453a5"
        if (androidextention.isOnline(this)) {
            androidextention.showProgressDialog(this)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(this, "FollowUnfollow", mContext)
//            val apiRequest = Api_Request()
//            apiRequest.email = emailSignUp_et.getText().toString().trim()

            try {
                serviceManager.getFollowunfollow(callBack, userId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onApiSuccess(response: Responce, apiName: String?) {
        androidextention.disMissProgressDialog(this)

        username.setText(response.result.postResult.userId.userName)
        layoutMore.setText(response.result.postResult.description)
        eventType.setText(response.result.postResult.categoryId.categoryName.toString())
        totalLike.setText(response.result.likeCount.toString())
        commentcount.setText(response.result.commentCount.toString())
//        totalshare.setText(response.result.commentCount)

        var filedata = response.result.postResult.thumbNail
        Glide.with(this).load(filedata).into(vedio);


//        Toast.makeText(this, "success", Toast.LENGTH_LONG).show()
        if (apiName.equals("LikeUnlike")){
        video_post_like.setColorFilter(resources.getColor(R.color.red))

    }
    else if (apiName.equals("FollowUnfollow")){
            follow.setText("Following")
            click = true
        }else if(click == true){
            follow.setText("+ Follow")
            click=false
        }


    }

    override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        Toast.makeText(this, "fail", Toast.LENGTH_LONG).show()
    }
}