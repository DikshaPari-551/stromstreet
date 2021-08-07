package com.example.myapplication.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class EmailVerificationActivity : AppCompatActivity() {
    lateinit var et1: EditText
    lateinit var backgroundd: RelativeLayout
    lateinit var error_text: TextView
    lateinit var et2: EditText
    lateinit var et3: EditText
    lateinit var et4: EditText

    lateinit var submit: RelativeLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_verification)

        submit = findViewById(R.id.layout_submit)
        backgroundd = findViewById(R.id.backgroundd_error)
        error_text = findViewById(R.id.textView_errorrr)
        et1 = findViewById(R.id.et_1)
        et2 = findViewById(R.id.et_2)
        et3 = findViewById(R.id.et_3)
        et4 = findViewById(R.id.et_4)

        et1.addTextChangedListener(GenericTextWatcher(et1, et2))
        et2.addTextChangedListener(GenericTextWatcher(et2, et3))
        et3.addTextChangedListener(GenericTextWatcher(et3, et4))
        et4.addTextChangedListener(GenericTextWatcher(et4, null))


        et1.setOnKeyListener(GenericKeyEvent(et1, null))
        et2.setOnKeyListener(GenericKeyEvent(et2, et1))
        et3.setOnKeyListener(GenericKeyEvent(et3, et2))
        et4.setOnKeyListener(GenericKeyEvent(et4, et3))


        submit.setOnClickListener {

            var ett1 = et1.text.toString()
            var ett2 = et2.text.toString()
            var ett3 = et3.text.toString()
            var ett4 = et4.text.toString()
            if (ett1.length == 0) {
                backgroundd.visibility = View.VISIBLE

                backgroundd.setBackgroundResource(R.drawable.background_error)
                error_text.setText("Invalid OTP")

            } else if (ett2.length == 0) {
                backgroundd.visibility = View.VISIBLE

                backgroundd.setBackgroundResource(R.drawable.background_error)
                error_text.setText("Invalid OTP")

            } else if (ett3.length == 0) {
                backgroundd.visibility = View.VISIBLE

                backgroundd.setBackgroundResource(R.drawable.background_error)
                error_text.setText("Invalid OTP")
            }
            else if (ett4.length == 0) {
                backgroundd.visibility = View.VISIBLE

                backgroundd.setBackgroundResource(R.drawable.background_error)
                error_text.setText("Invalid OTP")
            }
            else {
                backgroundd.setBackgroundResource(R.drawable.drawable_back)
                error_text.setText("")
                backgroundd.visibility = View.GONE
                var intent = Intent(this, ResetPasswordActivity::class.java)
                startActivity(intent)

            }


        }

    }
}