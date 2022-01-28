package com.stormstreet.myapplication

//import androidx.constraintlayout.motion.widget.Debug.getLocation
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.stormstreet.myapplication.Activities.EmailVerificationActivity
import com.stormstreet.myapplication.Activities.ForgotPasswordActivity
import com.stormstreet.myapplication.Activities.SignUpActivity
import com.stormstreet.myapplication.ValidationExt.Validations
import com.stormstreet.myapplication.entity.ApiCallBack
import com.stormstreet.myapplication.entity.Request.Api_Request
import com.stormstreet.myapplication.entity.Response.Responce
import com.stormstreet.myapplication.entity.Service_Base.ApiResponseListener
import com.stormstreet.myapplication.entity.Service_Base.ServiceManager
import com.stormstreet.myapplication.extension.androidextention
import com.stormstreet.myapplication.extension.androidextention.initLoader
import com.stormstreet.myapplication.util.SavedPrefManager
import com.stormstreet.sleeponcue.extension.diasplay_toast
import org.json.JSONException
import org.json.JSONObject
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
    lateinit  var googleicon:LinearLayout
    private var passwordNotVisible = 0
    lateinit var mLoginEmail: TextView
    lateinit var mLoginPassword: TextView
    lateinit var uemail: String
    lateinit var upassword: String
    lateinit var deviceToken: String
    private lateinit var email: EditText
    private lateinit var password: EditText
    lateinit var lottie : LottieAnimationView
    lateinit  var facebook:LinearLayout
    var RC_SIGN_IN = 0
    var callBackManager: CallbackManager? = null
    lateinit var mGoogleSignInClient: GoogleSignInClient
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
        if (Build.VERSION.SDK_INT >= 21)
        {
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
        lottie = findViewById(R.id.loader)
        googleicon= findViewById(R.id.googleicon)
        facebook= findViewById(R.id.facebook)
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


            }


        }
        googleicon.setOnClickListener {
            GOOGLESIGN()

        }
        facebook.setOnClickListener {

            facebookLogin()
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

    private fun facebookLogin() {


        LoginManager.getInstance().logInWithReadPermissions(this, arrayListOf("email"));
        LoginManager.getInstance()
            .registerCallback(callBackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    val accessToken = result?.accessToken!!
                    facebookLoginResponse(accessToken)

                }

                override fun onCancel() {
                }

                override fun onError(error: FacebookException?) {
                }

            })
    }

    private fun facebookLoginResponse(accessToken: AccessToken)
    {
        val graphRequest =
            GraphRequest.newMeRequest(accessToken,
                    object : GraphRequest.GraphJSONObjectCallback {
                        override fun onCompleted(
                                obj: JSONObject,
                                response: GraphResponse
                        ) {
                            if (response != null) {
                                try {

                                    provideData(obj, accessToken)

                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            }
                        }

                    })

        val param = Bundle()
        param.putString("fieleds", "name,email,id")
        graphRequest.parameters = param
        graphRequest.executeAsync()
    }

    private fun provideData(obj: JSONObject, accessToken: AccessToken)
    {
        Log.d("facebook token:", accessToken.token)
        var profile = Profile.getCurrentProfile()
        //SavedPrefManager.saveStringPreferences(this,SavedPrefManager.FIRST_NAME,profile.firstName)
//        SavedPrefManager.saveStringPreferences(this,SavedPrefManager.LAST_NAME,profile.lastName)
//        SavedPrefManager.saveStringPreferences(this,SavedPrefManager.PROFILE_PIC,profile.getProfilePictureUri(250,250).toString())
        var facebookToken = accessToken.token
        if (androidextention.isOnline(this)) {
//            androidextention.showProgressDialog(this)
            lottie.initLoader(true)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                    ApiCallBack<Responce>(this, "socialloginApi", mContext)
            val apiRequest = Api_Request()


            //apiRequest.email = profile!!.firstName
            apiRequest.socialId = profile!!.id
            apiRequest.socialType = "facebook"
            apiRequest.firstName =  profile!!.firstName
            apiRequest.lastName =  profile!!.lastName
            apiRequest.deviceToken =  SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.KEY_DEVICE_TOKEN).toString();

            System.out.println("socialloginApi=" + profile!!.id + profile!!.firstName + profile!!.lastName)

            try {
                serviceManager.socialLoginUser(callBack, apiRequest)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            diasplay_toast("Please check internet connection.")
        }
    }

    private fun GOOGLESIGN() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1077830200638-rvau1kgbn5afgsuac4jqorfu7u7f2pfk.apps.googleusercontent.com")
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        val signInIntent = mGoogleSignInClient.signInIntent

        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        callBackManager!!.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data)

        try {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(mContext, "Cancel", Toast.LENGTH_SHORT).show()
            }
            if (requestCode == RC_SIGN_IN) {
                var task = GoogleSignIn.getSignedInAccountFromIntent(data);
                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                handleSignInResult(task)
            } else {
                Log.w("error", "user cancel")
            }
        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>?) {
        try {
            //   var account: GoogleSignInAccount = GoogleSignInAccount.createDefault()

            var acc = GoogleSignIn.getLastSignedInAccount(applicationContext)
            var googletoken = acc.idToken
//            SavedPrefManager.saveStringPreferences(
//                this,
//                SavedPrefManager.FIRST_NAME,
//                acc.displayName
//            )
            SavedPrefManager.saveStringPreferences(this, SavedPrefManager.EMAIL, acc.email)
            gooogleSignup(acc)
        } catch (e: ApiException) {
            Log.w("error", "signInResult:failed code=" + e.statusCode)
        }

    }

    private fun gooogleSignup(acc: GoogleSignInAccount?)
    {
        if (androidextention.isOnline(this)) {
//            androidextention.showProgressDialog(this)
            lottie.initLoader(true)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(this, "socialloginApi", mContext)
            val apiRequest = Api_Request()


            apiRequest.email = acc!!.email
            apiRequest.socialId = acc!!.id
            apiRequest.socialType = "google"
            apiRequest.firstName =  acc!!.displayName
            apiRequest.lastName =  acc!!.familyName
            apiRequest.deviceToken =  SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.KEY_DEVICE_TOKEN).toString();


            try {
                serviceManager.socialLoginUser(callBack, apiRequest)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            diasplay_toast("Please check internet connection.")
        }

    }

    private fun initializedControl() {

//        val options = FirebaseOptions.Builder()
//                .setApiKey("AIzaSyBoMGVwGl8pdRiGBGl9jhqurmPmPwxzu1A")
//                .setApplicationId("1:1077830200638:android:21dab8dc180b0ae8f6106f")
//                .build()
//        FirebaseApp.initializeApp(this,options);
        callBackManager = CallbackManager.Factory.create()
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                // Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            SavedPrefManager.saveStringPreferences(applicationContext, SavedPrefManager.KEY_DEVICE_TOKEN, token)
            // Log and toast
            //  val msg = getString(R.string.msg_token_fmt, token)
            // Log.d(TAG, msg)
            //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
        val token =    FirebaseMessaging.getInstance().getToken();
        System.out.println("Fetching FCM registration token failed" + token)
//        locationpermission()
    }

