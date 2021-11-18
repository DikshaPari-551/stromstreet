package com.example.myapplication.Activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.bumptech.glide.Glide
import com.example.myapplication.Adaptor.ImageSliderAdaptor
import com.example.myapplication.LoginActivity
import com.example.myapplication.LoginFlag
import com.example.myapplication.R
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.SavedPrefManager
import de.hdodenhof.circleimageview.CircleImageView
import me.relex.circleindicator.CircleIndicator3
import okhttp3.ResponseBody

class PostActivity : AppCompatActivity(), ApiResponseListener<Responce> {

    lateinit var comment: ImageView
    lateinit var vedio: ImageView
    lateinit var more: TextView
    lateinit var video_post_like: ImageView
    lateinit var sharePost: ImageView
    lateinit var savePost: ImageView
    lateinit var notifyPost: ImageView
    lateinit var follow: TextView
    lateinit var backPostButton: ImageView
    var mContext: Context = this
    lateinit var username: TextView
    lateinit var layoutMore: TextView
    lateinit var limitTextMore: TextView
    lateinit var eventType: TextView
    lateinit var totalLike: TextView
    lateinit var commentcount: TextView
    lateinit var address: TextView
    lateinit var profileimg: CircleImageView
    lateinit var viewPager2: ViewPager2
    lateinit var indicator3: CircleIndicator3
    lateinit var internetConnection: LinearLayout
    var shareImageLinks = ArrayList<String>()
    var shareLink: String = ""
    var VP_Position = 0
    var des = ""

    private lateinit var adapter: ImageSliderAdaptor


    var USERID: String = ""
    var postid: String = ""
    var LikeUnlike: Boolean = false
    var isFollow: Boolean = false

//    lateinit var totalshare: TextView


    private var loginFlag: Boolean = false

    var click: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.black)
        }
        getINent()
        postdetails()
        backPostButton = findViewById(R.id.back_arrow_post)
        profileimg = findViewById(R.id.profileimg)
        sharePost = findViewById(R.id.share_post)
        savePost = findViewById(R.id.saved_post)
        video_post_like = findViewById(R.id.video_post_like)
        notifyPost = findViewById(R.id.notify_post)
        follow = findViewById(R.id.follow)
        vedio = findViewById(R.id.vedio)
        more = findViewById(R.id.more)
        loginFlag = LoginFlag.getLoginFlag()
        username = findViewById(R.id.username)
        layoutMore = findViewById(R.id.text_more)
        limitTextMore = findViewById(R.id.limit_text_more)
        eventType = findViewById(R.id.eventType)
        totalLike = findViewById(R.id.totalLike)
        commentcount = findViewById(R.id.commentcount)
        address = findViewById(R.id.address)
        viewPager2 = findViewById(R.id.multi_image)
        indicator3 = findViewById(R.id.indicator)
        internetConnection = findViewById(R.id.no_wifi)
        comment = findViewById(R.id.comment)
