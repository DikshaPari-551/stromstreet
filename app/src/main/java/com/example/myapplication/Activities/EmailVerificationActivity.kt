package com.example.myapplication.Activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.LoginActivity
import com.example.myapplication.R
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.SavedPrefManager
import com.example.sleeponcue.extension.diasplay_toast
import okhttp3.ResponseBody
import java.lang.Exception


class EmailVerificationActivity : AppCompatActivity(), ApiResponseListener<Responce> {
    lateinit var et1: EditText
    lateinit var backgroundd: RelativeLayout
    lateinit var error_text: TextView

    var mContext: Context = this
    var Otptext: String? = null
    var id = ""
    var nameapi = ""

    lateinit var et2: EditText
    lateinit var et3: EditText
    lateinit var timer_text: TextView
    lateinit var otp: TextView
    lateinit var et4: EditText
    lateinit var text_login: TextView

    lateinit var submit: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_verification)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.black)
        }
        otp = findViewById(R.id.otp)
        submit = findViewById(R.id.layout_submit)
        backgroundd = findViewById(R.id.backgroundd_error)
        timer_text = findViewById(R.id.timer_text)
        error_text = findViewById(R.id.textView_errorrr)
        et1 = findViewById(R.id.et_1)
        et2 = findViewById(R.id.et_2)
        et3 = findViewById(R.id.et_3)
        et4 = findViewById(R.id.et_4)
        text_login = findViewById(R.id.text_login)

        et1.addTextChangedListener(GenericTextWatcher(et1, et2))
        et2.addTextChangedListener(GenericTextWatcher(et2, et3))
        et3.addTextChangedListener(GenericTextWatcher(et3, et4))
        et4.addTextChangedListener(GenericTextWatcher(et4, null))


        et1.setOnKeyListener(GenericKeyEvent(et1, null))
        et2.setOnKeyListener(GenericKeyEvent(et2, et1))
        et3.setOnKeyListener(GenericKeyEvent(et3, et2))
        et4.setOnKeyListener(GenericKeyEvent(et4, et3))
        id = intent.getStringExtra("EMAIL").toString()
        object : CountDownTimer(180000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds = (millisUntilFinished / 1000).toInt()
                val hours = seconds / (60 * 60)
                val tempMint = seconds - hours * 60 * 60
                val minutes = tempMint / 60
                seconds = tempMint - minutes * 60
                timer_text.setText(
                    String.format(
                        "%02d",
                        minutes
                    ) + ":" + String.format("%02d", seconds) + " sec "
                )
            }

            override fun onFinish() {
                timer_text.setText("Session Expired")
            }
        }.start()

        text_login.setOnClickListener {
            resendOtp()
        }


        submit.setOnClickListener {

            var ett1 = et1.text.toString()
            var ett2 = et2.text.toString()
            var ett3 = et3.text.toString()
            var ett4 = et4.text.toString()
            Otptext = ett1 + ett2 + ett3 + ett4.trim()
            if (ett1.length == 0) {
                otp.visibility = View.VISIBLE

                otp.setText(" *Invalid OTP")

            } else if (ett2.length == 0) {
                otp.visibility = View.VISIBLE

//                backgroundd.setBackgroundResource(R.drawable.background_error)
                otp.setText("*Invalid OTP")

            } else if (ett3.length == 0) {
                otp.visibility = View.VISIBLE

                otp.setText("Invalid OTP")
            } else if (ett4.length == 0) {
                otp.visibility = View.VISIBLE

                otp.setText("Invalid OTP")
            } else {

                otp.visibility = View.GONE
                verifyOtp()
                var intent = Intent(this, ResetPasswordActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun resendOtp() {
        nameapi = "ResendOtp"

        androidextention.showProgressDialog(this)
        val serviceManager = ServiceManager(mContext)
        val callBack: ApiCallBack<Responce> =
            ApiCallBack<Responce>(this, "ResendOtp", mContext)
        val apiRequest = Api_Request()
        apiRequest.email = id
//        apiRequest.email = intent.getStringExtra("VerifyEmail")
//        apiRequest.confirm_password = resetConfirmPassword.getText().toString().trim()

        try {
            serviceManager.forget(callBack, apiRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }    }

    private fun verifyOtp() {
        nameapi = "VerifyOtp"

        androidextention.showProgressDialog(this)
        val serviceManager = ServiceManager(mContext)
        val callBack: ApiCallBack<Responce> =
            ApiCallBack<Responce>(this, "VerifyOtp", mContext)
        val apiRequest = Api_Request()

        apiRequest.email = id
        apiRequest.otp = Otptext?.toInt()
        try {
            serviceManager.vOtp(callBack, apiRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onApiSuccess(response: Responce, apiName: String?) {
        if (androidextention.isOnline(this)) {
            if (nameapi.equals("VerifyOtp")) {
                if (response.responseCode == "200") {
                    Log.w("", "Response" + response.result)
                    androidextention.disMissProgressDialog(this)
                    SavedPrefManager.saveStringPreferences(this,SavedPrefManager.TOKEN,response.result.token)
                    Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                    var intent = Intent(this, LoginActivity::class.java)
//                    intent.putExtra("VerifyEmail", id)
                    startActivity(intent)
                    finish()
                }
            }
            else if (nameapi.equals("ResendOtp")) {
                if (response.responseCode == "200") {
                    Toast.makeText(this, "Success"+response.result.otp, Toast.LENGTH_LONG).show()

                }

            }
        }
    }

    override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
        Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        Toast.makeText(this, "fail", Toast.LENGTH_LONG).show()
    }


}
