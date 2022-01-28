package com.stormstreet.myapplication.Activities

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.stormstreet.myapplication.R
import com.stormstreet.myapplication.entity.ApiCallBack
import com.stormstreet.myapplication.entity.Request.Api_Request
import com.stormstreet.myapplication.entity.Response.Responce
import com.stormstreet.myapplication.entity.Service_Base.ApiResponseListener
import com.stormstreet.myapplication.entity.Service_Base.ServiceManager
import com.stormstreet.myapplication.extension.androidextention
import com.stormstreet.myapplication.extension.androidextention.initLoader
import com.stormstreet.myapplication.util.SavedPrefManager
import com.stormstreet.sleeponcue.extension.diasplay_toast
import java.lang.Exception

class ReportPost : AppCompatActivity(), ApiResponseListener<Responce> {

    lateinit var report_text: EditText
    lateinit var submitreport: LinearLayout
    lateinit var backArrow: ImageView
    lateinit var lottie: LottieAnimationView
    lateinit var reportvalue: String
    lateinit var _id: String
    var mContext: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_post)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.black)
        }
        report_text = findViewById(R.id.report_text)
        submitreport = findViewById(R.id.submitreport)
        backArrow = findViewById(R.id.back_arrow)
        lottie = findViewById(R.id.loader)
        getInent()

        backArrow.setOnClickListener {
            finish()
        }

        submitreport.setOnClickListener {
            reportvalue= report_text.text.toString().trim()

            reportpostapi()
        }
    }
    private fun getInent() {
        try {
            _id = SavedPrefManager.getStringPreferences(this,SavedPrefManager._id).toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun reportpostapi() {

        if (androidextention.isOnline(this)) {
//            androidextention.showProgressDialog(this)
            lottie.initLoader(true)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(this, "CreateReport", mContext)
            val apiRequest = Api_Request()
            apiRequest.reportType = "Post"
            apiRequest.comment = "string"
            apiRequest.description = reportvalue.toString()

            try {
                serviceManager.reportPostApi(callBack, apiRequest,_id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            diasplay_toast("Check Your Internet Connection")
        }
    }

    override fun onApiSuccess(response: Responce, apiName: String?) {
        lottie.initLoader(false)
        Toast.makeText(this, "Report Sent Successfully", Toast.LENGTH_LONG).show()
        report_text.setText(null)
    }

    override fun onApiErrorBody(response: String?, apiName: String?) {
        lottie.initLoader(false)
        Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        lottie.initLoader(false)
        Toast.makeText(this, "Server not responding", Toast.LENGTH_LONG).show()
    }
}