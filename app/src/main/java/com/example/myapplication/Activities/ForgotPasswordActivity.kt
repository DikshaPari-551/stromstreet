package com.example.myapplication.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.ValidationExt.Validations

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var send_otp: RelativeLayout
    lateinit var forget_back_error: RelativeLayout
    lateinit var forget_email: EditText
    lateinit var forget_text_error: TextView
    lateinit var mErrorTextForgotPassword : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        forget_back_error = findViewById(R.id.forget_back_error)
        forget_text_error = findViewById(R.id.forget_text_error)
        forget_email = findViewById(R.id.forget_email_text)
        send_otp = findViewById(R.id.layout_otp)
        mErrorTextForgotPassword = findViewById(R.id.email_forgot_password)

        send_otp.setOnClickListener {
            var email = forget_email.text.toString()
            Validations.EmailLogin(email,mErrorTextForgotPassword,forget_text_error,forget_back_error)
            if( Validations.EmailLogin(email,mErrorTextForgotPassword,forget_text_error,forget_back_error)== true){
                var intent = Intent(this, EmailVerificationActivity::class.java)
                startActivity(intent)
            }
        }
    }
}