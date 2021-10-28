package com.example.myapplication.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.myapplication.R

class AppConst {
    companion object {
        var OLD_PASSWORD : String = "old_password"
        var IMAGEDATA : String = "false"
        var ONEPHOTO : String = ""
        var POST_CATEGORY_ID : String = "_id"
        var USER_SIGNUP_IMAGE : String = "user_image"
        var USER_IMAGE_UPLOADED : String = "false"
        var USER_IMAGE_LINK : String = "user_image_link"
        var MEDIA_TYPE : String = "media_type"


        fun hideKeyboard(activity: Activity) {
            try {
                val `in`: InputMethodManager =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                val view: View = activity.findViewById(R.id.content)
                assert(`in` != null)
                `in`.hideSoftInputFromWindow(
                    view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            } catch (ignored: Throwable) {
            }
        }
    }
}