//    private fun locationpermission() {

//    }

    private fun LogIn() {
        if (androidextention.isOnline(this)) {
//            androidextention.showProgressDialog(this)
            lottie.initLoader(true)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(this, "LoginApi", mContext)
            val apiRequest = Api_Request()


            apiRequest.emailUserName = uemail
            apiRequest.password = upassword
            apiRequest.deviceType = "android"
            apiRequest.deviceToken =  SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.KEY_DEVICE_TOKEN).toString();

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
            diasplay_toast("Please check internet connection.")
        }
    }

    override fun onApiSuccess(response: Responce, apiName: String?) {
//        androidextention.disMissProgressDialog(this)
        lottie.initLoader(false)
        if (response.responseCode.equals("200") ) {
            if (apiName!!.equals("LoginApi")) {
                if (response.result.otpVerification == false) {
                    var intent = Intent(applicationContext, EmailVerificationActivity::class.java)
                    startActivity(intent)
                } else if (response.result.otpVerification == true) {
                    SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager.TOKEN, response.result.token)
                    SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager.USERID, response.result._id)

                    SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager.userName, response.result.userName)
                    SavedPrefManager.saveStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN, "true")
                    var intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

        } else
            if (apiName!!.equals("socialloginApi")) {
                SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager.TOKEN, response.result.token)
                SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager.USERID, response.result._id)
                SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager.userName, response.result.userName)
                SavedPrefManager.saveStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN, "true")
                var intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
    }
    }

    override fun onApiErrorBody(response: String?, apiName: String?) {
//        androidextention.disMissProgressDialog(this)
        lottie.initLoader(false)

        Toast.makeText(this, "Invalid credential.", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
//        androidextention.disMissProgressDialog(this)
        lottie.initLoader(false)

        Toast.makeText(this, "Server not responding", Toast.LENGTH_LONG).show()

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