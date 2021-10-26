package com.example.myapplication.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adaptor.CategoryListAdaptor
import com.example.myapplication.R
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Response.CategoryResult
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import okhttp3.ResponseBody


class secondFragment : Fragment() {
lateinit var man:ImageView
    lateinit var textSearch1: LinearLayout
    lateinit var textSearch2: LinearLayout
    lateinit var textSearch3: LinearLayout

    lateinit var textSearch4: LinearLayout
    lateinit var textSearch6: LinearLayout

    lateinit var textSearch5: LinearLayout
    lateinit var categoryList: RecyclerView

    lateinit var serviceManager : ServiceManager
    lateinit var callBack: ApiCallBack<Responce>
    lateinit var mContext : Context
    lateinit var adaptor: CategoryListAdaptor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v= inflater.inflate(R.layout.fragment_second, container, false)
        mContext = activity!!.applicationContext
        serviceManager = ServiceManager(activity)

        man=v.findViewById(R.id.user)

        textSearch1=v.findViewById(R.id.textsearch1)
        textSearch2=v.findViewById(R.id.textsearch2)

        textSearch3=v.findViewById(R.id.textsearch3)
        textSearch4=v.findViewById(R.id.textsearch4)

        textSearch5=v.findViewById(R.id.textsearch5)
        textSearch6=v.findViewById(R.id.textsearch6)
        categoryList=v.findViewById(R.id.categoty_list)

        //api
        categoryListApi()
        textSearch1.setOnClickListener{
            textSearch1.setBackgroundResource(R.drawable.drawable_chat)
            textSearch2.setBackgroundResource(R.drawable.background_edit_text)
            textSearch3.setBackgroundResource(R.drawable.background_edit_text)
            textSearch4.setBackgroundResource(R.drawable.background_edit_text)
            textSearch5.setBackgroundResource(R.drawable.background_edit_text)
            textSearch6.setBackgroundResource(R.drawable.background_edit_text)
        }
        textSearch3.setOnClickListener{
            textSearch3.setBackgroundResource(R.drawable.drawable_chat)
            textSearch2.setBackgroundResource(R.drawable.background_edit_text)
            textSearch1.setBackgroundResource(R.drawable.background_edit_text)
            textSearch4.setBackgroundResource(R.drawable.background_edit_text)
            textSearch5.setBackgroundResource(R.drawable.background_edit_text)
            textSearch6.setBackgroundResource(R.drawable.background_edit_text)
        }
        textSearch4.setOnClickListener{
            textSearch4.setBackgroundResource(R.drawable.drawable_chat)
            textSearch2.setBackgroundResource(R.drawable.background_edit_text)
            textSearch3.setBackgroundResource(R.drawable.background_edit_text)
            textSearch1.setBackgroundResource(R.drawable.background_edit_text)
            textSearch5.setBackgroundResource(R.drawable.background_edit_text)
            textSearch6.setBackgroundResource(R.drawable.background_edit_text)
        }
        textSearch5.setOnClickListener{
            textSearch5.setBackgroundResource(R.drawable.drawable_chat)
            textSearch2.setBackgroundResource(R.drawable.background_edit_text)
            textSearch3.setBackgroundResource(R.drawable.background_edit_text)
            textSearch4.setBackgroundResource(R.drawable.background_edit_text)
            textSearch1.setBackgroundResource(R.drawable.background_edit_text)
            textSearch6.setBackgroundResource(R.drawable.background_edit_text)
        }
        textSearch6.setOnClickListener{
            textSearch6.setBackgroundResource(R.drawable.drawable_chat)
            textSearch2.setBackgroundResource(R.drawable.background_edit_text)
            textSearch3.setBackgroundResource(R.drawable.background_edit_text)
            textSearch4.setBackgroundResource(R.drawable.background_edit_text)
            textSearch1.setBackgroundResource(R.drawable.background_edit_text)
            textSearch5.setBackgroundResource(R.drawable.background_edit_text)
        }
        textSearch2.setOnClickListener{
            textSearch2.setBackgroundResource(R.drawable.drawable_chat)
            textSearch1.setBackgroundResource(R.drawable.background_edit_text)
            textSearch3.setBackgroundResource(R.drawable.background_edit_text)
            textSearch4.setBackgroundResource(R.drawable.background_edit_text)
            textSearch5.setBackgroundResource(R.drawable.background_edit_text)
            textSearch6.setBackgroundResource(R.drawable.background_edit_text)



        }

        man.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                HomeFragment()
            )
                ?.commit()
        }
        return v
    }

    private fun categoryListApi() {
        androidextention.showProgressDialog(mContext)
        callBack =
            ApiCallBack<Responce>(object : ApiResponseListener<Responce> {
                override fun onApiSuccess(response: Responce, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    if (response.responseCode == "200") {
                        val list : List<CategoryResult> = response.result.categoryResult
                        setCategoryListAdaptor(list)
                    } else {
                        Toast.makeText(
                            activity,
                            response.responseMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    Toast.makeText(
                        activity,
                        "error response" + response.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onApiFailure(failureMessage: String?, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    Toast.makeText(
                        activity,
                        "failure response:" + failureMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }

            }, "CategoryList",mContext)

        try {
            serviceManager.getCategoryList(callBack)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun filterApi() {
        androidextention.showProgressDialog(mContext)
        callBack =
            ApiCallBack<Responce>(object : ApiResponseListener<Responce> {
                override fun onApiSuccess(response: Responce, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    if (response.responseCode == "200") {

                    } else {
                        Toast.makeText(
                            activity,
                            response.responseMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    Toast.makeText(
                        activity,
                        "error response" + response.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onApiFailure(failureMessage: String?, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    Toast.makeText(
                        activity,
                        "failure response:" + failureMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }

            }, "Filter",mContext)

//        val api_Request = Api_Request()
//        api_Request.categoryId = ""
//        api_Request.

        try {
            serviceManager.getCategoryList(callBack)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setCategoryListAdaptor(list: List<CategoryResult>) {
        adaptor = CategoryListAdaptor(list)
        val layoutManager = LinearLayoutManager(activity)
        categoryList.layoutManager = layoutManager
        categoryList.adapter = adaptor

    }



}