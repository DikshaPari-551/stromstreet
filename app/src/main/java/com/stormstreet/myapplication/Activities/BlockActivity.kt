package com.stormstreet.myapplication.Activities

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.stormstreet.myapplication.Adaptor.Blockadapter
import com.stormstreet.myapplication.R
import com.stormstreet.myapplication.customclickListner.CustomClickListner2
import com.stormstreet.myapplication.entity.ApiCallBack
import com.stormstreet.myapplication.entity.Response.Docss
import com.stormstreet.myapplication.entity.Response.LocalActivityResponse
import com.stormstreet.myapplication.entity.Response.Responce
import com.stormstreet.myapplication.entity.Service_Base.ApiResponseListener
import com.stormstreet.myapplication.entity.Service_Base.ServiceManager
import com.stormstreet.myapplication.extension.androidextention.initLoader
import com.stormstreet.sleeponcue.extension.diasplay_toast
import java.lang.Exception


class BlockActivity : AppCompatActivity(), ApiResponseListener<LocalActivityResponse>,
    CustomClickListner2
{
    var mContext: Context = this
    var pages: Int = 0
    lateinit var adaptor: Blockadapter
    lateinit var recycler_view3: RecyclerView
    var list = ArrayList<Docss>()
    lateinit var back_arrow: ImageView
    lateinit var lottie : LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blocklist)
        recycler_view3 = findViewById(R.id.recycler_view3)
        lottie = findViewById(R.id.loader)
        back_arrow = findViewById(R.id.back_arrow_chat)
        back_arrow.setOnClickListener(
            {
                finish()
            }
        )
        Blocaklist();
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

    override fun onApiSuccess(response: LocalActivityResponse, apiName: String?) {
        lottie.initLoader(false)

        if(apiName.equals("Userblocklist"))
        {
            if(list.size==0)
            {
                pages = response.result.pages

                list.addAll(response.result.docslist)
//            list.addAll(response.result.docss)
                setAdapter(list)
            }
            else{
                Toast.makeText(this, "No data found..", Toast.LENGTH_LONG).show();
                finish()
            }

        }
        else
            if(apiName.equals("Unblock"))
            {
                diasplay_toast(response.responseMessage)
                finish()
            }
    }

    private fun setAdapter(list: ArrayList<Docss>)
    {
        adaptor =  Blockadapter(this, list,this)
        val layoutManager = LinearLayoutManager(this)
        recycler_view3.layoutManager = layoutManager
        recycler_view3.adapter = adaptor
    }

    override fun onApiErrorBody(response: String?, apiName: String?) {
        lottie.initLoader(false)

        Toast.makeText(this, response, Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        lottie.initLoader(false)

        Toast.makeText(this, failureMessage, Toast.LENGTH_LONG).show()
    }

    override fun customClick(value: Docss, type: String) {
        lottie.initLoader(true)
        val serviceManager = ServiceManager(mContext)
        val callBack: ApiCallBack<LocalActivityResponse> =
            ApiCallBack<LocalActivityResponse>(this, "Unblock", mContext)

        try {
            serviceManager.UnblockOtherUser(callBack, value._id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}