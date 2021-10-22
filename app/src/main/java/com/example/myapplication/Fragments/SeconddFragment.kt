package com.example.myapplication.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adaptor.SaveListAdaptor
import com.example.myapplication.R
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Response.Docs
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.SavedPrefManager
import okhttp3.ResponseBody

class SeconddFragment : Fragment(), ApiResponseListener<Responce> {
    lateinit var recyclerview:RecyclerView
    lateinit var mContext : Context
    lateinit var adaptor: SaveListAdaptor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v=  inflater.inflate(R.layout.fragment_secondd, container, false)
        mContext= activity!!

        savedpostlist()

        recyclerview=v.findViewById(R.id.recyclervieww)
//        val adaptor = ProfileAdaptor()
//        val layoutManager = GridLayoutManager(activity,3)
//        recyclerview.layoutManager = layoutManager
//        recyclerview.adapter = adaptor

        return  v
    }

    private fun savedpostlist() {
        val Token = SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.TOKEN).toString()

        if (androidextention.isOnline(mContext)) {
            androidextention.showProgressDialog(mContext)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(this, "SavedPostList", mContext)


            try {
                serviceManager.getSavedList(callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onApiSuccess(response: Responce, apiName: String?) {
        androidextention.disMissProgressDialog(activity)
        if (response.responseCode == "200") {
            androidextention.disMissProgressDialog(mContext)
            Toast.makeText(mContext, "Success", Toast.LENGTH_LONG).show();
            var list = ArrayList<Docs>()
            list.addAll(response.result.docs)
            setAdapter(list)


        }
    }

    override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
        Toast.makeText(activity, "error", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        Toast.makeText(activity, "fail", Toast.LENGTH_LONG).show()
    }

    fun setAdapter(list: ArrayList<Docs>) {
        adaptor = this?.let { SaveListAdaptor(it, list) }!!
        val layoutManager = GridLayoutManager(activity,3)
        recyclerview?.layoutManager = layoutManager
        recyclerview?.adapter = adaptor
    }

}