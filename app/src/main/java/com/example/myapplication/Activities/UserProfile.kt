package com.example.myapplication.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Adaptor.ProfileAdaptor
import com.example.myapplication.Adaptor.UserProfilePostAdaptor
import com.example.myapplication.Exoplayer
import com.example.myapplication.R
import com.example.myapplication.customclickListner.CustomClickListner
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Response.Docs
import com.example.myapplication.entity.Response.Docss
import com.example.myapplication.entity.Response.LocalActivityResponse
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.SavedPrefManager
import com.example.myapplication.util.SavedPrefManager.Companion.postid
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.ResponseBody
import java.lang.Exception

class UserProfile : AppCompatActivity(), ApiResponseListener<Responce>, CustomClickListner {
    lateinit var tag: ImageView
    lateinit var back_tab1: LinearLayout
    lateinit var totalfollower: LinearLayout
    lateinit var totalfollowing: LinearLayout
    lateinit var followbtn: LinearLayout
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
    lateinit var postRecycler: RecyclerView
    var isFollow: Boolean = false
    lateinit var USERID: String
    lateinit var Userid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        profileApi()
        userpostlist()

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
        profileImage = findViewById(R.id.profileImage)
        postRecycler = findViewById(R.id.postRecycler)
        totalfollower = findViewById(R.id.totalfollower)
        totalfollowing = findViewById(R.id.totalfollowing)

//        totalfollower.setOnClickListener {
//            var intent = Intent(this, Followers::class.java)
//            startActivity(intent)
//        }
//        totalfollowing.setOnClickListener {
//            var intent = Intent(this, Following::class.java)
//            intent.putExtra("userId", Userid)
//
//
//            startActivity(intent)
//        }

        followbtn.setOnClickListener {
            followUnfollow()
        }
        back_arrow.setOnClickListener {
            finish()
        }
    }

    private fun userpostlist() {
        Userid =
            SavedPrefManager.getStringPreferences(this, SavedPrefManager.otherUserId).toString()
        if (androidextention.isOnline(mContext)) {
            androidextention.showProgressDialog(mContext)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(this, "OtherUserPostList", mContext)
            try {
                serviceManager.getOtherPostlist(callBack, Userid)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

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
        androidextention.showProgressDialog(this)
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
        androidextention.disMissProgressDialog(this)
        try {
            username.setText(response.result.profileResult.userName)
            followers.setText(response.result.followerCount.toString())
            following.setText(response.result.followingCount.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            var filedata = response.result.profileResult.profilePic
            Glide.with(mContext).load(filedata).into(profileImage);
        } catch (e: Exception) {
            e.printStackTrace()
        }


//        Toast.makeText(this, "success", Toast.LENGTH_LONG).show()
        isFollow = response.result.isFollow
        if (apiName.equals("Profile")) {
            if (isFollow == true) {
                followuser.setText("Unfollow")
            } else if (isFollow == false) {
                followuser.setText("Follow")
            }
        } else if (apiName.equals("FollowUnfollow")) {
            profileApi()
        } else if (apiName.equals("OtherUserPostList")) {
            var list = ArrayList<Docs>()
            list.addAll(response.result.docs)
            setAdapter(list)
        }
    }

    override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
        Toast.makeText(this, "No Post Found", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show()
    }

    fun setAdapter(list: ArrayList<Docs>) {
        adaptor = this?.let { UserProfilePostAdaptor(it, list, this) }!!
        val layoutManager = GridLayoutManager(this, 3)
        postRecycler?.layoutManager = layoutManager
        postRecycler?.adapter = adaptor
    }

    override fun customClick(value: Docs, type: String) {
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