package com.stormstreet.myapplication.Activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.airbnb.lottie.LottieAnimationView
import com.stormstreet.myapplication.R
import com.stormstreet.myapplication.entity.ApiCallBack
import com.stormstreet.myapplication.entity.Request.Api_Request
import com.stormstreet.myapplication.entity.Response.Responce
import com.stormstreet.myapplication.entity.Service_Base.ApiResponseListener
import com.stormstreet.myapplication.entity.Service_Base.ServiceManager
import com.stormstreet.myapplication.extension.androidextention
import com.stormstreet.myapplication.extension.androidextention.initLoader
import com.stormstreet.sleeponcue.extension.diasplay_toast
import java.lang.Exception

class ChangePassword : AppCompatActivity() {
    lateinit var old_password: EditText
    lateinit var layout_submitt: LinearLayout
    lateinit var background: RelativeLayout
    lateinit var error_text: TextView
    lateinit var oldPasswordErrorText: TextView
    lateinit var mEyeImagePass: ImageView
    lateinit var newPasswordErrorText: TextView
    lateinit var oldPass: String
    lateinit var newPass: String
    lateinit var imgnewpass: ImageView
    var passwordNotVisible = 0
    lateinit var new_passeord: EditText
    lateinit var lottie : LottieAnimationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.black)
        }
        imgnewpass = findViewById(R.id.eye_img_reset_new_pass)
        mEyeImagePass = findViewById(R.id.eye_img_reset_pss)
        layout_submitt = findViewById(R.id.layout_submittt)
//        background = findViewById(R.id.forget_back_error)
//        error_text = findViewById(R.id.forget_text_error)
        old_password = findViewById(R.id.old_password)
        new_passeord = findViewById(R.id.changepassword)
        oldPasswordErrorText = findViewById(R.id.reset_Password_errtext)
        newPasswordErrorText = findViewById(R.id.reset_re_neterPassword_errtext)
        lottie = findViewById(R.id.loader)


        imgnewpass.setOnClickListener {
            if (passwordNotVisible == 0) {
                old_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                imgnewpass.setImageDrawable(resources.getDrawable(R.drawable.eye_login))
                passwordNotVisible = 1


            } else if (passwordNotVisible == 1) {
                old_password.transformationMethod = PasswordTransformationMethod.getInstance()
                imgnewpass.setImageDrawable(resources.getDrawable(R.drawable.eye_img))
                passwordNotVisible = 0
            } else {
                old_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                imgnewpass.setImageDrawable(resources.getDrawable(R.drawable.eye_login))
                passwordNotVisible = 1
            }
        }
        mEyeImagePass.setOnClickListener {
            if (passwordNotVisible == 0) {
                new_passeord.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                mEyeImagePass.setImageDrawable(resources.getDrawable(R.drawable.eye_login))
                passwordNotVisible = 1


            } else if (passwordNotVisible == 1) {
                new_passeord.transformationMethod = PasswordTransformationMethod.getInstance()
                mEyeImagePass.setImageDrawable(resources.getDrawable(R.drawable.eye_img))
                passwordNotVisible = 0
            } else {
                new_passeord.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                mEyeImagePass.setImageDrawable(resources.getDrawable(R.drawable.eye_login))
                passwordNotVisible = 1
            }
        }

        layout_submitt.setOnClickListener {
            validation()
        }
    }

    private fun validation() {
        oldPass = old_password.text.toString()
        newPass = new_passeord.text.toString()

        if (old_password.text.isEmpty()) {
            oldPasswordErrorText.setText("*Please enter old password.")
            oldPasswordErrorText.visibility = View.VISIBLE
        } else if (new_passeord.text.isEmpty()) {
            oldPasswordErrorText.visibility = View.GONE
            newPasswordErrorText.setText("*Please enter new password.")
            newPasswordErrorText.visibility = View.VISIBLE
        } else if (new_passeord.text.length < 6) {
            oldPasswordErrorText.visibility = View.GONE
            newPasswordErrorText.setText("*Please enter new password more than 6-digits.")
            newPasswordErrorText.visibility = View.VISIBLE
        } else {
            oldPasswordErrorText.visibility = View.GONE
            newPasswordErrorText.visibility = View.GONE
            changePassword()
        }
    }

    private fun changePassword() {
        if (androidextention.isOnline(this)) {
//            androidextention.showProgressDialog(this)
            lottie.initLoader(true)
            val serviceManager = ServiceManager(this)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(object : ApiResponseListener<Responce> {
                    override fun onApiSuccess(response: Responce, apiName: String?) {
//                        androidextention.disMissProgressDialog(this@ChangePassword)
                        lottie.initLoader(false)
                        if (response.responseCode == "200") {
                            Toast.makeText(
                                this@ChangePassword,
                                response.responseMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this@ChangePassword,
                                response.responseMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onApiErrorBody(response: String?, apiName: String?) {
//                        androidextention.disMissProgressDialog(this@ChangePassword)
                        lottie.initLoader(false)
                        Toast.makeText(
                            this@ChangePassword,
                            "Something Went Wrong" + response.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
//                        androidextention.disMissProgressDialog(this@ChangePassword)
                        lottie.initLoader(false)
                        Toast.makeText(
                            this@ChangePassword,
                            "Server not responding" + failureMessage,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                }, "ResetPassword", this)

            val apiRequest = Api_Request()
            apiRequest.oldPassword = oldPass
            apiRequest.newPassword = newPass

            try {
                serviceManager.userChangePassword(callBack, apiRequest)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            diasplay_toast("Please check internet connection.")
        }
    }
}