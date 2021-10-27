package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.Debug.getLocation
import com.example.myapplication.Activities.EmailVerificationActivity
import com.example.myapplication.Activities.ForgotPasswordActivity
import com.example.myapplication.Activities.SignUpActivity
import com.example.myapplication.ValidationExt.Validations
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.SavedPrefManager
import com.example.sleeponcue.extension.diasplay_toast
import okhttp3.ResponseBody
import java.util.regex.Pattern


class
LoginActivity : AppCompatActivity(), ApiResponseListener<Responce> {
    lateinit var mEmailText: EditText
    lateinit var check_login: CheckBox
    lateinit var check_text_login: TextView
    var mContext: Context = this
    private val LOCATION_PERMISSION_REQ_CODE = 1000;
    var savedPrefManager = SavedPrefManager
    lateinit var mPassword: EditText
    lateinit var mLayout_Login: LinearLayout
    lateinit var text_sign_up: TextView
    lateinit var mBackerror: RelativeLayout
    lateinit var text_forget: TextView
    lateinit var merror: TextView
    lateinit var eyeImg: ImageView
    private var passwordNotVisible = 0
    lateinit var mLoginEmail: TextView
    lateinit var mLoginPassword: TextView
    lateinit var uemail: String
    lateinit var upassword: String
    lateinit var deviceToken: String
    private lateinit var email: EditText
    private lateinit var password: EditText

    private val PASSWORD_PATTERN =
        Pattern.compile(
            "^" +
                    "(?=.*[@#$%^&+=])" +  // at least 1 special character
                    "(?=\\S+$)" +  // no white spaces
                    ".{4,}" +  // at least 4 characters
                    "$"
        )
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.black)
        }

        initializedControl()


        check_login = findViewById(R.id.check_login)
        check_text_login = findViewById(R.id.check_text_login)
        mEmailText = findViewById(R.id.email_text)
        mBackerror = findViewById(R.id.back_error)
        text_sign_up = findViewById(R.id.text_sign_up)
        mLoginPassword = findViewById(R.id.password_login_up)
        text_forget = findViewById(R.id.text_forget_password)
        mLoginEmail = findViewById(R.id.username_login_up)
        mPassword = findViewById(R.id.password_text)
        merror = findViewById(R.id.text_error)
        mLayout_Login = findViewById(R.id.layout_login)
        eyeImg = findViewById(R.id.eye_img)


        eyeImg.setOnClickListener {
            if (passwordNotVisible == 0) {
                mPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                eyeImg.setImageDrawable(resources.getDrawable(R.drawable.eye_login))
                passwordNotVisible = 1


            } else if (passwordNotVisible == 1) {
                mPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                eyeImg.setImageDrawable(resources.getDrawable(R.drawable.eye_img))
                passwordNotVisible = 0
            } else {
                mPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                eyeImg.setImageDrawable(resources.getDrawable(R.drawable.eye_login))
                passwordNotVisible = 1
            }
        }
        text_forget.setOnClickListener {
            var intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
        text_sign_up.setOnClickListener {
            var intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        mLayout_Login.setOnClickListener {

            uemail = mEmailText.text.toString().trim()
            upassword = mPassword.text.toString().trim()

            if (Validations.Email(uemail, mLoginEmail) && Validations.PasswordLogin(
                    upassword,
                    mLoginPassword
                ) == true
            ) {
                LogIn()
//                var intent = Intent(applicationContext, MainActivity::class.java)
//
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                SavedPrefManager.saveStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN, "true")
//                this.finish()
//
//                startActivity(intent)
//
//                LoginFlag.setLoginFlag( true)


            }


        }

//    private fun validation() {
//
//        Validations.EmailLogin(email,mLoginEmail,merror,mBackerror)
//        Validations.PasswordLogin(pasword,mLoginPassword,merror,mBackerror)
//
////        var intent=Intent(this, MainActivity::class.java)
////            startActivity(intent)
////             finish()
//
//
////        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()||!PASSWORD_PATTERN.matcher(pasword).matches()){
////
//////        if(email.length == 0 ){
//////            mBackerror.visibility = View.VISIBLE
//////            merror.setText("UserName and Password is not valid")
//////            mBackerror.setBackgroundResource(R.drawable.background_error)
//////            mLoginEmail.setText(" Input email field is empty ")
//////            mLoginEmail.visibility = View.VISIBLE
//////
//////          }
//////        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//////            mBackerror.visibility = View.VISIBLE
//////            merror.setText("UserName and Password is not valid")
//////            mBackerror.setBackgroundResource(R.drawable.background_error)
//////            mLoginEmail.setText(" Input email or username  is not valid ")
//////            mLoginEmail.visibility = View.VISIBLE
//////
//////
//////        }
//////        else{
//////            mLoginEmail.setText(" ")
//////            mLoginEmail.visibility = View.GONE
//////        }
////
////
////          if(pasword.length==0){
////            mBackerror.visibility = View.VISIBLE
////            merror.setText("UserName and Password is not valid")
////            mBackerror.setBackgroundResource(R.drawable.background_error)
////           mLoginPassword .setText(" Input Password field is empty ")
////             mLoginPassword.visibility = View.VISIBLE
////
////         }
////        else if(pasword.length>=8){
////            mBackerror.visibility = View.VISIBLE
////            merror.setText("UserName and Password is not valid")
////            mBackerror.setBackgroundResource(R.drawable.background_error)
////            mLoginPassword.setText(" Input password length will not be greater than 8")
////             mLoginPassword.visibility = View.VISIBLE
////
////         }
////
////        else if(!PASSWORD_PATTERN.matcher(pasword).matches()){
////             mBackerror.visibility = View.VISIBLE
////             merror.setText("UserName and Password is not valid")
////             mBackerror.setBackgroundResource(R.drawable.background_error)
////             mLoginPassword.setText(" Input Password is not valid ")
////             mLoginPassword.visibility = View.VISIBLE
////
////         }
////            else{
////                mLoginPassword.setText("")
////              mLoginPassword.visibility = View.GONE
////          }
////        }
////        else{
////            merror.setText("")
////            mBackerror.setBackgroundResource(R.drawable.drawable_back)
////            mBackerror.visibility = View.GONE
////              mLoginPassword.setText("")
////              mLoginEmail.setText("")
////              mLoginPassword.visibility = View.GONE
////              mLoginEmail.visibility = View.GONE
////
////
////
////             var intent=Intent(this, MainActivity::class.java)
////             startActivity(intent)
////             finish()
////
////
////         }
//    }
    }

    private fun initializedControl() {
        deviceToken =
            Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

//        locationpermission()
    }

