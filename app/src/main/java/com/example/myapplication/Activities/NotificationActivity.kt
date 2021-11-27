package com.example.myapplication.Activities

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adaptor.NotificationAdaptor
import com.example.myapplication.Exoplayer
import com.example.myapplication.R
import com.example.myapplication.customclickListner.CustomClickListner2
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Response.Docss
import com.example.myapplication.entity.Response.LocalActivityResponse
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.SavedPrefManager

class NotificationActivity : AppCompatActivity(), ApiResponseListener<LocalActivityResponse>,
    CustomClickListner2 {
    lateinit var adaptor: NotificationAdaptor
    lateinit var recycler_view3: RecyclerView
    lateinit var back_arrow_chat: ImageView
    var otherUserId: String = ""
    lateinit var progress_bar: ProgressBar
    lateinit var nestedScrollView: NestedScrollView
    var list = ArrayList<Docss>()
    var page: Int = 1
    var pages: Int = 0
    var limit: Int = 10
    var mContext: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.black)
        }
        followerApi()
        recycler_view3 = findViewById(R.id.recycler_view3)
        back_arrow_chat = findViewById(R.id.back_arrow_chat)
        progress_bar = findViewById(R.id.progress_bar)
        nestedScrollView = findViewById(R.id.nestedScrollView)
        back_arrow_chat.setOnClickListener {
            finish()
        }

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
                        followerApi()
                        androidextention.disMissProgressDialog(mContext)
                    }
                }
            }
        })
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
                serviceManager.getNotification(callBack, page.toString(), limit.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onApiSuccess(response: LocalActivityResponse, apiName: String?) {
        if (response.responseCode == "200") {
            androidextention.disMissProgressDialog(this)
            pages = response.result.pages
            list.addAll(response.result.docs)
            setAdapter(list)


        }
    }


    override fun onApiErrorBody(response: String?, apiName: String?) {
        androidextention.disMissProgressDialog(this)
        Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        androidextention.disMissProgressDialog(this)
        Toast.makeText(this, "Server not responding", Toast.LENGTH_LONG).show()
    }


    fun setAdapter(list: ArrayList<Docss>) {
        adaptor = this?.let { NotificationAdaptor(it, list, this) }!!
        val layoutManager = LinearLayoutManager(this)
        recycler_view3.layoutManager = layoutManager
        recycler_view3.adapter = adaptor
    }

    override fun customClick(data: Docss, type: String) {
        try {
            if (data.postId.mediaType.equals("IMAGE")) {
                postid_(data);
                if (data.notificationType.equals("LIKE_POST")) {
                    val intent = Intent(this, PostActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this, PostActivity2::class.java)
                    startActivity(intent)
                }
            } else if (data.postId.mediaType.equals("VIDEO")) {
                postid_(data);
                if (data.notificationType.equals("LIKE_POST")) {
                    val intent = Intent(this, Exoplayer::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this, PostActivity2::class.java)
                    startActivity(intent)
                }
            } else if (data.notificationType.equals("FOLLOW")) {
                postid_(data);
                val intent = Intent(this, UserProfile::class.java)
                startActivity(intent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun postid_(data: Docss) {
        SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager._id, data.postId._id)
        SavedPrefManager.saveStringPreferences(
            mContext,
            SavedPrefManager.otherUserId,
            data.notificationBy._id
        )
    }

//    override fun customClick(value: Docss, type: String) {
////        value.userId
//        otherUserId = value.followerId._id
//
//        if (type.equals("profile")) {
//            var intent = Intent(applicationContext, UserProfile::class.java)
//            SavedPrefManager.saveStringPreferences(
//                mContext,
//                SavedPrefManager.otherUserId,
//                otherUserId
//            )
//
//            startActivity(intent)
//        }
////        if (type.equals("Follow")){
////            followerApi()
////        }
//    }
}
