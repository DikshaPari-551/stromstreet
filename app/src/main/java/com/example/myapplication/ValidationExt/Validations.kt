package com.example.myapplication.ValidationExt

import android.app.Activity
import android.util.Patterns
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.myapplication.R
import java.util.regex.Pattern

object Validations : Activity() {
     private val PASSWORD_PATTERN =
         Pattern.compile(
             "^" +
                     "(?=.*[@#$%^&+=])" +  // at least 1 special character
                     "(?=\\S+$)" +  // no white spaces
                     ".{4,}" +  // at least 4 characters
                     "$")
 var check:Boolean=true
    fun required(value: String, msg:TextView){
        if(value.length ==0){
            msg.visibility = View.VISIBLE
            msg.setText("Full Name input field is null")
        }

        else{
            msg.setText("")
            msg.visibility = View.GONE

        }

    }
     fun Email(value: String,msg:TextView ){
         if (value.length==0){
             msg.setText("Email Address input field is empty")
             msg.visibility = View.VISIBLE

         }
         else if(!Patterns.EMAIL_ADDRESS.matcher(value).matches()){
             msg.setText("Email Address input field is not valid")
             msg.visibility = View.VISIBLE

         }
         else{
             msg.setText("")
             msg.visibility = View.GONE

         }
     }

     fun CheckPhoneNumber(value: String,msg: TextView){
         if (value.length==0){
             msg.setText("phone number input field is empty")
             msg.visibility = View.VISIBLE

         }
         else if (value.length>=12){
             msg.setText("phone number length must not greater than 12")
             msg.visibility = View.VISIBLE

         }
         else{
             msg.setText("")
             msg.visibility = View.GONE

         }

     }

     fun Password(value: String, msg: TextView){
         if(value.length==0){
             msg.setText("password input is empty")
             msg.visibility = View.VISIBLE


         }
         else if(!PASSWORD_PATTERN.matcher(value).matches()){

             msg.setText(" Password input field is not valid ")
             msg.visibility = View.VISIBLE

         }
         else if (value.length>=8){
             msg.setText("password length must not greater than 8")
             msg.visibility = View.VISIBLE

         }
         else{
             msg.setText("")
             msg.visibility = View.GONE

         }
     }


    fun EmailLogin(value: String,msg:TextView, mErrorMessage:TextView,mBackground:RelativeLayout):Boolean{
        if (value.length==0){
            msg.setText("Email  Address input field is empty")
            msg.visibility = View.VISIBLE
            mBackground.visibility = View.VISIBLE
            mErrorMessage.setText("Email and Password is not valid")
            mBackground.setBackgroundResource(R.drawable.background_error)
          return false
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            msg.setText("Email Address input field is not valid")
            msg.visibility = View.VISIBLE
            mBackground.visibility = View.VISIBLE
            mErrorMessage.setText("Email and Password is not valid")
            mBackground.setBackgroundResource(R.drawable.background_error)
      return false
        }
        else{
            msg.setText("")
            msg.visibility = View.GONE
            mBackground.visibility = View.VISIBLE
            mErrorMessage.setText("")
            mBackground.setBackgroundResource(R.drawable.drawable_back)
       return true
        }
        return  true
    }
    fun PasswordLogin(value: String,msg:TextView, mErrorMessage:TextView,mBackground:RelativeLayout):Boolean{
        if (value.length==0){
            msg.setText("Password input field is empty")
            msg.visibility = View.VISIBLE
            mBackground.visibility = View.VISIBLE
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
        else if (value.length<=6){
            msg.setText("password length must not less than 6")
            msg.visibility = View.VISIBLE
            mBackground.visibility = View.VISIBLE
            mErrorMessage.setText("UserName and Password is not valid")
            mBackground.setBackgroundResource(R.drawable.background_error)
          return false
        }
        else{
            msg.setText("")
            msg.visibility = View.GONE
            mBackground.visibility = View.VISIBLE
            mErrorMessage.setText("")
            mBackground.setBackgroundResource(R.drawable.drawable_back)
return true        }
    return  true
    }


 }