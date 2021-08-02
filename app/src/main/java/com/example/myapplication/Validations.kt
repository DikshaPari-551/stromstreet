package com.example.myapplication

import android.app.Activity
import android.util.Patterns
import android.widget.TextView

 object Validations : Activity() {

    fun required(value: String, msg:TextView){
        if(value.length ==0){
            msg.setText("Full name input is null")
        }

        else{
            msg.setText("")
        }

    }
     fun Email(value: String,msg:TextView ){
         if (value.length==0){
             msg.setText("email input is empty")
         }
         else if(!Patterns.EMAIL_ADDRESS.matcher(value).matches()){
             msg.setText("Email Address is not valid")
         }
         else{
             msg.setText("")
         }
     }

     fun CheckPhoneNumber(value: String,msg: TextView){
         if (value.length==0){
             msg.setText("phone number input is empty")
         }
         else if (value.length>=12){
             msg.setText("phone number length must not greater than 12")
         }
         else{
             msg.setText("")
         }

     }

     fun Password(value: String, msg: TextView){
         if(value.length==0){
             msg.setText("password input is empty")

         }
         else if (value.length>=7){
             msg.setText("password length must not greater than 7")
         }
         else{
             msg.setText("")
         }
     }


 }