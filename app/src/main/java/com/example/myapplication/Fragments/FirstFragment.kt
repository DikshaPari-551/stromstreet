package com.example.myapplication.Fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Activities.PostActivity
import com.example.myapplication.Adaptor.ProfileAdaptor
import com.example.myapplication.Exoplayer
import com.example.myapplication.R
import com.example.myapplication.customclickListner.CustomClickListner3
import com.example.myapplication.customclickListner.CustomClickListneruserpost
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Response.*
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.SavedPrefManager
import okhttp3.ResponseBody

class FirstFragment() : Fragment(), ApiResponseListener<UserPostResponse>,
    CustomClickListneruserpost {

    lateinit var recycler: RecyclerView
    lateinit var noPost: TextView
    lateinit var mContext: Context
    lateinit var adaptor: ProfileAdaptor
    lateinit var USERID: String
    lateinit var progress_bar: ProgressBar
    lateinit var nestedScrollView: NestedScrollView
    var page: Int = 1
    var pages: Int = 0
    var limit: Int = 15
    var list = ArrayList<UserPostDocs>()
    var result: String =""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_first, container, false)
        recycler = v.findViewById(R.id.recycler_view_tab1)
        noPost = v.findViewById(R.id.no_post)
        progress_bar = v.findViewById(R.id.progress_bar)
        nestedScrollView = v.findViewById(R.id.nestedScrollView)
        mContext = activity!!
        userpostlist()

        nestedScrollView.setOnScrollChangeListener(object :
            NestedScrollView.OnScrollChangeListener {
            override fun onScrollChange(

                v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int
            ) {
                if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                    page++
                    progress_bar.visibility = View.VISIBLE
                    if (page > pages) {
                        progress_bar.visibility = View.GONE
                    } else {
                        userpostlist()
                    }
                }
            }
        })
        return v
    }

    private fun userpostlist() {

        if (androidextention.isOnline(mContext)) {
            androidextention.showProgressDialog(mContext)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<UserPostResponse> =
                ApiCallBack<UserPostResponse>(this, "SavedPostList", mContext)

            var apiRequest = Api_Request()
            apiRequest.page = page.toString()
            apiRequest.limit = limit.toString()
            try {
                serviceManager.getPostlist(callBack, apiRequest)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onApiSuccess(response: UserPostResponse, apiName: String?) {
        androidextention.disMissProgressDialog(activity)
        pages = response.result.pages

        if (response.responseCode == "200") {
            androidextention.disMissProgressDialog(mContext)
//            Toast.makeText(mContext, "Success", Toast.LENGTH_LONG).show();
            list.addAll(response.result.docs)
            setAdapter(list)
        }
    }

    override fun onApiErrorBody(response: String?, apiName: String?) {
        androidextention.disMissProgressDialog(mContext)
//        Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_LONG).show()
        noPost.visibility = View.VISIBLE
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        androidextention.disMissProgressDialog(mContext)

        Toast.makeText(activity, "Server not responding", Toast.LENGTH_LONG).show()
    }

    fun setAdapter(list: ArrayList<UserPostDocs>) {
        adaptor = this?.let { ProfileAdaptor(it, list, this) }!!
        val layoutManager = GridLayoutManager(activity, 3)
        recycler?.layoutManager = layoutManager
        recycler?.adapter = adaptor
    }


//    override fun customClick(value: Docss, type: String)

    //    override fun customClick(value: UserPostDocs, type: String) {
//
////
////        if (type.equals("profile")) {
////            var intent = Intent(mContext, PostActivity::class.java)
////            SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager._id, USERID)
//////            intent.putExtra("userId", USERID)
////
////            startActivity(intent)
////        }
//        USERID = value._id
//        if (type.equals("profile")) {
//            if (value.mediaType.toLowerCase().equals("video")) {
//                var intent = Intent(mContext, Exoplayer::class.java)
//                SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager._id, USERID)
//                startActivityForResult(intent,1)
//
//            } else {
//                var intent = Intent(mContext, PostActivity::class.java)
//                SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager._id, USERID)
//                startActivityForResult(intent,1)
//            }
//
//        }
//    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                result = data!!.getStringExtra("result")
                System.out.println("postposition" + result)
                list!!.removeAt(result.toInt());
                adaptor.notifyItemRemoved(result.toInt());
                adaptor.notifyItemRangeChanged(result.toInt(), list.size)
            }
        }
    }

    override fun customClick(value: UserPostDocs, type: String, i: Int) {
        USERID = value._id
        if (type.equals("profile")) {
            if (value.mediaType.toLowerCase().equals("video")) {
                var intent = Intent(mContext, Exoplayer::class.java)
                intent.putExtra("postion",i.toString())
                SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager._id, USERID)
                startActivityForResult(intent, 1)

            } else {
                var intent = Intent(mContext, PostActivity::class.java)
                intent.putExtra("postion",i.toString())
                SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager._id, USERID)
                startActivityForResult(intent, 1)
            }

        }
    }

}