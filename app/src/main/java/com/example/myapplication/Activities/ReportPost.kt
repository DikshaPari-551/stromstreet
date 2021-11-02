package com.example.myapplication.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.SavedPrefManager
import com.example.sleeponcue.extension.diasplay_toast
import okhttp3.ResponseBody
import java.lang.Exception

class ReportPost : AppCompatActivity(), ApiResponseListener<Responce> {

    lateinit var report_text: EditText
    lateinit var submitreport: LinearLayout
    lateinit var reportvalue: String
    lateinit var _id: String
    var mContext: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_post)
        report_text = findViewById(R.id.report_text)
        submitreport = findViewById(R.id.submitreport)
        getInent()


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
            androidextention.showProgressDialog(this)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(this, "CreateReport", mContext)
            val apiRequest = Api_Request()
            apiRequest.reportType = "post"
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
        Toast.makeText(this, "Report Sent Successfully", Toast.LENGTH_LONG).show()
        report_text.setText(null)
    }

    override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
        Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        Toast.makeText(this, "Server not responding", Toast.LENGTH_LONG).show()
    }
}