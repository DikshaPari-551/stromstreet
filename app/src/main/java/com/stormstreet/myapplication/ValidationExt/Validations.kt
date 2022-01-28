package com.stormstreet.myapplication.ValidationExt

import android.app.Activity
import android.util.Patterns
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.stormstreet.myapplication.R
import java.util.regex.Pattern

object Validations : Activity() {
    private val PASSWORD_PATTERN =
        Pattern.compile(
            "^" +
                    "(?=.*[@#$%^&+=])" +  // at least 1 special character
                    "(?=\\S+$)" +  // no white spaces
                    ".{4,}" +  // at least 4 characters
                    "$"
        )
    var check: Boolean = true
    fun required(value: String, msg: TextView): Boolean {
        if (value.length == 0) {
            msg.visibility = View.VISIBLE
            msg.setText("*Please enter your full name")
            return false
        } else {
            msg.setText("")
            msg.visibility = View.GONE
            return true
        }
        return true
    }
    fun user(value: String, msg: TextView): Boolean {
        if (value.length == 0) {
            msg.visibility = View.VISIBLE
            msg.setText("*Please enter your user name")
            return false
        } else {
            msg.setText("")
            msg.visibility = View.GONE
            return true
        }
        return true
    }

    fun Email(value: String, msg: TextView): Boolean {
        if (value.length == 0) {
            msg.setText("*Please enter your email address ")
            msg.visibility = View.VISIBLE
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            msg.setText("*Please enter your valid email address")
            msg.visibility = View.VISIBLE
            return false
        } else {
            msg.setText("")
            msg.visibility = View.GONE
            return true
        }
        return true
    }

    fun CheckPhoneNumber(value: String, msg: TextView): Boolean {
        if (value.length == 0) {
            msg.setText("*Please enter your phone number")
            msg.visibility = View.VISIBLE
            return false
        } else if (value.length > 10) {
            msg.setText("*Please enter your phone number up to 10-digits")
            msg.visibility = View.VISIBLE
            return false
        } else {
            msg.setText("")
            msg.visibility = View.GONE
            return true
        }
        return true
    }

    fun Password(value: String, msg: TextView): Boolean {
        if (value.length == 0) {
            msg.setText("*Please enter your password")
            msg.visibility = View.VISIBLE
            return false

        }
//        else if (!PASSWORD_PATTERN.matcher(value).matches()) {
//
//            msg.setText("*Please enter your valid password")
//            msg.visibility = View.VISIBLE
//            return false
//        }
        else if (value.length < 6) {
            msg.setText("*Please enter your password more than 6-digits ")
            msg.visibility = View.VISIBLE
            return false
        } else {
            msg.setText("")
            msg.visibility = View.GONE
            return true
        }
        return true
    }
    fun PasswordLogin(
        value: String,
        msg: TextView

    ): Boolean {
        if (value.length == 0) {
            msg.setText("*Plaese enter your password")
            msg.visibility = View.VISIBLE

            return false
        }
//        else if(!PASSWORD_PATTERN.matcher(value).matches()){
//
//            msg.setText(" Password input field is not valid ")
//            msg.visibility = View.VISIBLE
//            mBackground.visibility = View.VISIBLE
//            mErrorMessage.setText("UserName and Password is not valid")
//            mBackground.setBackgroundResource(R.drawable.background_error)
//        }
        else if (value.length < 6) {
            msg.setText("*Please enter your password more than 6-digits")
            msg.visibility = View.VISIBLE

            return false
        } else {
            msg.setText("")
            msg.visibility = View.GONE

            return true
        }
        return true
    }








    fun EmailLogin(
        value: String,
        msg: TextView,
        mErrorMessage: TextView,
        mBackground: RelativeLayout
    ): Boolean {
        if (value.length == 0) {
            msg.setText("*Please enter email address")
            msg.visibility = View.VISIBLE
            mBackground.visibility = View.GONE
            mErrorMessage.setText("Email and Password is not valid")
            mBackground.setBackgroundResource(R.drawable.background_error)
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            msg.setText("*Please enter your valid email address")
            msg.visibility = View.VISIBLE
            mBackground.visibility = View.GONE
            mErrorMessage.setText("Email and Password is not valid")
            mBackground.setBackgroundResource(R.drawable.background_error)
            return false}
//        } else {
//            msg.setText("")
//            msg.visibility = View.GONE
//            mBackground.visibility = View.GONE
//            mErrorMessage.setText("")
//            mBackground.setBackgroundResource(R.drawable.drawable_back)
//            return true
//        }
         else if (value.length == 0) {
            msg.setText("*Plaese enter your password")
            msg.visibility = View.VISIBLE
            mBackground.visibility = View.GONE
            mErrorMessage.setText("UserName and Password is not valid")
            mBackground.setBackgroundResource(R.drawable.background_error)
            return false
        }
//        else if(!PASSWORD_PATTERN.matcher(value).matches()){
//
//            msg.setText(" Password input field is not valid ")
//            msg.visibility = View.VISIBLE
//            mBackground.visibility = View.VISIBLE
//            mErrorMessage.setText("UserName and Password is not valid")
//            mBackground.setBackgroundResource(R.drawable.background_error)
//        }
        else if (value.length < 6) {
            msg.setText("*Please enter your password more than 6-digits")
            msg.visibility = View.VISIBLE
            mBackground.visibility = View.GONE
            mErrorMessage.setText("UserName and Password is not valid")
            mBackground.setBackgroundResource(R.drawable.background_error)
            return false
        } else {
            msg.setText("")
            msg.visibility = View.GONE
            mBackground.visibility = View.GONE
            mErrorMessage.setText("")
            mBackground.setBackgroundResource(R.drawable.drawable_back)
            return true
        }
        return true
    }

    }

