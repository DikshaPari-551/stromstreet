package com.stormstreet.myapplication.Activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.stormstreet.myapplication.Adaptor.Follower_Adaptor
import com.stormstreet.myapplication.R
import com.stormstreet.myapplication.customclickListner.CustomClickListner2
import com.stormstreet.myapplication.entity.ApiCallBack
import com.stormstreet.myapplication.entity.Response.Docss
import com.stormstreet.myapplication.entity.Response.LocalActivityResponse
import com.stormstreet.myapplication.entity.Response.Responce
import com.stormstreet.myapplication.entity.Service_Base.ApiResponseListener
import com.stormstreet.myapplication.entity.Service_Base.ServiceManager
import com.stormstreet.myapplication.extension.androidextention
import com.stormstreet.myapplication.extension.androidextention.initLoader
import com.stormstreet.myapplication.util.SavedPrefManager

class Followers : AppCompatActivity(), ApiResponseListener<LocalActivityResponse>,CustomClickListner2 {
    lateinit var adaptor: Follower_Adaptor
    lateinit var recycler_view3: RecyclerView
    lateinit var back_arrow_chat: ImageView
    var otherUserId: String = ""
    var Followersdata: String = ""

    lateinit var progress_bar: ProgressBar
    lateinit var nestedScrollView: NestedScrollView
    lateinit var lottie : LottieAnimationView

    var list = ArrayList<Docss>()
    var page: Int = 1
    var pages: Int = 0
    var limit : Int = 10
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

        recycler_view3 = findViewById(R.id.recycler_view3)
        back_arrow_chat = findViewById(R.id.back_arrow_chat)
        progress_bar = findViewById(R.id.progress_bar)
        nestedScrollView = findViewById(R.id.nestedScrollView)
        lottie = findViewById(R.id.loader)
        GETINTENT()

        back_arrow_chat.setOnClickListener {
            finish()
        }

          nestedScrollView.setOnScrollChangeListener(object :  NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(
                v: NestedScrollView?,scrollX: Int,scrollY: Int,oldScrollX: Int,oldScrollY: Int) {
                if(scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight){
//                    val lastVisibleItemPosition: Int = layoutManager.findLastVisibleItemPosition()

                    page++
                    progress_bar.visibility= View.VISIBLE
                    if(page > pages) {
                        progress_bar.visibility= View.GONE
                    } else {
                        followerApi()
                        androidextention.disMissProgressDialog(mContext)
                    }
                }
            }
        })
    }

    private fun GETINTENT()
    {
        followerApi()
    }

    private fun Blocaklist() {
        lottie.initLoader(true)
        val serviceManager = ServiceManager(mContext)
        val callBack: ApiCallBack<LocalActivityResponse> =
                ApiCallBack<LocalActivityResponse>(this, "Userblocklist", mContext)

        try {
            serviceManager.blocklist(callBack)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }


    private fun followerApi() {
        val Token = SavedPrefManager.getStringPreferences(this, SavedPrefManager.TOKEN).toString()
        if (androidextention.isOnline(this)) {
//            androidextention.showProgressDialog(this)
            lottie.initLoader(true)

            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<LocalActivityResponse> =
                ApiCallBack<LocalActivityResponse>(this, "Follower", mContext)
//            val apiRequest = Api_Request()
//            apiRequest.email = emailSignUp_et.getText().toString().trim()
            try {
                serviceManager.getFollower(callBack,page.toString(),limit.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onApiSuccess(response: LocalActivityResponse, apiName: String?) {
        if (response.responseCode == "200") {
            lottie.initLoader(false)
            if(apiName.equals("Userblocklist"))
            {
                pages = response.result.pages
//            Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();
                list.addAll(response.result.docslist)
//            list.addAll(response.result.docss)
                setAdapter(list)
            }
            else
            {


                list.addAll(response.result.docs)
//            list.addAll(response.result.docss)
                setAdapter(list)
            }
//            androidextention.disMissProgressDialog(this)



        }
    }


    override fun onApiErrorBody(response: String?, apiName: String?) {
        lottie.initLoader(false)

        Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        lottie.initLoader(false)

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