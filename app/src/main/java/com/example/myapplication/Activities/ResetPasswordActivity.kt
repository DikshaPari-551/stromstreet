package com.example.myapplication.Activities

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.example.myapplication.Fragments.HomeFragment
import com.example.myapplication.LoginActivity
import com.example.myapplication.R
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.SavedPrefManager
import okhttp3.ResponseBody
import org.w3c.dom.Text
import java.lang.Exception

class ResetPasswordActivity : AppCompatActivity(), ApiResponseListener<Responce> {
    var mContext: Context = this

    lateinit var new_password: EditText
    lateinit var layout_submitt: LinearLayout
    lateinit var background: RelativeLayout
    lateinit var error_text: TextView
    lateinit var resetPasswordErrText: TextView
    lateinit var mEyeImagePass: ImageView
    lateinit var reEnterPassword: TextView
    lateinit var new_pass: String
    lateinit var re_enter_pass: String
    lateinit var imgnewpass: ImageView
    var passwordNotVisible = 0
    lateinit var re_enter_passeord: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.black)
        }
        imgnewpass = findViewById(R.id.eye_img_reset_new_pass)
        mEyeImagePass = findViewById(R.id.eye_img_reset_pss)
        layout_submitt = findViewById(R.id.layout_submittt)
        background = findViewById(R.id.forget_back_error)
        error_text = findViewById(R.id.forget_text_error)
        new_password = findViewById(R.id.new_password)
        re_enter_passeord = findViewById(R.id.Re_enter_password)
        resetPasswordErrText = findViewById(R.id.reset_Password_errtext)
        reEnterPassword = findViewById(R.id.reset_re_neterPassword_errtext)

        imgnewpass.setOnClickListener {
            if (passwordNotVisible == 0) {
                new_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                imgnewpass.setImageDrawable(resources.getDrawable(R.drawable.eye_login))
                passwordNotVisible = 1


            } else if (passwordNotVisible == 1) {
                new_password.transformationMethod = PasswordTransformationMethod.getInstance()
                imgnewpass.setImageDrawable(resources.getDrawable(R.drawable.eye_img))
                passwordNotVisible = 0
            } else {
                new_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                imgnewpass.setImageDrawable(resources.getDrawable(R.drawable.eye_login))
                passwordNotVisible = 1
            }
        }
        mEyeImagePass.setOnClickListener {
            if (passwordNotVisible == 0) {
                re_enter_passeord.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                mEyeImagePass.setImageDrawable(resources.getDrawable(R.drawable.eye_login))
                passwordNotVisible = 1


            } else if (passwordNotVisible == 1) {
                re_enter_passeord.transformationMethod = PasswordTransformationMethod.getInstance()
                mEyeImagePass.setImageDrawable(resources.getDrawable(R.drawable.eye_img))
                passwordNotVisible = 0
            } else {
                re_enter_passeord.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                mEyeImagePass.setImageDrawable(resources.getDrawable(R.drawable.eye_login))
                passwordNotVisible = 1
            }
        }

        layout_submitt.setOnClickListener {
            new_pass = new_password.text.toString()
            re_enter_pass = re_enter_passeord.text.toString()
            if (new_pass.length < 6) {


                resetPasswordErrText.setText("*Please enter new password more than 6-digits.")
                resetPasswordErrText.visibility = View.VISIBLE

            } else if (!new_pass.equals(re_enter_pass)) {

                //    background.setBackgroundResource(R.drawable.background_error)
                //         error_text.setText("*Re-enter password is not equal to new password.")
                reEnterPassword.setText("*Both password should match.")
                reEnterPassword.visibility = View.VISIBLE
                resetPasswordErrText.setText("")

            } else {
                resetPasswordErrText.setText("")
                reEnterPassword.setText("")
                resetPasswordErrText.visibility = View.GONE
                reEnterPassword.visibility = View.GONE
                resetPassword()

            }

        }
    }

    private fun resetPassword() {
        androidextention.showProgressDialog(this)
        val serviceManager = ServiceManager(mContext)
//        val Token = SavedPrefManager.getStringPreferences(this,SavedPrefManager.TOKEN).toString()
        var token: String =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYxNmViNDcwZDg1ZGY5MmU0N2U5NmEyNiIsImVtYWlsIjoiYWpheUBnbWFpbC5jb20iLCJ1c2VyVHlwZSI6IlVzZXIiLCJpYXQiOjE2MzQ3MTMzODcsImV4cCI6MTYzNDc5OTc4N30.A2DKYuca6WEGtpq7EIJS6knyjTvIEZkhr4MTcLEeXmg"
        val callBack: ApiCallBack<Responce> =
            ApiCallBack<Responce>(this, "ResetPassword", mContext)
        val apiRequest = Api_Request()
        apiRequest.newPassword = new_pass
//        apiRequest.new_password = resetNewPassword.getText().toString().trim()
//        apiRequest.confirm_password = resetConfirmPassword.getText().toString().trim()

        try {
            serviceManager.reset(callBack, apiRequest, token)
            Log.d("token", token)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onApiSuccess(response: Responce, apiName: String?) {
        if (response.responseCode == "200") {
            androidextention.disMissProgressDialog(this)
            Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }
    }

    override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()

    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        Toast.makeText(this, "Failure", Toast.LENGTH_LONG).show()

    }
}