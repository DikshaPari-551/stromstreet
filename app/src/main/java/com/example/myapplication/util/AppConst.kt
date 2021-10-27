package com.example.myapplication.util

import android.R
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


class AppConst {
    companion object {
        const val OLD_PASSWORD : String = "old_password"

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