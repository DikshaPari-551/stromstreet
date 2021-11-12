package com.example.myapplication.Activities

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.WindowManager
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.LoginActivity
import com.example.myapplication.R
import com.example.myapplication.ValidationExt.Validations
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.sleeponcue.extension.diasplay_toast
import okhttp3.ResponseBody
import java.lang.Exception

class ForgotPasswordActivity : AppCompatActivity() , ApiResponseListener<Responce> {
    lateinit var send_otp: RelativeLayout
    lateinit var forget_back_error: RelativeLayout
    lateinit var forget_email: EditText
    lateinit var mErrorTextForgotPassword:TextView
    lateinit var forget_text_error: TextView
    var mContext: Context = this
    lateinit var email : String

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
            email = forget_email.text.toString().trim()
            Validations.EmailLogin(email,mErrorTextForgotPassword,forget_text_error,forget_back_error)
            if( Validations.EmailLogin(email,mErrorTextForgotPassword,forget_text_error,forget_back_error)== true){
                forgetpassword()

//                var intent = Intent(this, EmailVerificationActivity::class.java)
//                startActivity(intent)
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

    private fun forgetpassword() {
        androidextention.showProgressDialog(this)
        val serviceManager = ServiceManager(mContext)
        val callBack: ApiCallBack<Responce> =
            ApiCallBack<Responce>(this, "ForgetPassword", mContext)
        val apiRequest = Api_Request()
        apiRequest.email = email
//        apiRequest.email = intent.getStringExtra("VerifyEmail")
//        apiRequest.confirm_password = resetConfirmPassword.getText().toString().trim()

        try {
            serviceManager.forget(callBack, apiRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onApiSuccess(response: Responce, apiName: String?) {
        if (androidextention.isOnline(this)) {
            if (response.responseCode == "200") {
                Toast.makeText(
                    this,
                    response.responseMessage ,
                    Toast.LENGTH_LONG
                ).show()
                var intent = Intent(this, EmailVerificationActivity::class.java)
                intent.putExtra("FORGOTACTIVITY", "forgotactivity")
                intent.putExtra("EMAIL", response.result.email)
                startActivity(intent)
            } else {
                Toast.makeText(this, response.responseMessage, Toast.LENGTH_LONG).show()
            }
        } else {

            diasplay_toast("Please check internet connection.")
        }
    }

    override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
        Toast.makeText(this, "Something Went Wrong" + response.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        Toast.makeText(this, "Server not responding" + failureMessage, Toast.LENGTH_LONG).show()
    }
}