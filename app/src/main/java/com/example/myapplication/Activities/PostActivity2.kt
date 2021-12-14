package com.example.myapplication.Activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.myapplication.Adaptor.CommentImageSliderAdaptor
import com.example.myapplication.Adaptor.ImageSliderAdaptor
import com.example.myapplication.Adaptor.Post2Adapter
import com.example.myapplication.LoginActivity
import com.example.myapplication.LoginFlag
import com.example.myapplication.R
import com.example.myapplication.customclickListner.CustomCommentLikeListener
import com.example.myapplication.customclickListner.CustomReplyListener
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Response.CommentList
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.extension.androidextention.initLoader
import com.example.myapplication.util.AppConst
import com.example.myapplication.util.SavedPrefManager
import com.example.sleeponcue.extension.diasplay_toast
import okhttp3.ResponseBody

class PostActivity2 : AppCompatActivity(), ApiResponseListener<Responce>, CustomReplyListener,
    CustomCommentLikeListener {
    private lateinit var post2recycler: RecyclerView
    private var loginFlag: Boolean = false
    lateinit var follow1: TextView
    lateinit var add_comment: TextView
    lateinit var totalLike: TextView
    lateinit var username: TextView
    lateinit var report: TextView
    lateinit var eventType: TextView
    lateinit var commentcount: TextView
    lateinit var post_description: TextView
    lateinit var address: TextView
    lateinit var video_post_like: ImageView
    lateinit var comment: ImageView
    lateinit var share: LinearLayout
    lateinit var backButton: ImageView
    lateinit var profileImage: ImageView
    lateinit var userImageComment: ImageView
    lateinit var loadcomment: TextView
    lateinit var commenttext: EditText
    lateinit var commentRV: RecyclerView
    lateinit var commentvalue: String
    lateinit var commentLayout: RelativeLayout
    var shareImageLinks = ArrayList<String>()
    var prgress:Boolean=true
    private lateinit var adapter2: CommentImageSliderAdaptor

    //multiimage view

    lateinit var multiPostViewer: RelativeLayout
    lateinit var postViewer: RelativeLayout
    lateinit var multiPost: ViewPager2
    lateinit var imageCount: TextView
    lateinit var totalImageCount: TextView
    lateinit var lottie : LottieAnimationView

    var tImageCounter = 0
    var imageCounter = 1


    var mContext: Context = this
    lateinit var vedio: ImageView
    var USERID: String = ""
    var COMMENT_ID: String = ""
    var LikeUnlike: Boolean = false
    var postid: String = ""
    var mediatype: String = ""
    var shareLink: String = ""
    var isFollow: Boolean = false
    var click: Boolean = false
    var commentType = ""
    lateinit var adaptor: Post2Adapter
    lateinit var swipeRefresh: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post2)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.black)
        }

        swipeRefresh = findViewById(R.id.swipeRefresh)
        post2recycler = findViewById(R.id.post2recyclerview)
        loginFlag = LoginFlag.getLoginFlag()
        follow1 = findViewById(R.id.follow1)
        add_comment = findViewById(R.id.add_comment)
        video_post_like = findViewById(R.id.video_post_like)
        comment = findViewById(R.id.comment1)
        share = findViewById(R.id.share1)
        backButton = findViewById(R.id.back_arrow)
        loadcomment = findViewById(R.id.loadcomment)
        commenttext = findViewById(R.id.commenttext)
        report = findViewById(R.id.report)
        vedio = findViewById(R.id.vedio)
        totalLike = findViewById(R.id.totalLike)
        username = findViewById(R.id.username)
        post_description = findViewById(R.id.post_description)
        eventType = findViewById(R.id.eventType)
        commentcount = findViewById(R.id.commentcount)
        commentLayout = findViewById(R.id.commentLayout)
        profileImage = findViewById(R.id.profileImage)
        address = findViewById(R.id.address)
        userImageComment = findViewById(R.id.user_image_comment)
        lottie = findViewById(R.id.loader)

