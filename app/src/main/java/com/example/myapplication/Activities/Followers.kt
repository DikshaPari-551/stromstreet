package com.example.myapplication.Activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adaptor.Follower_Adaptor
import com.example.myapplication.R
import com.example.myapplication.customclickListner.CustomClickListner2
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Response.Docss
import com.example.myapplication.entity.Response.LocalActivityResponse
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.SavedPrefManager
import okhttp3.ResponseBody

class Followers : AppCompatActivity(), ApiResponseListener<LocalActivityResponse>,CustomClickListner2 {
    lateinit var adaptor: Follower_Adaptor
    lateinit var recycler_view3: RecyclerView
    lateinit var back_arrow_chat: ImageView
    var otherUserId: String = ""

    //    var list: ArrayList<Docs> = arrayListOf()
    var mContext: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_followers)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.black)
        }
        followerApi()
        recycler_view3 = findViewById(R.id.recycler_view3)
        back_arrow_chat = findViewById(R.id.back_arrow_chat)

        back_arrow_chat.setOnClickListener {
            finish()
        }
    }

    private fun followerApi() {
        val Token = SavedPrefManager.getStringPreferences(this, SavedPrefManager.TOKEN).toString()
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
//            Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();
            var list = ArrayList<Docss>()
            list.addAll(response.result.docs)
//            list.addAll(response.result.docss)
            setAdapter(list)


        }
    }


    override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
        Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        Toast.makeText(this, "Server not responding", Toast.LENGTH_LONG).show()
    }


    fun setAdapter(list: ArrayList<Docss>) {
        adaptor = this?.let { Follower_Adaptor(it,this, list) }!!
        val layoutManager = LinearLayoutManager(this)
        recycler_view3.layoutManager = layoutManager
        recycler_view3.adapter = adaptor
    }

    override fun customClick(value: Docss, type: String)    {
//        value.userId
        otherUserId = value.followerId._id

        if (type.equals("profile")){
            var intent = Intent(applicationContext, UserProfile::class.java)
            SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager.otherUserId,otherUserId)

            startActivity(intent)
        }
//        if (type.equals("Follow")){
//            followerApi()
//        }
    }
}