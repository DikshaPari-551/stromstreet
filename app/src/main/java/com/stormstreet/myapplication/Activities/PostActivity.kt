package com.stormstreet.myapplication.Activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.stormstreet.myapplication.Adaptor.ImageSliderAdaptor
import com.stormstreet.myapplication.BottomSheets.BottomSheetOptions
import com.stormstreet.myapplication.LoginActivity
import com.stormstreet.myapplication.LoginFlag
import com.stormstreet.myapplication.R
import com.stormstreet.myapplication.customclickListner.ClickListnerDelete
import com.stormstreet.myapplication.entity.ApiCallBack
import com.stormstreet.myapplication.entity.Response.Responce
import com.stormstreet.myapplication.entity.Service_Base.ApiResponseListener
import com.stormstreet.myapplication.entity.Service_Base.ServiceManager
import com.stormstreet.myapplication.extension.androidextention
import com.stormstreet.myapplication.extension.androidextention.initLoader
import com.stormstreet.myapplication.util.SavedPrefManager
import de.hdodenhof.circleimageview.CircleImageView
import me.relex.circleindicator.CircleIndicator3


class PostActivity : AppCompatActivity(), ApiResponseListener<Responce>, ClickListnerDelete {

    lateinit var comment: ImageView
    lateinit var vedio: ImageView
    lateinit var more: TextView
    lateinit var video_post_like: ImageView
    lateinit var sharePost: ImageView
    lateinit var savePost: ImageView
    lateinit var notifyPost: ImageView
    lateinit var follow: TextView
    lateinit var backPostButton: ImageView
    lateinit var three_dots: ImageView
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
    lateinit var lottie : LottieAnimationView
    var shareImageLinks = ArrayList<String>()
    var shareLink: String = ""
     var tag:String=""
    var VP_Position = 0
    var des = ""
    var position=""
    var prgress: Boolean = true
    private lateinit var adapter: ImageSliderAdaptor
    var USERID: String = ""
    var postid: String = ""
    var USERID_data: String = ""
    var LikeUnlike: Boolean = false
    var isSaved: Boolean = false
    var isFollow: Boolean = false

    private var loginFlag: Boolean = false

    var click: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        if (Build.VERSION.SDK_INT >= 21)
        {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.black)
        }

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
        three_dots = findViewById(R.id.three_dots)
        lottie = findViewById(R.id.loader)

        getINent()
        postdetails()
        limitTextMore.visibility = View.VISIBLE
        layoutMore.visibility = View.GONE
        USERID_data = SavedPrefManager.getStringPreferences(this, SavedPrefManager.USERID).toString()

        layoutMore.setMovementMethod(ScrollingMovementMethod())

