package com.example.myapplication.ValidationExt

import android.app.Activity
import android.util.Patterns
import android.view.View
import android.widget.TextView

 object Validations : Activity() {

    fun required(value: String, msg:TextView){
        if(value.length ==0){
            msg.visibility = View.VISIBLE
            msg.setText("Full name input is null")
        }

        else{
            msg.setText("")
            msg.visibility = View.GONE

        }

    }
     fun Email(value: String,msg:TextView ){
         if (value.length==0){
             msg.setText("email input is empty")
             msg.visibility = View.VISIBLE

         }
         else if(!Patterns.EMAIL_ADDRESS.matcher(value).matches()){
             msg.setText("Email Address is not valid")
             msg.visibility = View.VISIBLE

         }
         else{
             msg.setText("")
             msg.visibility = View.GONE

         }
     }

     fun CheckPhoneNumber(value: String,msg: TextView){
         if (value.length==0){
             msg.setText("phone number input is empty")
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
         else if (value.length>=7){
             msg.setText("password length must not greater than 7")
             msg.visibility = View.VISIBLE

         }
         else{
             msg.setText("")
             msg.visibility = View.GONE

         }
     }


 }