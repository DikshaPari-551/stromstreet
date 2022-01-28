package com.stormstreet.myapplication.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stormstreet.myapplication.Adaptor.CategoryListAdaptor
import com.stormstreet.myapplication.R
import com.stormstreet.myapplication.customclickListner.FilterCustomListener
import com.stormstreet.myapplication.entity.ApiCallBack
import com.stormstreet.myapplication.entity.Response.CategoryResult
import com.stormstreet.myapplication.entity.Response.Responce
import com.stormstreet.myapplication.entity.Service_Base.ApiResponseListener
import com.stormstreet.myapplication.entity.Service_Base.ServiceManager
import com.stormstreet.myapplication.extension.androidextention


class secondFragment(var s: String) : Fragment(), FilterCustomListener {
    lateinit var man: ImageView
    lateinit var textSearch1: LinearLayout
    lateinit var textSearch2: LinearLayout
    lateinit var textSearch3: LinearLayout

    lateinit var textSearch4: LinearLayout
    lateinit var textSearch6: LinearLayout
    lateinit var applyButton: LinearLayout

    lateinit var textSearch5: LinearLayout
    lateinit var categoryList: RecyclerView

    lateinit var serviceManager: ServiceManager
    lateinit var callBack: ApiCallBack<Responce>
    lateinit var mContext: Context
    lateinit var adaptor: CategoryListAdaptor
    var catId:  ArrayList<CategoryResult> = ArrayList<CategoryResult>()
    var maxDis: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_second, container, false)
        mContext = activity!!.applicationContext
        serviceManager = ServiceManager(activity)

        man = v.findViewById(R.id.user)

        textSearch1 = v.findViewById(R.id.textsearch1)
        textSearch2 = v.findViewById(R.id.textsearch2)
        applyButton = v.findViewById(R.id.apply_button)

        textSearch3 = v.findViewById(R.id.textsearch3)
        textSearch4 = v.findViewById(R.id.textsearch4)

        textSearch5 = v.findViewById(R.id.textsearch5)
        textSearch6 = v.findViewById(R.id.textsearch6)
        categoryList = v.findViewById(R.id.categoty_list)

        //api
        categoryListApi()
        textSearch1.setOnClickListener {
            textSearch1.setBackgroundResource(R.drawable.drawable_chat)
            textSearch2.setBackgroundResource(R.drawable.background_edit_text)
            textSearch3.setBackgroundResource(R.drawable.background_edit_text)
            textSearch4.setBackgroundResource(R.drawable.background_edit_text)
            textSearch5.setBackgroundResource(R.drawable.background_edit_text)
            textSearch6.setBackgroundResource(R.drawable.background_edit_text)
            maxDis = 5
        }
        textSearch3.setOnClickListener {
            textSearch3.setBackgroundResource(R.drawable.drawable_chat)
            textSearch2.setBackgroundResource(R.drawable.background_edit_text)
            textSearch1.setBackgroundResource(R.drawable.background_edit_text)
            textSearch4.setBackgroundResource(R.drawable.background_edit_text)
            textSearch5.setBackgroundResource(R.drawable.background_edit_text)
            textSearch6.setBackgroundResource(R.drawable.background_edit_text)
            maxDis = 10
        }
        textSearch4.setOnClickListener {
            textSearch4.setBackgroundResource(R.drawable.drawable_chat)
            textSearch2.setBackgroundResource(R.drawable.background_edit_text)
            textSearch3.setBackgroundResource(R.drawable.background_edit_text)
            textSearch1.setBackgroundResource(R.drawable.background_edit_text)
            textSearch5.setBackgroundResource(R.drawable.background_edit_text)
            textSearch6.setBackgroundResource(R.drawable.background_edit_text)
            maxDis = 15
        }
        textSearch5.setOnClickListener {
            textSearch5.setBackgroundResource(R.drawable.drawable_chat)
            textSearch2.setBackgroundResource(R.drawable.background_edit_text)
            textSearch3.setBackgroundResource(R.drawable.background_edit_text)
            textSearch4.setBackgroundResource(R.drawable.background_edit_text)
            textSearch1.setBackgroundResource(R.drawable.background_edit_text)
            textSearch6.setBackgroundResource(R.drawable.background_edit_text)
            maxDis = 20

        }
        textSearch6.setOnClickListener {
            textSearch6.setBackgroundResource(R.drawable.drawable_chat)
            textSearch2.setBackgroundResource(R.drawable.background_edit_text)
            textSearch3.setBackgroundResource(R.drawable.background_edit_text)
            textSearch4.setBackgroundResource(R.drawable.background_edit_text)
            textSearch1.setBackgroundResource(R.drawable.background_edit_text)
            textSearch5.setBackgroundResource(R.drawable.background_edit_text)
            maxDis = 30
        }
        textSearch2.setOnClickListener {
            textSearch2.setBackgroundResource(R.drawable.drawable_chat)
            textSearch1.setBackgroundResource(R.drawable.background_edit_text)
            textSearch3.setBackgroundResource(R.drawable.background_edit_text)
            textSearch4.setBackgroundResource(R.drawable.background_edit_text)
            textSearch5.setBackgroundResource(R.drawable.background_edit_text)
            textSearch6.setBackgroundResource(R.drawable.background_edit_text)
            maxDis = 50


        }

        man.setOnClickListener {
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                HomeFragment()
            )
                ?.commit()
        }
        applyButton.setOnClickListener {
            var list : ArrayList<String> = ArrayList<String>()
            for (i in catId){
                if (i.flag.equals(true)){
                    list.add(i._id.toString())
                }


            }

            if (s.equals("home")) {
                val bundle = Bundle()
                bundle.putSerializable("CAT_ID", list);
               // bundle.putString("CAT_ID", catId)
                bundle.putInt("MAX_DIS", maxDis)
                val fragObj = HomeFragment()
                fragObj.arguments = bundle
                getFragmentManager()?.beginTransaction()?.replace(
                    R.id.linear_layout,
                    fragObj
                )
                    ?.commit()
            } else if (s.equals("trending")) {
                val bundle = Bundle()
                bundle.putSerializable("CAT_ID", list)
                bundle.putInt("MAX_DIS", maxDis)
                val fragObj = TrendingFragment()
                fragObj.arguments = bundle
                getFragmentManager()?.beginTransaction()?.replace(
                    R.id.linear_layout,
                    fragObj
                )
                    ?.commit()
            } else if (s.equals("following")) {
                val bundle = Bundle()
                bundle.putSerializable("CAT_ID", list)
                bundle.putInt("MAX_DIS", maxDis)
                val fragObj = FollowingActivityFragment()
                fragObj.arguments = bundle
                getFragmentManager()?.beginTransaction()?.replace(
                    R.id.linear_layout,
                    fragObj
                )
                    ?.commit()
            }
        }
        return v
    }

    private fun categoryListApi() {
        if (androidextention.isOnline(mContext)) {
            androidextention.showProgressDialog(mContext)
            callBack =
                ApiCallBack<Responce>(object : ApiResponseListener<Responce> {

                    override fun onApiSuccess(response: Responce, apiName: String?) {
                        androidextention.disMissProgressDialog(mContext)
                        if (response.responseCode == "200") {
                            val list: ArrayList<CategoryResult> = response.result.categoryResult
                            setCategoryListAdaptor(list)
                        } else {
                            Toast.makeText(
                                activity,
                                response.responseMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onApiErrorBody(response: String?, apiName: String?) {
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

                }, "CategoryList", mContext)

            try {
                serviceManager.getCategoryList(callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

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

                override fun onApiErrorBody(response: String?, apiName: String?) {
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

            }, "Filter", mContext)

//        val api_Request = Api_Request()
//        api_Request.categoryId = ""
//        api_Request.

        try {
            serviceManager.getCategoryList(callBack)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setCategoryListAdaptor(list: ArrayList<CategoryResult>) {
        adaptor = CategoryListAdaptor(list, this, mContext)
        val layoutManager = LinearLayoutManager(activity)
        categoryList.layoutManager = layoutManager
        categoryList.adapter = adaptor
        adaptor.notifyDataSetChanged()

    }

    override fun filterCustomListener(list: ArrayList<CategoryResult>) {
        catId = list
    }


}