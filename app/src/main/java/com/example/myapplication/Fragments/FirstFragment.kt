package com.example.myapplication.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Activities.PostActivity
import com.example.myapplication.Adaptor.ProfileAdaptor
import com.example.myapplication.Adaptor.SaveListAdaptor
import com.example.myapplication.R
import com.example.myapplication.customclickListner.CustomClickListner
import com.example.myapplication.customclickListner.CustomClickListner2
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Response.Docs
import com.example.myapplication.entity.Response.Docss
import com.example.myapplication.entity.Response.LocalActivityResponse
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.SavedPrefManager
import okhttp3.ResponseBody

class FirstFragment : Fragment(), ApiResponseListener<LocalActivityResponse> , CustomClickListner2 {

    lateinit var recycler: RecyclerView
    lateinit var mContext: Context
    lateinit var adaptor: ProfileAdaptor
    lateinit var USERID: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_first, container, false)
        mContext = activity!!
        userpostlist()

        recycler = v.findViewById(R.id.recycler_view_tab1)
        return v

    }

    private fun userpostlist() {

        if (androidextention.isOnline(mContext)) {
            androidextention.showProgressDialog(mContext)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<LocalActivityResponse> =
                ApiCallBack<LocalActivityResponse>(this, "SavedPostList", mContext)


            try {
                serviceManager.getPostlist(callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onApiSuccess(response: LocalActivityResponse, apiName: String?) {
        androidextention.disMissProgressDialog(activity)
        if (response.responseCode == "200") {
            androidextention.disMissProgressDialog(mContext)
            Toast.makeText(mContext, "Success", Toast.LENGTH_LONG).show();
            var list = ArrayList<Docss>()
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

    fun setAdapter(list: ArrayList<Docss>) {
        adaptor = this?.let { ProfileAdaptor(it,list, this) }!!
        val layoutManager = GridLayoutManager(activity, 3)
        recycler?.layoutManager = layoutManager
        recycler?.adapter = adaptor
    }


    override fun customClick(value: Docss, type: String) {
        USERID = value._id

        if (type.equals("profile")) {
            var intent = Intent(mContext, PostActivity::class.java)
            SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager._id, USERID)
//            intent.putExtra("userId", USERID)

            startActivity(intent)
        }
    }

}