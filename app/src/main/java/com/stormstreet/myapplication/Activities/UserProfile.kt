package com.stormstreet.myapplication.Activities

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.stormstreet.myapplication.Adaptor.UserProfilePostAdaptor
import com.stormstreet.myapplication.Exoplayer
import com.stormstreet.myapplication.R
import com.stormstreet.myapplication.customclickListner.CustomClickListner3
import com.stormstreet.myapplication.entity.ApiCallBack
import com.stormstreet.myapplication.entity.Response.*
import com.stormstreet.myapplication.entity.Service_Base.ApiResponseListener
import com.stormstreet.myapplication.entity.Service_Base.ServiceManager
import com.stormstreet.myapplication.extension.androidextention
import com.stormstreet.myapplication.extension.androidextention.initLoader
import com.stormstreet.myapplication.util.SavedPrefManager
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.Exception

class UserProfile : AppCompatActivity(), ApiResponseListener<Responce>, CustomClickListner3 {
    lateinit var tag: ImageView
    lateinit var back_tab1: LinearLayout
    lateinit var totalfollower: LinearLayout
    lateinit var totalfollowing: LinearLayout
    lateinit var followbtn: LinearLayout
    lateinit var message: LinearLayout
    lateinit var backButton: ImageView
    lateinit var profileImage: CircleImageView
    var mContext: Context = this
    lateinit var adaptor: UserProfilePostAdaptor
    lateinit var color_grid: ImageView
    lateinit var back_arrow: ImageView
    lateinit var back_tab: LinearLayout
    lateinit var layout_tab1: RelativeLayout
    lateinit var layout_tab2: RelativeLayout
    lateinit var username: TextView
    lateinit var followers: TextView
    lateinit var following: TextView
    lateinit var followuser: TextView
    lateinit var userbio: TextView
    lateinit var noPost: TextView
    lateinit var postRecycler: RecyclerView
    var isFollow: Boolean = false
    lateinit var USERID: String
    lateinit var reciver_id: String
    lateinit var Userid: String
    lateinit var user_name: String
    lateinit var progress_bar: ProgressBar
    lateinit var nestedScrollView: NestedScrollView
    lateinit var moreUserBio: TextView
    lateinit var moreButton: TextView
    lateinit var lottie: LottieAnimationView
    var filedata:String=""

    var list = ArrayList<UserPostDocs>()
    var page: Int = 1
    var pages: Int = 0
    var limit: Int = 12
    companion object {
        var moreText : String = ""

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.black)
        }


//        color_grid=findViewById(R.id.color_grid)
////        back_tab=findViewById(R.id.back_tab)
//        layout_tab1=findViewById(R.id.layout_tab1)
////        layout_tab2=findViewById(R.id.layout_tab2)
        back_arrow = findViewById(R.id.back_arrow)
        username = findViewById(R.id.username)
        followers = findViewById(R.id.followers)
        following = findViewById(R.id.following)
        followuser = findViewById(R.id.followuser)
        followbtn = findViewById(R.id.followbtn)
        message = findViewById(R.id.message)
        profileImage = findViewById(R.id.profileImage)
        postRecycler = findViewById(R.id.postRecycler)
        totalfollower = findViewById(R.id.totalfollower)
        totalfollowing = findViewById(R.id.totalfollowing)
        noPost = findViewById(R.id.no_post)
        progress_bar = findViewById(R.id.progress_bar)
        nestedScrollView = findViewById(R.id.nestedScrollView)
        userbio = findViewById(R.id.userbio)
        moreUserBio = findViewById(R.id.more_userbio)
        moreButton = findViewById(R.id.more_button)
        lottie = findViewById(R.id.loader)
        profileApi()
        newPostList()
