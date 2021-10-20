package com.example.myapplication.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.myapplication.R
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.SavedPrefManager
import okhttp3.ResponseBody
import java.lang.Exception


class ProfileFragment : Fragment() , ApiResponseListener<Responce> {
    lateinit var tag:ImageView
    lateinit var back_tab1:LinearLayout
    lateinit var backButton:ImageView
    lateinit var mContext :Context

    lateinit var color_grid:ImageView
    lateinit var back_tab:LinearLayout
    lateinit var layout_tab1:RelativeLayout
    lateinit var layout_tab2:RelativeLayout
    lateinit var username:TextView
    lateinit var followers:TextView
    lateinit var following:TextView
    lateinit var buttonProfileDetail:LinearLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_profile, container, false)
        mContext= activity!!
        profileApi()
        tag=v.findViewById(R.id.tag)
        back_tab1=v.findViewById(R.id.back_tab1)
        buttonProfileDetail=v.findViewById(R.id.button_profile_detail)
        buttonProfileDetail.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                EditProfileFragment()
            )
                ?.commit()

        }
        backButton=v.findViewById(R.id.back_arrow)
        backButton.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                HomeFragment()
            )
                ?.commit()
        }

        color_grid=v.findViewById(R.id.color_grid)
        back_tab=v.findViewById(R.id.back_tab)
        layout_tab1=v.findViewById(R.id.layout_tab1)
        layout_tab2=v.findViewById(R.id.layout_tab2)
        username = v.findViewById(R.id.username)
        followers = v.findViewById(R.id.followers)
        following = v.findViewById(R.id.following)

        color_grid.setImageDrawable(resources.getDrawable(R.drawable.color_grid))
        back_tab.setBackgroundResource(R.drawable.rectangle_tab)
        tag.setImageDrawable(resources.getDrawable(R.drawable.tag))
        back_tab1.setBackgroundColor(resources.getColor(R.color.edit_color))
        getFragmentManager()?.beginTransaction()?.replace(
            R.id.layout_tabsss,
            FirstFragment()
        )
            ?.commit()

        layout_tab1.setOnClickListener{
            color_grid.setImageDrawable(resources.getDrawable(R.drawable.color_grid))
            back_tab.setBackgroundResource(R.drawable.rectangle_tab)
            tag.setImageDrawable(resources.getDrawable(R.drawable.tag))
            back_tab1.setBackgroundColor(resources.getColor(R.color.edit_color))
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.layout_tabsss,
                FirstFragment()
            )
                ?.commit()

        }
        layout_tab2.setOnClickListener{
            color_grid.setImageDrawable(resources.getDrawable(R.drawable.grid))
            back_tab.setBackgroundColor(resources.getColor(R.color.edit_color))
            tag.setImageDrawable(resources.getDrawable(R.drawable.ggg))
            back_tab1.setBackgroundResource(R.drawable.rectangle_tab)
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.layout_tabsss,
                SeconddFragment()
            )
                ?.commit()
        }
//        tab_layout=v.findViewById(R.id.tab_layout)
//        viewPager=v.findViewById(R.id.viewPager)

//        var mFragAdaptor= fragmentManager?.let { FragAdaptor(it) }
//        viewPager.adapter=mFragAdaptor
//        tab_layout.setupWithViewPager(viewPager)
//        mFragAdaptor?.add(FirstFragment(),"one")
//        mFragAdaptor?.add(SeconddFragment(),"two")

        return v
    }

    private fun profileApi() {
        val Token = SavedPrefManager.getStringPreferences(activity,SavedPrefManager.TOKEN).toString()
//        val Token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYxNmZiMTBjZTYzMjY0MjA4ZDA4MWExNSIsImVtYWlsIjoiYWpheUBnbWFpbC5jb20iLCJ1c2VyVHlwZSI6IlVzZXIiLCJpYXQiOjE2MzQ3MTAyMTEsImV4cCI6MTYzNDc5NjYxMX0.NirNVhYOeAlgfalbbSJ4x2KBUK8L62FKXRPENA6CdJY"
        androidextention.showProgressDialog(activity)
        val serviceManager = ServiceManager(mContext)
        val callBack: ApiCallBack<Responce> =
            ApiCallBack<Responce>(this, "Update", mContext)

        try {
            serviceManager.getProfile(callBack,Token)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onApiSuccess(response: Responce, apiName: String?) {
        androidextention.disMissProgressDialog(activity)
        username.setText(response.result.userResult.userName)
        followers.setText(response.result.followerCount.toString())
        following.setText(response.result.followingCount.toString())

        Toast.makeText(activity, "success", Toast.LENGTH_LONG).show()

    }

    override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
        Toast.makeText(activity, "error", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        Toast.makeText(activity, "fail", Toast.LENGTH_LONG).show()
    }


}