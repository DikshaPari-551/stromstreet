package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Activities.ForgotPasswordActivity
import com.example.myapplication.Activities.SignUpActivity

class LoginActivity : AppCompatActivity() {
    lateinit var mEmailText:EditText
    lateinit var mPassword:EditText
    lateinit var mLayout_Login:LinearLayout
    lateinit var text_sign_up:TextView
    lateinit var mBackerror:RelativeLayout
    lateinit var text_forget:TextView
    lateinit var merror:TextView
    lateinit var eyeImg:ImageView
    private var passwordNotVisible = 0
 var count=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mEmailText=findViewById(R.id.email_text)
        mBackerror=findViewById(R.id.back_error)
        text_sign_up=findViewById(R.id.text_sign_up)
        text_forget=findViewById(R.id.text_forget_password)
        mPassword=findViewById(R.id.password_text)
        merror=findViewById(R.id.text_error)
        mLayout_Login=findViewById(R.id.layout_login)
        eyeImg=findViewById(R.id.eye_img)
eyeImg.setOnClickListener{
    if(passwordNotVisible==0){
        mPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        eyeImg.setImageDrawable(resources.getDrawable(R.drawable.eye_open_img))
        passwordNotVisible = 1


    }
        else if(passwordNotVisible==1){
        mPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        eyeImg.setImageDrawable(resources.getDrawable(R.drawable.eye_img))
        passwordNotVisible = 0
    }
    else{
        mPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        eyeImg.setImageDrawable(resources.getDrawable(R.drawable.eye_open_img))
        passwordNotVisible = 1
    }
}
        text_forget.setOnClickListener{
            var intent=Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
        text_sign_up.setOnClickListener{
            var intent=Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        mLayout_Login.setOnClickListener{
           validation()

        }


    }

    private fun validation() {
        var email=mEmailText.text.toString()
        var pasword=mPassword.text.toString()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || pasword.length>=10){
            mBackerror.visibility = View.VISIBLE
        merror.setText("UserName and Password is not valid")
            mBackerror.setBackgroundResource(R.drawable.background_error)
          }
        else{
            merror.setText("")
            mBackerror.setBackgroundResource(R.drawable.drawable_back)
            mBackerror.visibility = View.GONE

            LoginFlag.setLoginFlag(true)
            var intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}