//        if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
//                .equals("true")
//        ) {
//            video_post_like.setColorFilter(resources.getColor(R.color.white))
//            savePost.setImageDrawable(resources.getDrawable(R.drawable.unsaved_post))
//        }

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
            finish()
        }


        three_dots.setOnClickListener {
            if ((SavedPrefManager.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                            .equals("true"))
            ) {
                if (postid == USERID_data){
                    tag="delete"
                    follow.visibility = View.GONE
                }else {
                    tag="report"
                    // three_dots.visibility = View.GONE
                    follow.visibility = View.VISIBLE
                }
            }else{
                // three_dots.visibility = View.GONE
                follow.visibility = View.GONE
            }
        var bottomSheet = BottomSheetOptions(this,tag)
        bottomSheet.show(supportFragmentManager,"")
        }

        username.setOnClickListener {
            if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {
                val i = Intent(this, UserProfile::class.java)
                startActivity(i)
                finish()
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }
        profileimg.setOnClickListener {
            if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {
                val i = Intent(this, UserProfile::class.java)
                startActivity(i)
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
            }
        }


        comment.setOnClickListener {
            if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {
                var i = Intent(this, PostActivity2::class.java)
                startActivity(i)
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
            }
        }

        video_post_like.setOnClickListener {
            var count = 0
            if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {
                if(count == 0 && LikeUnlike == false){
                    count++
                    video_post_like.setColorFilter(resources.getColor(R.color.red))

                } else {
                    video_post_like.setColorFilter(resources.getColor(R.color.white))
                    count = 0

                }
                likeunlike()
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
            }
        }


        savePost.setOnClickListener {
            var countSaved = 0
            if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {
                if(countSaved == 0 && isSaved == false){
                    countSaved++
                    savePost.setImageDrawable(resources.getDrawable(R.drawable.saved_post))

                } else {
                    savePost.setImageDrawable(resources.getDrawable(R.drawable.unsaved_post))
                    countSaved = 0

                }
                saveunsave()
            } else {

                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
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
            USERID =
                SavedPrefManager.getStringPreferences(this, SavedPrefManager._id).toString()
                 position= intent.getStringExtra("postion").toString()


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun postdetails() {
//        val Token = SavedPrefManager.getStringPreferences(this,SavedPrefManager.TOKEN).toString()
        if (androidextention.isOnline(this)) {
            if (prgress) {
//                androidextention.showProgressDialog(this)
                lottie.initLoader(true)
            }

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
//        androidextention.showProgressDialog(mContext)
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
//        androidextention.showProgressDialog(mContext)
//        val Token = SavedPrefManager.getStringPreferences(this,SavedPrefManager.TOKEN).toString()
        lottie.initLoader(false)
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

    private fun deletePostapi() {
        if (androidextention.isOnline(this)) {
//            androidextention.showProgressDialog(this)
            lottie.initLoader(true)
                    val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(this, "DeletePost", mContext)
            try {
                serviceManager.deletepost(callBack, USERID)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onApiSuccess(response: Responce, apiName: String?) {

//        androidextention.disMissProgressDialog(this)
//        commentcount.setText(response.result.commentCount.toString())
        lottie.initLoader(false)
        LikeUnlike = response.result.isLike
        isSaved = response.result.isSave
        isFollow = response.result.isFollow

//        Toast.makeText(this, "success", Toast.LENGTH_LONG).show()
        if (apiName.equals("PostDetails")) {
            try {
                username.setText(response.result.postResult.userId.userName.toString())
                des = response.result.postResult.description
                if (des.length > 70) {
                    more.visibility = View.VISIBLE
                }
                limitTextMore.setText(response.result.postResult.description)
                eventType.setText(response.result.postResult.categoryId.categoryName.toString())
                totalLike.setText(response.result.likeCount.toString())
                commentcount.setText(response.result.commentCount.toString())
                postid = response.result.postResult.userId._id.toString()
                address.setText(response.result.postResult.address.toString())
                SavedPrefManager.saveStringPreferences(
                    mContext,
                    SavedPrefManager.otherUserId,
                    postid
                )

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
 if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                .equals("true")
        ) {
     if (LikeUnlike == true) {
         video_post_like.setColorFilter(resources.getColor(R.color.red))

     } else if (LikeUnlike == false) {
         video_post_like.setColorFilter(resources.getColor(R.color.white))
     }

     if (isSaved == true) {
         savePost.setImageDrawable(resources.getDrawable(R.drawable.saved_post))
     } else if (isSaved == false) {
         savePost.setImageDrawable(resources.getDrawable(R.drawable.unsaved_post))
     }
            if (isFollow == true) {
                follow.setText("Unfollow")
            } else if (isFollow == false) {
                follow.setText("Follow")
            }
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
                    try {
                        var filedata = response.result.postResult.imageLinks[0]
                        Glide.with(this).load(filedata).into(vedio);
                        shareLink = filedata
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }

            } catch (e: IndexOutOfBoundsException) {
                e.printStackTrace()
            }

        } else if (apiName.equals("LikeUnlike")) {
            prgress = false
            postdetails()

        } else if (apiName.equals("FollowUnfollow")) {
            prgress = false
            postdetails()
        } else if (apiName.equals("SaveUnsave")) {
            prgress = false
            postdetails()
        } else if (apiName.equals("DeletePost")) {
            val returnIntent = Intent()
            returnIntent.putExtra("result", position)
            setResult(RESULT_OK, returnIntent)
            finish()
        }


    }

    override fun onApiErrorBody(response: String?, apiName: String?) {
//        androidextention.disMissProgressDialog(this)
        lottie.initLoader(false)
        Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
//        androidextention.disMissProgressDialog(this)
        lottie.initLoader(false)
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

    override fun deletePost() {
        deletePostapi()
    }

    override fun reportpost() {

        val intent = Intent(this, ReportPost::class.java)
        startActivity(intent)
        finish()
    }
}
