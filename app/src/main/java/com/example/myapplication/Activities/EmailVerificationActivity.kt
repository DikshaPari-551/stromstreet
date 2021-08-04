package com.example.myapplication.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.myapplication.R

class EmailVerificationActivity : AppCompatActivity() {
    lateinit var et1:EditText
    lateinit var backgroundd: RelativeLayout
    lateinit var error_text: TextView
    lateinit var et2:EditText
    lateinit var et3:EditText
    lateinit var et4:EditText
    lateinit var submit:RelativeLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_verification)

        submit=findViewById(R.id.layout_submit)
        backgroundd=findViewById(R.id.backgroundd_error)
        error_text=findViewById(R.id.textView_errorrr)
    et1=findViewById(R.id.et_1)
        et2=findViewById(R.id.et_2)
        et3=findViewById(R.id.et_3)
        et4=findViewById(R.id.et_4)
        submit.setOnClickListener{
            var intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
            var ett1=et1.text.toString()
            var ett2=et2.text.toString()
            var ett3=et3.text.toString()
            var ett4=et4.text.toString()
            if(ett1!="1"||ett2!="2"||ett3!="3"||ett4!="4"){
                backgroundd.setBackgroundResource(R.drawable.background_error)
                error_text.setText("Invalid OTP")

            }else{
                backgroundd.setBackgroundResource(R.drawable.drawable_back)
                error_text.setText("")

            }
        }

    }
}