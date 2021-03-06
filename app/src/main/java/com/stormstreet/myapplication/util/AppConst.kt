package com.stormstreet.myapplication.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.stormstreet.myapplication.R

class AppConst {
    companion object {
        var OLD_PASSWORD : String = "old_password"
        var POST_CATEGORY_ID : String = "_id"
        var USER_IMAGE_LINK : String = "user_image_link"



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