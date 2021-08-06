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
import java.util.regex.Pattern

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
    lateinit var mLoginEmail:TextView
    lateinit var mLoginPassword:TextView
    private val PASSWORD_PATTERN =

        "^" +
                "(?=.*[@#$%^&+=])" +  // at least 1 special character
                "(?=\\S+$)" +  // no white spaces
                ".{4,}" +  // at least 4 characters
                "$"
 var count=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mEmailText=findViewById(R.id.email_text)
        mBackerror=findViewById(R.id.back_error)
        text_sign_up=findViewById(R.id.text_sign_up)
        mLoginPassword=findViewById(R.id.password_login_up)
        text_forget=findViewById(R.id.text_forget_password)
        mLoginEmail=findViewById(R.id.username_login_up)
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
            var intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }


    }

    private fun validation() {
        var email=mEmailText.text.toString()
        var pasword=mPassword.text.toString()

        if(email.length == 0 ){
            mBackerror.visibility = View.VISIBLE
            merror.setText("UserName and Password is not valid")
            mBackerror.setBackgroundResource(R.drawable.background_error)
            mLoginEmail.setText(" Input email field is empty ")
            mLoginEmail.visibility = View.VISIBLE

          }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mBackerror.visibility = View.VISIBLE
            merror.setText("UserName and Password is not valid")
            mBackerror.setBackgroundResource(R.drawable.background_error)

            mLoginEmail.setText(" Input email or username  is not valid ")
            mLoginEmail.visibility = View.VISIBLE


        }


         if(pasword.length==0){
            mBackerror.visibility = View.VISIBLE
            merror.setText("UserName and Password is not valid")
            mBackerror.setBackgroundResource(R.drawable.background_error)
           mLoginPassword .setText(" Input Password field is empty ")
             mLoginPassword.visibility = View.VISIBLE

         }
        else if(pasword.length>=8){
            mBackerror.visibility = View.VISIBLE
            merror.setText("UserName and Password is not valid")
            mBackerror.setBackgroundResource(R.drawable.background_error)
            mLoginPassword.setText(" Input password length will not be greater than 8")
             mLoginPassword.visibility = View.VISIBLE

         }

        else if(!pasword.equals(PASSWORD_PATTERN)){
             mBackerror.visibility = View.VISIBLE
             merror.setText("UserName and Password is not valid")
             mBackerror.setBackgroundResource(R.drawable.background_error)
             mLoginPassword.setText(" Input Password is not valid ")
             mLoginPassword.visibility = View.VISIBLE

         }
        else{
            merror.setText("")
            mBackerror.setBackgroundResource(R.drawable.drawable_back)
            mBackerror.visibility = View.GONE

            LoginFlag.setLoginFlag(true)

        }
    }
}