//multiimage view
        multiPostViewer = findViewById(R.id.multi_post_viewer)
        postViewer = findViewById(R.id.post_viewer)
        multiPost = findViewById(R.id.multi_post)
        imageCount = findViewById(R.id.image_count)
        totalImageCount = findViewById(R.id.total_image_count)
        commentType = "POST"
        if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                .equals("true")
        ) {
            commentLayout.visibility = View.VISIBLE
            add_comment.visibility = View.GONE
            add_comment.isEnabled = false
//                commentType = "POST"
        }
        getINent()
        postdetails()
        Commentlist()
        swipeRefresh.setOnRefreshListener {
            refresh()
            swipeRefresh.isRefreshing = false
        }

        follow1.setOnClickListener {
            followunfollow()
        }

        backButton.setOnClickListener {
            finish()
        }

        loadcomment.setOnClickListener {
            if (!commenttext.text.toString().equals("") && commenttext.text.toString() != null) {
                commentvalue = commenttext.text.toString()
                if (commentType.equals("POST")) {
                    postcomment(commentType, "")
//                commentLayout.visibility = View.VISIBLE
                } else {
                    postcomment(commentType, COMMENT_ID)
                    commentType = "POST"

                }
            } else {
                Toast.makeText(mContext, "Please add comment.", Toast.LENGTH_SHORT).show()
            }
        }

        report.setOnClickListener {
            val i = Intent(this, ReportPost::class.java)
            intent.putExtra("userId", USERID)

            startActivity(i)
        }

        video_post_like.setOnClickListener {
            likeunlike()
        }


        comment.setOnClickListener {
            if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {
                commentType = "POST"
                commenttext.requestFocus()
                val imgr: InputMethodManager =
                    mContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imgr.toggleSoftInput(
                    InputMethodManager.SHOW_FORCED,
                    InputMethodManager.HIDE_IMPLICIT_ONLY
                )
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
            }
        }

        share.setOnClickListener {
            if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {
                val i = Intent(Intent.ACTION_SEND)
                i.setType("text/plain")
                var shareBody: String = shareLink
                var shareSubject: String = "Strome street post link:"
                i.putExtra(Intent.EXTRA_SUBJECT, shareSubject)
                i.putExtra(Intent.EXTRA_TEXT, shareBody)
                startActivity(Intent.createChooser(i, "Sharing using"))
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
            }
        }

        val callback: ViewPager2.OnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(tImageCounter >= position) {
                    var count = position + 1
                    imageCount.text = count.toString()
                }
            }
        }
        multiPost.registerOnPageChangeCallback(callback);

