package com.example.myapplication.Activities

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Adaptor.Post2Adapter
import com.example.myapplication.LoginActivity
import com.example.myapplication.LoginFlag
import com.example.myapplication.R
import com.example.myapplication.customclickListner.CustomReplyListener
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Response.CommentList
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.AppConst
import com.example.myapplication.util.SavedPrefManager
import com.example.sleeponcue.extension.diasplay_toast
import okhttp3.ResponseBody
import java.lang.Exception

class PostActivity2 : AppCompatActivity(), ApiResponseListener<Responce>, CustomReplyListener{
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
    lateinit var share: ImageView
    lateinit var backButton: ImageView
    lateinit var profileImage: ImageView
    lateinit var loadcomment: TextView
    lateinit var commenttext: EditText
    lateinit var commentRV: RecyclerView
    lateinit var commentvalue: String
    lateinit var commentLayout: RelativeLayout

    var mContext: Context = this
    lateinit var vedio: ImageView
    var USERID: String = ""
    var COMMENT_ID: String = ""
    var LikeUnlike: Boolean = false
    var postid: String = ""
    var mediatype: String = ""
    var isFollow: Boolean = false
    var click: Boolean = false
    var commentType = ""
    lateinit var adaptor : Post2Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post2)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.black)
        }

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
//        COMMENT_ID = SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.COMMENT_ID)!!

        getINent()
        postdetails()
        Commentlist()


        follow1.setOnClickListener {
            followunfollow()
        }

        backButton.setOnClickListener {
            finish()
        }

        loadcomment.setOnClickListener {
            commentvalue = commenttext.text.toString().trim()
            if (commentType.equals("POST")) {
                postcomment(commentType, "")
            } else {
                postcomment(commentType, COMMENT_ID)
//                RepliesCommentList(commentRV)

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
                Toast.makeText(this, "Please comment", Toast.LENGTH_LONG).show()
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
                var shareBody: String = "Share Body"
                var shareSubject: String = "Share Subject"
                i.putExtra(Intent.EXTRA_SUBJECT, shareSubject)
                i.putExtra(Intent.EXTRA_TEXT, shareBody)
                startActivity(Intent.createChooser(i, "Sharing using"))
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
            }
        }

        add_comment.setOnClickListener {
            commentLayout.visibility = View.VISIBLE
            commentType = "POST"
            if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
            }
        }
    }

    private fun followunfollow() {
//        val Userid = SavedPrefManager.getStringPreferences(this, SavedPrefManager.otherUserId).toString()
//        val Userid = SavedPrefManager.getStringPreferences(this, SavedPrefManager._id).toString()
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
//        val Token = SavedPrefManager.getStringPreferences(this,SavedPrefManager.TOKEN).toString()
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
            androidextention.showProgressDialog(this)
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
            androidextention.showProgressDialog(this)
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
//            androidextention.showProgressDialog(this)
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

//    private fun RepliesCommentList(commentRepliesRecyclerView: RecyclerView) {
//        if (androidextention.isOnline(this)) {
//            val serviceManager = ServiceManager(mContext)
//            val callBack: ApiCallBack<Responce> =
//                ApiCallBack<Responce>(object : ApiResponseListener<Responce> {
//                    override fun onApiSuccess(response: Responce, apiName: String?) {
//                        var replierCommentList = ArrayList<Replies>()
//                        replierCommentList.addAll(response.result.replies)
////                        setAdapter(replierCommentList,"COMMENT")
//                        var adaptor = RepliesCommentAdaptor(mContext, replierCommentList)
//                        val layoutManager = LinearLayoutManager(mContext)
//                        commentRepliesRecyclerView.layoutManager = layoutManager
//                        commentRepliesRecyclerView.adapter = adaptor
//                    }
//
//                    override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
//                        Toast.makeText(mContext, "Data not found.", Toast.LENGTH_LONG).show()
//                    }
//
//                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
//                        Toast.makeText(mContext, "Server not responding", Toast.LENGTH_LONG).show()
//                    }
//
//                }, "RepliesCommentlist", mContext)
//            try {
//                serviceManager.getRepliesCommentlist(callBack, COMMENT_ID)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }

    override fun onApiSuccess(response: Responce, apiName: String?) {
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
            } catch (e: Exception) {
                e.printStackTrace()
            }


            LikeUnlike = response.result.isLike
            try {
                var filedata = response.result.postResult.userId.profilePic
                Glide.with(this).load(filedata).into(profileImage);
            } catch (e: Exception) {
                e.printStackTrace()
            }
//        totalshare.setText(response.result.commentCount)
            if (LikeUnlike == true) {
                video_post_like.setColorFilter(resources.getColor(R.color.red))

            } else if (LikeUnlike == false) {
                video_post_like.setColorFilter(resources.getColor(R.color.white))
            }
            if (mediatype.toLowerCase().equals("image")) {
                try {
                    var filedata = response.result.postResult.imageLinks[0]
                    Glide.with(this).load(filedata).into(vedio);
                } catch (e: IndexOutOfBoundsException) {
                    e.printStackTrace()
                }
            } else if (mediatype.toLowerCase().equals("video")) {
                try {
                    var filedata = response.result.postResult.thumbNail
                    Glide.with(this).load(filedata).into(vedio);
                } catch (e: IndexOutOfBoundsException) {
                    e.printStackTrace()
                }
            }

        } else if (apiName.equals("LikeUnlike")) {
            postdetails()
        }
        if (apiName.equals("Comment")) {
            commenttext.setText("")
            commentLayout.visibility = View.GONE
            AppConst.hideKeyboard(this);
            Commentlist()
        }
        isFollow = response.result.isFollow
        if (apiName.equals("PostDetails")) {
            if (isFollow == true) {
                follow1.setText("Unfollow")
            } else if (isFollow == false) {
                follow1.setText("Follow")
            }
        }
//        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()

        if (apiName.equals("Commentlist")) {
            var postCommentList = ArrayList<CommentList>()
            postCommentList.addAll(response.result.commentList)
            setAdapter(postCommentList)
//            if(commentType.equals("POST")) {
//                System.out.println("POST")
//                adaptor.notifyDataSetChanged()
//            }
//
//            else  if(commentType.equals("COMMENT")) {
//                System.out.println("COMMENT")
//
//                adaptor.notifyDataSetChanged()
//            }
//            else
//            {
//
//                setAdapter(postCommentList)
//            }

//            if (response.result.commentList.get(0).equals("COOMENT")){
//                RepliesCommentList(commentRV)
//            }

        }
    }

    private fun setAdapter(list: ArrayList<CommentList>?) {
//        if(commentType.equals("POST")) {
            adaptor = Post2Adapter(this, list!!,this)
            val layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, true)
            post2recycler.layoutManager = layoutManager
            post2recycler.adapter = adaptor
//        } else {
//            var adaptor = Post2Adapter(this, list!!, this)
//            val layoutManager = LinearLayoutManager(this)
//            post2recycler.layoutManager = layoutManager
//            post2recycler.adapter = adaptor
//        }
    }


    override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
//        Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        Toast.makeText(this, "Server not responding", Toast.LENGTH_LONG).show()
    }

    override fun replyListener(commentRepliesRecyclerView: RecyclerView, position: Int, _id: String) {
        commentLayout.visibility = View.VISIBLE
        commentRepliesRecyclerView.visibility = View.VISIBLE
        commentType = "COMMENT"
        COMMENT_ID = _id

//        RepliesCommentList(commentRepliesRecyclerView)
//
////        commentvalue = commenttext.text.toString().trim()
////        postcomment("COMMENT",commentId)
    }
}