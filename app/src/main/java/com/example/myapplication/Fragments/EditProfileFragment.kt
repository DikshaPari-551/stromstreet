package com.example.myapplication.Fragments

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.myapplication.*
import com.example.myapplication.Activities.ChangePassword
import com.example.myapplication.BottomSheets.BottomSheetLogout
import com.example.myapplication.customclickListner.LogoutClickListener
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention.initLoader
import com.example.myapplication.util.SavedPrefManager
import java.lang.Exception


class EditProfileFragment : Fragment(), ApiResponseListener<Responce>, LogoutClickListener {
    lateinit var layoutButtonProfileDetail: LinearLayout
    lateinit var changePassword: LinearLayout
    lateinit var logout: RelativeLayout
    lateinit var backButton: ImageView
    lateinit var userProfile: ImageView
    lateinit var twitter: ImageView
    lateinit var facebook: ImageView
    lateinit var instagram: ImageView
    lateinit var youtube: ImageView
    lateinit var mContext: Context
    lateinit var username: TextView
    lateinit var name: TextView
    lateinit var phone_no: TextView
    lateinit var email_id: TextView
    lateinit var bio: TextView
    lateinit var lottie : LottieAnimationView

    var facebooklink=""
    var twitterlink=""
    var instalink=""
    var youtubelink=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        layoutButtonProfileDetail = v.findViewById(R.id.layout_button_profile_details)
        changePassword = v.findViewById(R.id.change_password)
        mContext = activity!!
        logout = v.findViewById(R.id.layout_logout_button)
        username = v.findViewById(R.id.username)
        name = v.findViewById(R.id.name)
        phone_no = v.findViewById(R.id.phone_no)
        userProfile = v.findViewById(R.id.userProfile)
        email_id = v.findViewById(R.id.email_id)
        twitter = v.findViewById(R.id.twitter)
        facebook = v.findViewById(R.id.facebook)
        instagram = v.findViewById(R.id.instagram)
        youtube = v.findViewById(R.id.youtube)
        youtube = v.findViewById(R.id.youtube)
        bio = v.findViewById(R.id.bio)
        lottie = v.findViewById(R.id.loader)

        profileApi()


        logout.setOnClickListener {
            var bottomsheettt =
                BottomSheetLogout(this)
            fragmentManager?.let { it1 -> bottomsheettt.show(it1, "bottomsheet") }

        }
        twitter.setOnClickListener {
            LINKDATA(twitterlink)

        }
        facebook.setOnClickListener {
            LINKDATA(facebooklink)

        }
        instagram.setOnClickListener {
            LINKDATA(instalink)

        }
        youtube.setOnClickListener {
            LINKDATA(youtubelink)

        }
        backButton = v.findViewById(R.id.back_arrow_edit_profile)
        backButton.setOnClickListener {
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                ProfileFragment()
            )
                ?.commit()
        }

//        logout.setOnClickListener{
//            var bottomSheetLogout=BottomSheetLogout()
//            fragmentManager?.let { it1 -> bottomSheetLogout.show(it1, "bottomsheet") }
//
//        }
        layoutButtonProfileDetail.setOnClickListener {
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                ProfileChangeFragment()
            )
                ?.commit()
        }

        changePassword.setOnClickListener {
            val intent = Intent(activity,ChangePassword::class.java)
            startActivity(intent)
        }


        return v
    }

    private fun LINKDATA(LINK: String)
    {
        try {
            val intent = Intent(Intent.ACTION_VIEW)

            intent.data = Uri.parse("$LINK")
            startActivity(intent)
        }
        catch (e: ActivityNotFoundException)
        {
           Toast.makeText(context,"Oops link not valid..",Toast.LENGTH_LONG)
        }

    }

    private fun profileApi() {
        val Token =
            SavedPrefManager.getStringPreferences(activity, SavedPrefManager.TOKEN).toString()
//        val Token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYxNmZiMTBjZTYzMjY0MjA4ZDA4MWExNSIsImVtYWlsIjoiYWpheUBnbWFpbC5jb20iLCJ1c2VyVHlwZSI6IlVzZXIiLCJpYXQiOjE2MzQ3MTAyMTEsImV4cCI6MTYzNDc5NjYxMX0.NirNVhYOeAlgfalbbSJ4x2KBUK8L62FKXRPENA6CdJY"
//        androidextention.showProgressDialog(activity)
        lottie.initLoader(true)
        val serviceManager = ServiceManager(mContext)
        val callBack: ApiCallBack<Responce> =
            ApiCallBack<Responce>(this, "Update", mContext)

        try {
            serviceManager.getProfile(callBack, Token)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onApiSuccess(response: Responce, apiName: String?) {
//        androidextention.disMissProgressDialog(activity)
        lottie.initLoader(false)
        try {
            username.setText(response.result.userResult.userName)
            name.setText(response.result.userResult.fullName)
            phone_no.setText(response.result.userResult.phoneNumber)
            email_id.setText(response.result.userResult.email)
            bio.setText(response.result.userResult.bio)
        }catch(e: Exception) {
            e.printStackTrace()
        }
        try {
            twitterlink = response.result.userResult.socialLinks.twitter
            facebooklink = response.result.userResult.socialLinks.facebook
            instalink = response.result.userResult.socialLinks.instagram
            youtubelink = response.result.userResult.socialLinks.youtube
        }catch (e: Exception){
            e.printStackTrace()
        }
        Glide.with(mContext).load(response.result.userResult.profilePic)
            .placeholder(R.drawable.circleprofile).into(userProfile)

//        Toast.makeText(activity, "success", Toast.LENGTH_LONG).show()

    }

    override fun onApiErrorBody(response: String?, apiName: String?) {
        lottie.initLoader(false)

        Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        lottie.initLoader(false)

        Toast.makeText(activity, "Server not responding", Toast.LENGTH_LONG).show()
    }


    override fun onResume() {
        super.onResume()
        profileApi()

    }

    override fun logoutClick(value: Boolean) {
        if(value){
            lottie.initLoader(true)
        }
    }

}