//        add_comment.setOnClickListener {
//
//            if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
//                    .equals("true")
//            ) {
//                commentLayout.visibility = View.VISIBLE
////                commentType = "POST"
//            } else {
//                val i = Intent(this, LoginActivity::class.java)
//                startActivity(i)
//            }
//        }
    }

    private fun refresh() {
        postdetails()
        Commentlist()
    }

    private fun followunfollow() {
//        androidextention.showProgressDialog(this)
        lottie.initLoader(true)
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

    private fun likeunlike() {
//        androidextention.showProgressDialog(this)
        lottie.initLoader(true)
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

    private fun getINent() {
        try {
            USERID = SavedPrefManager.getStringPreferences(this, SavedPrefManager._id).toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun postdetails() {
        if (androidextention.isOnline(this)) {
            if(prgress)
            {
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
        }
    }

    private fun postcomment(commentType: String, commentId: String) {
        if (androidextention.isOnline(this)) {
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(this, "Comment", mContext)
            val apiRequest = Api_Request()
            apiRequest.commentType = commentType
            apiRequest.comment = commentvalue
            try {
                if (commentType.equals("POST")) {
                    serviceManager.commentOnPost(callBack, apiRequest, USERID, "")
                } else if (commentType.equals("COMMENT")) {
                    serviceManager.replyCommentOnPost(callBack, apiRequest, commentId)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            diasplay_toast("Check Your Internet Connection")
        }
    }

    private fun Commentlist() {
        if (androidextention.isOnline(this)) {
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(this, "Commentlist", mContext)
            try {
                serviceManager.getCommentlist(callBack, USERID)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onApiSuccess(response: Responce, apiName: String?) {
        lottie.initLoader(false)

        if (apiName.equals("PostDetails")) {
            try {
                username.setText(response.result.postResult.userId.userName.toString())
                post_description.setText(response.result.postResult.description)
                eventType.setText(response.result.postResult.categoryId.categoryName.toString())
                totalLike.setText(response.result.likeCount.toString())
                commentcount.setText(response.result.commentCount.toString())
                postid = response.result.postResult.userId._id.toString()
                address.setText(response.result.postResult.address.toString())
                mediatype = response.result.postResult.mediaType
                isFollow = response.result.isFollow
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (isFollow == true) {
                follow1.setText("Unfollow")
            } else if (isFollow == false) {
                follow1.setText("Follow")
            }

            LikeUnlike = response.result.isLike
            try {
                var filedata = response.result.postResult.userId.profilePic
                Glide.with(this).load(filedata).placeholder(R.drawable.circleprofile)
                    .into(profileImage);
                Glide.with(this).load(filedata).placeholder(R.drawable.circleprofile)
                    .into(userImageComment);
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (LikeUnlike == true) {
                video_post_like.setImageDrawable(resources.getDrawable(R.drawable.heartred))

            } else if (LikeUnlike == false) {
                video_post_like.setImageDrawable(resources.getDrawable(R.drawable.grey_heart))
            }
            if (mediatype.toLowerCase().equals("image")) {
                if (response.result.postResult.imageLinks.size > 1) {
//                    try {
                    postViewer.visibility = View.GONE
                    multiPostViewer.visibility = View.VISIBLE
                    imageCount.text = imageCounter.toString()
                    tImageCounter = response.result.postResult.imageLinks.size
                    var total = response.result.postResult.imageLinks.size.toString()
                    totalImageCount.text = "/$total"
                    val imageList: List<String> = response.result.postResult.imageLinks
                    shareImageLinks = imageList as ArrayList<String>
                    setImageAdaptor(imageList)
//                    } catch (e: java.lang.Exception) {
//                        e.printStackTrace()
//                    }
                } else {
                    try {
                        postViewer.visibility = View.VISIBLE
                        multiPostViewer.visibility = View.GONE
                        var filedata = response.result.postResult.imageLinks[0]
                        shareLink = filedata
                        Glide.with(this).load(filedata).into(vedio);
                    } catch (e: IndexOutOfBoundsException) {
                        e.printStackTrace()
                    }
                }

            } else if (mediatype.toLowerCase().equals("video")) {
                try {
                    postViewer.visibility = View.VISIBLE
                    multiPostViewer.visibility = View.GONE
                    var filedata = response.result.postResult.thumbNail
                    shareLink = response.result.postResult.videoLink

                    Glide.with(mContext).load(filedata).into(vedio);
                } catch (e: IndexOutOfBoundsException) {
                    e.printStackTrace()
                }
            }

        } else if (apiName.equals("LikeUnlike")) {
            if (LikeUnlike == true) {
                video_post_like.setImageDrawable(resources.getDrawable(R.drawable.heartred))

            } else if (LikeUnlike == false) {
                video_post_like.setImageDrawable(resources.getDrawable(R.drawable.grey_heart))
            }
            prgress=false
            postdetails()
        }
        if (apiName.equals("Comment")) {
            commenttext.setText("")
            commentLayout.visibility = View.GONE
            AppConst.hideKeyboard(this);
            Commentlist()
            commentLayout.visibility = View.VISIBLE
        }


        else if (apiName.equals("FollowUnfollow")) {
            prgress=false
            postdetails()
        }

//        isFollow = response.result.isFollow
//        if (apiName.equals("PostDetails")) {
//            if (isFollow == true) {
//                follow1.setText("Unfollow")
//            } else if (isFollow == false) {
//                follow1.setText("Follow")
//            }
//        }

        if (apiName.equals("Commentlist")) {
            var postCommentList = ArrayList<CommentList>()
            postCommentList.addAll(response.result.commentList)
            setAdapter(postCommentList)
        }
    }

    fun commentLikeApi(commentId: String, commentLike: ImageView) {
        if (androidextention.isOnline(mContext)) {
//            androidextention.showProgressDialog(mContext)
            lottie.initLoader(true)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(object : ApiResponseListener<Responce> {
                    override fun onApiSuccess(response: Responce, apiName: String?) {
                        Log.d("commentlikes", response.result.toString())
                        if (response.responseCode == "200") {
                            lottie.initLoader(false)
                            if (response.result.isLike == true) {
                                commentLike.setImageDrawable(resources.getDrawable(R.drawable.heartred))
//                                commentLike.setColorFilter(resources.getColor(R.color.red))
                            } else {
                                commentLike.setImageDrawable(resources.getDrawable(R.drawable.grey_heart))
                            }
                            Commentlist()
//                            androidextention.disMissProgressDialog(mContext)

                        } else {
                            Toast.makeText(mContext, response.responseMessage, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onApiErrorBody(response: String?, apiName: String?) {
//                        androidextention.disMissProgressDialog(mContext)
                        lottie.initLoader(false)

//                        Toast.makeText(mContext,"Server not responding.", Toast.LENGTH_SHORT).show()

                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
//                        androidextention.disMissProgressDialog(mContext)
                        lottie.initLoader(false)

                        Toast.makeText(mContext, "Server not responding.", Toast.LENGTH_SHORT)
                            .show()
                    }

                }, "CommentLikeApi", mContext)
            try {
                serviceManager.getCommentLikes(callBack, commentId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Toast.makeText(mContext, "Please check your internet connection!!", Toast.LENGTH_SHORT)
                .show()
        }

    }

    private fun setAdapter(list: ArrayList<CommentList>?) {
        adaptor = Post2Adapter(this, list!!, this, this, resources)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        post2recycler.layoutManager = layoutManager
        post2recycler.adapter = adaptor
    }

    private fun setImageAdaptor(imageList: List<String>) {
        adapter2 = CommentImageSliderAdaptor(imageList, this)
        multiPost.adapter = adapter2
    }

    override fun onApiErrorBody(response: String?, apiName: String?) {
//        androidextention.disMissProgressDialog(mContext)
        lottie.initLoader(false)

//        Toast.makeText(this, "Data not found.", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
//        androidextention.disMissProgressDialog(mContext)
        lottie.initLoader(false)
        Toast.makeText(this, "Server not responding", Toast.LENGTH_LONG).show()
    }

    override fun replyListener(
        commentRepliesRecyclerView: RecyclerView,
        position: Int,
        _id: String
    ) {
//        commentLayout.visibility = View.VISIBLE
        commenttext.requestFocus()
        val imgr: InputMethodManager =
            mContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        commentRepliesRecyclerView.visibility = View.VISIBLE
        commentType = "COMMENT"
        COMMENT_ID = _id
    }

    override fun commentLikeListener(commentId: String, commentLike: ImageView) {
        commentLikeApi(commentId, commentLike)
    }
}