//        comment = findViewById(R.id.comment)
        limitTextMore.visibility = View.VISIBLE
        layoutMore.visibility = View.GONE


        getINent()
        postdetails()
        val callback: OnPageChangeCallback = object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sharePostLink(position)
            }
        }
        viewPager2.registerOnPageChangeCallback(callback);


        backPostButton.setOnClickListener {
//            val i = Intent(this, MainActivity::class.java)
//            startActivity(i)
            finish()

        }


        comment.setOnClickListener {
            if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {
                var i = Intent(this, PostActivity2()::class.java)
                startActivity(i)
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }

        video_post_like.setOnClickListener {
            if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {
                likeunlike()
//            if(click == false){
//            video_post_like.setColorFilter(resources.getColor(R.color.red))
//                click = true
//            }else if(click == true){
//                video_post_like.setColorFilter(resources.getColor(R.color.white))
//                click=false
//            }
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }


        savePost.setOnClickListener {
            if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {
                saveunsave()
                if (click == false) {
                    Toast.makeText(this, "Post Saved", Toast.LENGTH_SHORT).show()
                    click = true
                } else if (click == true) {
                    Toast.makeText(this, "Post Unsaved", Toast.LENGTH_SHORT).show()
                    click = false
                }
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }

        sharePost.setOnClickListener {
            if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {
                val i = Intent(Intent.ACTION_SEND)
                i.setType("text/plain")
                var shareBody: String = shareLink
                var shareSubject: String = "Share link:-"
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
            if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {
                Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show()
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }

        follow.setOnClickListener {
            if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {
                followunfollow()
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
            }

        }


        more.setOnClickListener {
            limitTextMore.visibility = View.GONE
            layoutMore.visibility = View.VISIBLE
            layoutMore.setText(des)
            more.setText("")
        }


    }

    private fun sharePostLink(position: Int) {
        shareLink = shareImageLinks.get(position)
    }

    private fun getINent() {
        try {
            USERID = SavedPrefManager.getStringPreferences(this, SavedPrefManager._id).toString()
//            USERID  = intent.getStringExtra("userId").toString()


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun postdetails() {
//        val Token = SavedPrefManager.getStringPreferences(this,SavedPrefManager.TOKEN).toString()
        if (androidextention.isOnline(this)) {
            androidextention.showProgressDialog(this)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(this, "PostDetails", mContext)
            try {
                serviceManager.getPostDetails(callBack, USERID)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            internetConnection.visibility = View.VISIBLE
            comment.isEnabled = false
            video_post_like.isEnabled = false
            savePost.isEnabled = false
            sharePost.isEnabled = false
            notifyPost.isEnabled = false
            follow.isEnabled = false
        }
    }

    private fun saveunsave() {
        val Token = SavedPrefManager.getStringPreferences(this, SavedPrefManager.TOKEN).toString()
        if (androidextention.isOnline(this)) {
//            androidextention.showProgressDialog(this)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(this, "SaveUnsave", mContext)
//            val apiRequest = Api_Request()
//            apiRequest.email = emailSignUp_et.getText().toString().trim()
            try {
                serviceManager.getSavepost(callBack, USERID)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun likeunlike() {
        androidextention.showProgressDialog(mContext)
        if (androidextention.isOnline(this)) {
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(this, "LikeUnlike", mContext)
            try {
                serviceManager.getLikeunlike(callBack, USERID)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun followunfollow() {
        androidextention.showProgressDialog(mContext)
//        val Token = SavedPrefManager.getStringPreferences(this,SavedPrefManager.TOKEN).toString()
        if (androidextention.isOnline(this)) {
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(this, "FollowUnfollow", mContext)
            try {
                serviceManager.getFollowunfollow(callBack, postid)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onApiSuccess(response: Responce, apiName: String?) {
        androidextention.disMissProgressDialog(this)
        commentcount.setText(response.result.commentCount.toString())
        LikeUnlike = response.result.isLike
        isFollow = response.result.isFollow
//        Toast.makeText(this, "success", Toast.LENGTH_LONG).show()
        if (apiName.equals("PostDetails")) {
            try {
                username.setText(response.result.postResult.userId.userName.toString())
                des = response.result.postResult.description
                if(des.length > 70) {
                    more.visibility = View.VISIBLE
                }
                limitTextMore.setText(response.result.postResult.description)
                eventType.setText(response.result.postResult.categoryId.categoryName.toString())
                totalLike.setText(response.result.likeCount.toString())
                commentcount.setText(response.result.commentCount.toString())
                postid = response.result.postResult.userId._id.toString()
                address.setText(response.result.postResult.address.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }

            try {
                var profile = response.result.postResult.userId.profilePic.toString()
                Glide.with(this).load(profile).placeholder(R.drawable.circleprofile)
                    .into(profileimg);
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (LikeUnlike == true) {
                video_post_like.setColorFilter(resources.getColor(R.color.red))

            } else if (LikeUnlike == false) {
                video_post_like.setColorFilter(resources.getColor(R.color.white))
            }

            if (isFollow == true) {
                follow.setText("Unfollow")
            } else if (isFollow == false) {
                follow.setText("Follow")
            }


            try {
                if (response.result.postResult.imageLinks.size > 1) {
                    vedio.visibility = View.GONE
                    viewPager2.visibility = View.VISIBLE
                    indicator3.visibility = View.VISIBLE
                    val imageList: List<String> = response.result.postResult.imageLinks
                    shareImageLinks = imageList as ArrayList<String>
                    setImageAdaptor(imageList)
                } else {
                    var filedata = response.result.postResult.imageLinks[0]
                    Glide.with(this).load(filedata).into(vedio);
                    shareLink = filedata
                }

            } catch (e: IndexOutOfBoundsException) {
                e.printStackTrace()
            }

        } else if (apiName.equals("LikeUnlike")) {
            postdetails()
        } else if (apiName.equals("FollowUnfollow")) {
            postdetails()
        }
    }

    override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
        androidextention.disMissProgressDialog(this)
        Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        androidextention.disMissProgressDialog(this)
        Toast.makeText(this, "Server not responding", Toast.LENGTH_LONG).show()
    }

    fun setImageAdaptor(imageList: List<String>) {
        adapter = ImageSliderAdaptor(imageList, this)
        viewPager2.adapter = adapter
        indicator3.setViewPager(viewPager2)
    }

    override fun onResume() {
        super.onResume()
        postdetails()
    }
}
