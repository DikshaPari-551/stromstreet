package com.example.myapplication.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.myapplication.R
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.SavedPrefManager
import okhttp3.ResponseBody
import java.lang.Exception

class UserProfile : AppCompatActivity(), ApiResponseListener<Responce> {
    lateinit var tag: ImageView
    lateinit var back_tab1: LinearLayout
    lateinit var totalfollower: LinearLayout
    lateinit var totalfollowing: LinearLayout
    lateinit var backButton: ImageView
    lateinit var mContext : Context

    lateinit var color_grid: ImageView
    lateinit var back_tab: LinearLayout
    lateinit var layout_tab1: RelativeLayout
    lateinit var layout_tab2: RelativeLayout
    lateinit var username: TextView
    lateinit var followers: TextView
    lateinit var following: TextView
    lateinit var followuser: TextView
    lateinit var unfollowuser: TextView
//    lateinit var buttonProfileDetail: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
//        color_grid=findViewById(R.id.color_grid)
////        back_tab=findViewById(R.id.back_tab)
//        layout_tab1=findViewById(R.id.layout_tab1)
////        layout_tab2=findViewById(R.id.layout_tab2)
//        username = findViewById(R.id.username)
//        followers = findViewById(R.id.followers)
//        following = findViewById(R.id.following)
//        profileApi()


//        color_grid.setImageDrawable(resources.getDrawable(R.drawable.color_grid))
//        back_tab.setBackgroundResource(R.drawable.rectangle_tab)
//        tag.setImageDrawable(resources.getDrawable(R.drawable.tag))
//        back_tab1.setBackgroundColor(resources.getColor(R.color.edit_color))
//
//        totalfollower.setOnClickListener{
//            var intent = Intent(this, Followers::class.java)
//            startActivity(intent)
//        }
//
//        following.setOnClickListener{
//            var intent = Intent(this, Following::class.java)
//            startActivity(intent)
//        }
    }

    private fun profileApi() {
        val Token = SavedPrefManager.getStringPreferences(this, SavedPrefManager.TOKEN).toString()
//        val Token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYxNmZiMTBjZTYzMjY0MjA4ZDA4MWExNSIsImVtYWlsIjoiYWpheUBnbWFpbC5jb20iLCJ1c2VyVHlwZSI6IlVzZXIiLCJpYXQiOjE2MzQ3MTAyMTEsImV4cCI6MTYzNDc5NjYxMX0.NirNVhYOeAlgfalbbSJ4x2KBUK8L62FKXRPENA6CdJY"
        androidextention.showProgressDialog(this)
        val serviceManager = ServiceManager(mContext)
        val callBack: ApiCallBack<Responce> =
            ApiCallBack<Responce>(this, "Profile", mContext)

        try {
            serviceManager.getProfile(callBack,Token)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onApiSuccess(response: Responce, apiName: String?) {
        androidextention.disMissProgressDialog(this)
        username.setText(response.result.userResult.userName)
        followers.setText(response.result.followerCount.toString())
        following.setText(response.result.followingCount.toString())

        Toast.makeText(this, "success", Toast.LENGTH_LONG).show()

    }

    override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
        Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        Toast.makeText(this, "fail", Toast.LENGTH_LONG).show()
    }


}