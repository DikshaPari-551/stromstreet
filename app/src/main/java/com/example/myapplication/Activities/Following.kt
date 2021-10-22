package com.example.myapplication.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adaptor.Following_Adaptor
import com.example.myapplication.R
import com.example.myapplication.customclickListner.CustomClickListner
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Response.Docs
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.SavedPrefManager
import okhttp3.ResponseBody
import java.lang.Exception

class Following : AppCompatActivity() , ApiResponseListener<Responce>,CustomClickListner {
    lateinit var adaptor: Following_Adaptor
    lateinit var recycler_view3: RecyclerView
    var mContext: Context = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_following)
        recycler_view3=findViewById(R.id.recycler_view3)
        followingApi()

    }

    private fun followingApi() {
        val Token = SavedPrefManager.getStringPreferences(this,SavedPrefManager.TOKEN).toString()
        val userId ="617119fbd830986dd4a453a5"
        if (androidextention.isOnline(this)) {
            androidextention.showProgressDialog(this)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(this, "Following", mContext)
            val apiRequest = Api_Request()

//            apiRequest.email = emailSignUp_et.getText().toString().trim()


            try {
                serviceManager.getFollowing(callBack, userId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onApiSuccess(response: Responce, apiName: String?) {
        if (response.responseCode == "200") {
            androidextention.disMissProgressDialog(this)
//            username.setText(response.result.userName)


            var list = ArrayList<Docs>()
            list.addAll(response.result.docs)

            setAdapter(list)


        }
    }


    override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        Toast.makeText(this, "fail", Toast.LENGTH_LONG).show()
    }


    fun setAdapter(list: ArrayList<Docs>) {
        adaptor =  Following_Adaptor(this,this,list)
        val layoutManager = LinearLayoutManager(this)
        recycler_view3.layoutManager = layoutManager
        recycler_view3.adapter = adaptor
    }

    override fun customClick( value: Docs,type:String)
    {
        value.userId
        if (type.equals("profile")){
            var intent = Intent(applicationContext, UserProfile::class.java)
            startActivity(intent)
        }

    }
}