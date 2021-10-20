package com.example.myapplication.Activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adaptor.Follower_Adaptor
import com.example.myapplication.R
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import okhttp3.ResponseBody
import java.lang.Exception

class Followers : AppCompatActivity(), ApiResponseListener<Responce> {
    lateinit var adaptor: Follower_Adaptor
    lateinit var recycler_view3: RecyclerView
    var mContext: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_followers)
        followingApi()

        recycler_view3=findViewById(R.id.recycler_view3)

        adaptor = this?.let { Follower_Adaptor(it) }!!
        val layoutManager = LinearLayoutManager(this)
        recycler_view3.layoutManager = layoutManager
        recycler_view3.adapter = adaptor
    }

    private fun followingApi() {
//        val Token = SavedPrefManager.getStringPreferences(this,SavedPrefManager.TOKEN).toString()
        val Token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYxNmZiMTBjZTYzMjY0MjA4ZDA4MWExNSIsImVtYWlsIjoiYWpheUBnbWFpbC5jb20iLCJ1c2VyVHlwZSI6IlVzZXIiLCJpYXQiOjE2MzQ3MzQzMzMsImV4cCI6MTYzNDgyMDczM30.wjAzfZhDXGY6JfbNWG6bnSPZoXefj4jSrR6Kllf6z-U"
        if (androidextention.isOnline(this)) {
            androidextention.showProgressDialog(this)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(this, "Following", mContext)
//            val apiRequest = Api_Request()

//            apiRequest.email = emailSignUp_et.getText().toString().trim()


            try {
                serviceManager.getFollower(callBack, Token)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onApiSuccess(response: Responce, apiName: String?) {
        if (response.responseCode == "200") {
            androidextention.disMissProgressDialog(this)
//            username.setText(response.result.userName)

            Toast.makeText(this,"Success", Toast.LENGTH_LONG).show()
        }

    }

    override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
        Toast.makeText(this,"Error", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        Toast.makeText(this,"fail", Toast.LENGTH_LONG).show()
    }
}