package com.example.myapplication.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Activities.PostActivity
import com.example.myapplication.Adaptor.SaveListAdaptor
import com.example.myapplication.Exoplayer
import com.example.myapplication.R
import com.example.myapplication.customclickListner.CustomClickListner
import com.example.myapplication.customclickListner.CustomClickListner3
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Response.Docs
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Response.UserPostDocs
import com.example.myapplication.entity.Response.UserPostResponse
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.SavedPrefManager
import okhttp3.ResponseBody

class SeconddFragment : Fragment(), ApiResponseListener<UserPostResponse> , CustomClickListner3 {
    lateinit var recyclerview:RecyclerView
    lateinit var mContext : Context
    lateinit var adaptor: SaveListAdaptor
    lateinit var USERID: String
    lateinit var noPost: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v=  inflater.inflate(R.layout.fragment_secondd, container, false)
        noPost = v.findViewById(R.id.no_post)

        mContext= activity!!

        savedpostlist()

        recyclerview=v.findViewById(R.id.recyclervieww)

        return  v
    }

    private fun savedpostlist() {
        val Token = SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.TOKEN).toString()

        if (androidextention.isOnline(mContext)) {
            androidextention.showProgressDialog(mContext)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<UserPostResponse> =
                ApiCallBack<UserPostResponse>(this, "SavedPostList", mContext)


            try {
                serviceManager.getSavedList(callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onApiSuccess(response: UserPostResponse, apiName: String?) {
        androidextention.disMissProgressDialog(activity)
        if (response.responseCode == "200") {
            androidextention.disMissProgressDialog(mContext)
//            Toast.makeText(mContext, "Success", Toast.LENGTH_LONG).show();
            var list = ArrayList<UserPostDocs>()
            list.addAll(response.result.docs)
            setAdapter(list)


        }
    }

    override fun onApiErrorBody(response: String?, apiName: String?) {
        androidextention.disMissProgressDialog(mContext)

//        Toast.makeText(activity, "No Post Saved", Toast.LENGTH_LONG).show()
        noPost.visibility = View.VISIBLE

    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        androidextention.disMissProgressDialog(mContext)

        Toast.makeText(activity, "Server not responding", Toast.LENGTH_LONG).show()
    }

    fun setAdapter(list: ArrayList<UserPostDocs>) {
        adaptor = this?.let { SaveListAdaptor(it, list,this) }!!
        val layoutManager = GridLayoutManager(activity,3)
        recyclerview?.layoutManager = layoutManager
        recyclerview?.adapter = adaptor
    }

    override fun customClick(value: UserPostDocs, type: String) {
        USERID = value.postId._id

//        if (type.equals("profile")) {
//            var intent = Intent(mContext, PostActivity::class.java)
//            SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager._id, USERID)
////            intent.putExtra("userId", USERID)
//
//            startActivity(intent)
//        }
//try {
//
//}catch (e:Exception){
//    e.printStackTrace()
//}

        if (type.equals("profile")) {
            if(value.postId.mediaType.toLowerCase().equals("video"))
            {
                var intent = Intent(mContext, Exoplayer::class.java)
                SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager._id, USERID)
                startActivity(intent)

            }
            else

            {
                var intent = Intent(mContext, PostActivity::class.java)
                SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager._id, USERID)
                startActivity(intent)
            }

        }
    }


}