//    private fun locationpermission() {

//    }

    private fun LogIn() {
        if (androidextention.isOnline(this)) {
            androidextention.showProgressDialog(this)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(this, "LoginApi", mContext)
            val apiRequest = Api_Request()
            apiRequest.emailUserName = uemail
            apiRequest.password = upassword
            apiRequest.deviceType = "android"
            apiRequest.deviceToken = deviceToken
//            savedPrefManager.saveStringPreferences(
//                this,
//                savedPrefManager.PASSWORD,
//                apiRequest.password
//            )

            try {
                serviceManager.LoginUser(callBack, apiRequest)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            diasplay_toast("Check Your Internet Connection")
        }
    }

    override fun onApiSuccess(response: Responce, apiName: String?) {
        if (response.responseCode == "200") {
            Toast.makeText(this, "success", Toast.LENGTH_LONG).show()
            if (response.result.otpVerification == false)
            {
                var intent = Intent(applicationContext, EmailVerificationActivity::class.java)
                startActivity(intent)
            }
            else if (response.result.otpVerification == true)
            {
                SavedPrefManager.saveStringPreferences(mContext,SavedPrefManager.TOKEN,response.result.token)
                SavedPrefManager.saveStringPreferences(mContext,SavedPrefManager.userName,response.result.userName)
                SavedPrefManager.saveStringPreferences(this,  SavedPrefManager.KEY_IS_LOGIN,"true")
                var intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
//            var intent = Intent(applicationContext, MainActivity::class.java)
//
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            SavedPrefManager.saveStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN, "true")
//            this.finish()
//
//            startActivity(intent)
//
//            LoginFlag.setLoginFlag(true)
        }
    }

    override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
        Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        Toast.makeText(this, "failure", Toast.LENGTH_LONG).show()

    }


    fun checkboxCheck(): Boolean {
        if (!check_login.isChecked) {
            check_text_login.setText("*Accepting checkbox is necessary")
            check_text_login.visibility = View.VISIBLE
            return false
        } else {
            check_text_login.setText("")
            check_text_login.visibility = View.GONE
            return true
        }
        return true
    }
}