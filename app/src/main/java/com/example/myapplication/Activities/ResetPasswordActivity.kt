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
import com.airbnb.lottie.LottieAnimationView
import com.example.myapplication.Fragments.HomeFragment
import com.example.myapplication.LoginActivity
import com.example.myapplication.R
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.extension.androidextention.initLoader
import com.example.myapplication.util.SavedPrefManager
import com.example.sleeponcue.extension.diasplay_toast
import okhttp3.ResponseBody
import org.w3c.dom.Text
import java.lang.Exception

class ResetPasswordActivity : AppCompatActivity() {
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
    lateinit var lottie: LottieAnimationView
    lateinit var serviceManager: ServiceManager
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
        lottie = findViewById(R.id.loader)

        serviceManager = ServiceManager(mContext)

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
        if (androidextention.isOnline(this)) {
//            androidextention.showProgressDialog(this)
            lottie.initLoader(true)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(object : ApiResponseListener<Responce> {
                    override fun onApiSuccess(response: Responce, apiName: String?) {
//                        androidextention.disMissProgressDialog(this@ResetPasswordActivity)
                        lottie.initLoader(false)
                        if (response.responseCode == "200") {
                            Toast.makeText(
                                this@ResetPasswordActivity,
                                response.responseMessage,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            val intent =
                                Intent(this@ResetPasswordActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finishAffinity()
                        }
                    }

                    override fun onApiErrorBody(response: String?, apiName: String?) {
//                        androidextention.disMissProgressDialog(this@ResetPasswordActivity)
                        lottie.initLoader(false)
                        Toast.makeText(
                            this@ResetPasswordActivity,
                            "Something Went Wrong " + response.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onApiFailure(failureMessage: String?, apiName: String?) {
//                        androidextention.disMissProgressDialog(this@ResetPasswordActivity)
                        lottie.initLoader(false)
                        Toast.makeText(
                            this@ResetPasswordActivity,
                            "Server not responding " + failureMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }, "RestPassword", this)

            val apiRequest = Api_Request()
            apiRequest.newPassword = new_pass
            val token = SavedPrefManager.getStringPreferences(this, SavedPrefManager.TOKEN)!!
            try {
                serviceManager.userRestPassword(
                    callBack, apiRequest, token

                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            diasplay_toast("Please check internet connection.")
        }
    }
}