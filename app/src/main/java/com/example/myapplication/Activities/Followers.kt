package com.example.myapplication.Activities

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adaptor.Follower_Adaptor
import com.example.myapplication.R
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Response.Docss
import com.example.myapplication.entity.Response.LocalActivityResponse
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.SavedPrefManager
import okhttp3.ResponseBody

class Followers : AppCompatActivity(), ApiResponseListener<LocalActivityResponse> {
    lateinit var adaptor: Follower_Adaptor
    lateinit var recycler_view3: RecyclerView

    //    var list: ArrayList<Docs> = arrayListOf()
    var mContext: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_followers)
        followerApi()

        recycler_view3 = findViewById(R.id.recycler_view3)


    }

    private fun followerApi() {
        val Token = SavedPrefManager.getStringPreferences(this,SavedPrefManager.TOKEN).toString()
//        val Token =
//            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYxNmZiMTBjZTYzMjY0MjA4ZDA4MWExNSIsImVtYWlsIjoiYWpheUBnbWFpbC5jb20iLCJ1c2VyVHlwZSI6IlVzZXIiLCJpYXQiOjE2MzQ3MzQzMzMsImV4cCI6MTYzNDgyMDczM30.wjAzfZhDXGY6JfbNWG6bnSPZoXefj4jSrR6Kllf6z-U"
        if (androidextention.isOnline(this)) {
            androidextention.showProgressDialog(this)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<LocalActivityResponse> =
                ApiCallBack<LocalActivityResponse>(this, "Follower", mContext)
//            val apiRequest = Api_Request()

//            apiRequest.email = emailSignUp_et.getText().toString().trim()


            try {
                serviceManager.getFollower(callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onApiSuccess(response: LocalActivityResponse, apiName: String?) {
        if (response.responseCode == "200") {
            androidextention.disMissProgressDialog(this)
            Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();
            var list = ArrayList<Docss>()
            list.addAll(response.result.docs)
//            list.addAll(response.result.docss)
            setAdapter(list)


        }
    }


    override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        Toast.makeText(this, "fail", Toast.LENGTH_LONG).show()
    }


    fun setAdapter(list: ArrayList<Docss>) {
        adaptor = this?.let { Follower_Adaptor(it, list) }!!
        val layoutManager = LinearLayoutManager(this)
        recycler_view3.layoutManager = layoutManager
        recycler_view3.adapter = adaptor
    }
}