//        Userid = intent.getStringExtra("id")


        followbtn.setOnClickListener {
            followUnfollow()
        }
        back_arrow.setOnClickListener {
            finish()
        }
        message.setOnClickListener({
            startActivity(
                Intent(this, ChatActivity::class.java).putExtra("reciver_id", reciver_id)
                    .putExtra("username", user_name).putExtra("profileimage", filedata)
            )
        })

        nestedScrollView.setOnScrollChangeListener(object :
            NestedScrollView.OnScrollChangeListener {
            override fun onScrollChange(
                v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int
            ) {
                if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
//                    val lastVisibleItemPosition: Int = layoutManager.findLastVisibleItemPosition()

                    page++
                    progress_bar.visibility = View.VISIBLE
                    if (page > pages) {
                        progress_bar.visibility = View.GONE
                    } else {
                        newPostList()
//                        androidextention.disMissProgressDialog(mContext)
                        lottie.initLoader(false)

                    }
                }
            }
        })

        moreButton.setOnClickListener {

            moreUserBio.setText(moreText)
            moreButton.visibility = View.GONE
            userbio.visibility = View.GONE

        }
    }

    private fun newPostList() {
        Userid =
            SavedPrefManager.getStringPreferences(this, SavedPrefManager.otherUserId).toString()
        if (androidextention.isOnline(mContext)) {
//            androidextention.showProgressDialog(mContext)
            lottie.initLoader(true)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<UserPostResponse> =
                ApiCallBack<UserPostResponse>(object : ApiResponseListener<UserPostResponse> {
                    override fun onApiSuccess(response: UserPostResponse, apiName: String?) {
//                        androidextention.disMissProgressDialog(mContext)
                        lottie.initLoader(false)
                        pages = response.result.pages

                        list.addAll(response.result.docs)
                        setAdapter(list)
                    }

                    override fun onApiErrorBody(response: String?, apiName: String?) {
//                        androidextention.disMissProgressDialog(mContext)
                        lottie.initLoader(false)
                        noPost.visibility = View.VISIBLE
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
//                        androidextention.disMissProgressDialog(mContext)
                        lottie.initLoader(false)
                        Toast.makeText(mContext, "Invalid response.", Toast.LENGTH_LONG).show()
                    }

                }, "OtherPostResponse", mContext)
            try {
                serviceManager.getOtherPostlist(callBack, Userid, page.toString(), limit.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

//    private fun userpostlist() {
//        Userid =
//            SavedPrefManager.getStringPreferences(this, SavedPrefManager.otherUserId).toString()
//        if (androidextention.isOnline(mContext)) {
//            androidextention.showProgressDialog(mContext)
//            val serviceManager = ServiceManager(mContext)
//            val callBack: ApiCallBack<Responce> =
//                ApiCallBack<Responce>(this, "OtherUserPostList", mContext)
//            try {
//                serviceManager.getOtherPostlist(callBack, Userid)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }

    private fun followUnfollow() {
        val Userid =
            SavedPrefManager.getStringPreferences(this, SavedPrefManager.otherUserId).toString()
        if (androidextention.isOnline(this)) {
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(this, "FollowUnfollow", mContext)
            try {
                serviceManager.getFollowunfollow(callBack, Userid)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun profileApi() {
        val Userid =
            SavedPrefManager.getStringPreferences(this, SavedPrefManager.otherUserId).toString()
//        androidextention.showProgressDialog(this)
        lottie.initLoader(true)
        val serviceManager = ServiceManager(mContext)
        val callBack: ApiCallBack<Responce> =
            ApiCallBack<Responce>(this, "Profile", mContext)

        try {
            serviceManager.getOtherUserProfile(callBack, Userid)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onApiSuccess(response: Responce, apiName: String?) {
//        androidextention.disMissProgressDialog(this)
        lottie.initLoader(false)
        try {
            username.setText(response.result.profileResult.fullName)
            followers.setText(response.result.followerCount.toString())
            following.setText(response.result.followingCount.toString())
            user_name = response.result.profileResult.fullName
            reciver_id = response.result.profileResult._id
            userbio.setText(response.result.profileResult.bio)
            if (userbio.text.length > 185) {
                moreText = response.result.profileResult.bio
                moreButton.visibility = View.VISIBLE
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            filedata = response.result.profileResult.profilePic
            Glide.with(mContext).load(filedata).placeholder(R.drawable.circleprofile)
                .into(profileImage);
        } catch (e: Exception) {
            e.printStackTrace()
        }


//        Toast.makeText(this, "success", Toast.LENGTH_LONG).show()
        isFollow = response.result.isFollow
        if (apiName.equals("Profile")) {
            if (isFollow == true) {
                followuser.setText("Unfollow")
                message.visibility = View.VISIBLE

            } else if (isFollow == false) {
                followuser.setText("Follow")
                message.visibility = View.GONE
            }
        } else if (apiName.equals("FollowUnfollow")) {
            profileApi()
        }
//        else if (apiName.equals("OtherUserPostList")) {
//            var list = ArrayList<Docs>()
//            list.addAll(response.result.docs)
//            setAdapter(list)
//        }
    }

    override fun onApiErrorBody(response: String?, apiName: String?) {
//        androidextention.disMissProgressDialog(this)
        lottie.initLoader(false)
        Toast.makeText(this, "No Post Found", Toast.LENGTH_LONG).show()

    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
//        androidextention.disMissProgressDialog(this)
        lottie.initLoader(false)
        Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show()
    }

    fun setAdapter(list: ArrayList<UserPostDocs>) {
        adaptor = this?.let { UserProfilePostAdaptor(it, list, this) }!!
        val layoutManager = GridLayoutManager(this, 3)
        postRecycler?.layoutManager = layoutManager
        postRecycler?.adapter = adaptor
    }

    override fun customClick(value: UserPostDocs, type: String) {
        USERID = value._id

//        if (type.equals("profile")) {
//            var intent = Intent(mContext, PostActivity::class.java)
//            SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager._id, USERID)
////            intent.putExtra("userId", USERID)
//
//            startActivity(intent)
//        }

        if (type.equals("profile")) {
            if (value.mediaType.toLowerCase().equals("video")) {
                var intent = Intent(mContext, Exoplayer::class.java)
                SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager._id, USERID)
                startActivity(intent)

            } else {
                var intent = Intent(mContext, PostActivity::class.java)
                SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager._id, USERID)
                startActivity(intent)
            }

        }
    }

}