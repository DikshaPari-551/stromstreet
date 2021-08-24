package com.example.myapplication.Activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.WindowManager
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.ValidationExt.Validations

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var send_otp: RelativeLayout
    lateinit var forget_back_error: RelativeLayout
    lateinit var forget_email: EditText
    lateinit var mErrorTextForgotPassword:TextView
    lateinit var forget_text_error: TextView
//    lateinit var mErrorTextForgotPassword : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
    if (Build.VERSION.SDK_INT >= 21) {
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = resources.getColor(R.color.black)
    }
        forget_back_error = findViewById(R.id.forget_back_error)
        forget_text_error = findViewById(R.id.forget_text_error)
        forget_email = findViewById(R.id.forget_email_text)
        mErrorTextForgotPassword=findViewById(R.id.email_forgot_password)
        send_otp = findViewById(R.id.layout_otp)
//        mErrorTextForgotPassword = findViewById(R.id.email_forgot_password)

        send_otp.setOnClickListener {
            var email = forget_email.text.toString().trim()
            Validations.EmailLogin(email,mErrorTextForgotPassword,forget_text_error,forget_back_error)
            if( Validations.EmailLogin(email,mErrorTextForgotPassword,forget_text_error,forget_back_error)== true){
                var intent = Intent(this, EmailVerificationActivity::class.java)
                startActivity(intent)
            }
//            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                forget_back_error.setBackgroundResource(R.drawable.background_error)
//                forget_text_error.setText("please enter your register email id.")
//            } else {
//
//                forget_text_error.setText("")
//                forget_back_error.setBackgroundResource(R.drawable.drawable_back)

//            }
        }
    }
}