package com.example.myapplication.Activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.LoginActivity
import com.example.myapplication.R
import com.example.myapplication.ValidationExt.Validations
import com.example.myapplication.bottomSheetDialog
import com.example.myapplication.customclickListner.ClickListner
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Request.SocialLinks
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.entity.permission.RequestPermission
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.AppConst
import com.example.myapplication.util.SavedPrefManager
import com.example.sleeponcue.extension.diasplay_toast
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.io.ByteArrayOutputStream
import java.io.File


class SignUpActivity : AppCompatActivity(), ApiResponseListener<Responce>, ClickListner {
    lateinit var check: CheckBox
    lateinit var nameSignUp: TextView
    lateinit var confirmPasswordEt: EditText
    lateinit var confirmPasswordTEXT: TextView
    lateinit var emailSignUp_text: TextView
    lateinit var check_text: TextView

    private val PASSWORD_PATTERN =

        "^" +
                "(?=.*[@#$%^&+=])" +  // at least 1 special character
                "(?=\\S+$)" +  // no white spaces
                ".{4,}" +  // at least 4 characters
                "$"

    lateinit var password_et: EditText
    lateinit var password_text: TextView


    lateinit var phone_et: EditText
    lateinit var phone_text: TextView

    lateinit var emailSignUp_et: EditText
    lateinit var username_text: TextView
    lateinit var username_et: EditText
    lateinit var login: TextView
    lateinit var layout_signup: RelativeLayout
    lateinit var background: RelativeLayout
    lateinit var camera: ImageView
    lateinit var circleProfile: CircleImageView
    lateinit var sign_up_full_name: EditText
    lateinit var twitterLink: EditText
    lateinit var facebookLink: EditText
    lateinit var instagramLink: EditText
    lateinit var youtubeLink: EditText
    lateinit var bio_text: EditText
    lateinit var error_text: TextView
    lateinit var mContext: Context
    private var USER_IMAGE_UPLOADED: String? = ""
    lateinit var image: Uri
    lateinit var imageFile: File
    lateinit var serviceManager: ServiceManager
    lateinit var callBack: ApiCallBack<Responce>
    var userProfile = ""
    var imageType = ""
    private val GALLERY = 1
    private var CAMERA: Int = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        RequestPermission.requestMultiplePermissions(this)
        mContext = this
        serviceManager = ServiceManager(mContext)
        emailSignUp_et = findViewById(R.id.email_sign_etext)
        check_text = findViewById(R.id.check_text)
        phone_et = findViewById(R.id.phonenumber_et)
        phone_text = findViewById(R.id.phone_sign_text)

        username_et = findViewById(R.id.username_et)
        username_text = findViewById(R.id.username_sign_text)

        password_et = findViewById(R.id.password_sign_et)
        password_text = findViewById(R.id.password_sign_text)

        emailSignUp_text = findViewById(R.id.email_sign_text)
        sign_up_full_name = findViewById(R.id.fullname_text)
        nameSignUp = findViewById(R.id.name_sign_up)
        camera = findViewById(R.id.img_camera)
        circleProfile = findViewById(R.id.circleProfile)
        login = findViewById(R.id.text_login)
        check = findViewById(R.id.check)
        layout_signup = findViewById(R.id.layout_signup)
        background = findViewById(R.id.background_error)
        error_text = findViewById(R.id.textView_error)
        confirmPasswordTEXT = findViewById(R.id.Confirmpassword_sign_text)
        confirmPasswordEt = findViewById(R.id.confirmpassword_sign_et)
        twitterLink = findViewById(R.id.su_twitter_link)
        facebookLink = findViewById(R.id.su_facebook_link)
        instagramLink = findViewById(R.id.su_instagram_link)
        youtubeLink = findViewById(R.id.su_youtube_link)
        bio_text = findViewById(R.id.bio_text)

        camera.setOnClickListener {
            var bottomsheet = bottomSheetDialog("signup", this)
            bottomsheet.show(supportFragmentManager, "bottomsheet")
        }
        login.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


        layout_signup.setOnClickListener {
            ConfirmPassword()
            checkboxCheck()
            CheckValidations()
            Signup()
        }
    }

    private fun Signup() {
        if (androidextention.isOnline(this)) {
            androidextention.showProgressDialog(this)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(this, "SignupApi", mContext)
            val apiRequest = Api_Request()
            val socialLinks =
                SocialLinks(
                    facebookLink.text.toString().trim(),
                    twitterLink.text.toString().trim(),
                    instagramLink.text.toString().trim(),
                    youtubeLink.text.toString().trim()
                )
            apiRequest.fullName = sign_up_full_name.getText().toString()
            apiRequest.userName = username_et.getText().toString().trim()
            apiRequest.email = emailSignUp_et.getText().toString().trim()
            apiRequest.phoneNumber = phone_et.getText().toString().trim()
            apiRequest.password = password_et.getText().toString().trim()
            apiRequest.bio = bio_text.getText().toString()
            apiRequest.deviceType = "Android"
            apiRequest.profilePic = userProfile
            apiRequest.socialLinks = socialLinks


            try {
                serviceManager.requestLoginUser(callBack, apiRequest)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {

            diasplay_toast("Check Your Internet Connection")
        }
    }

    override fun onApiSuccess(response: Responce, apiName: String?) {

        if (response.responseCode == "200") {
            androidextention.disMissProgressDialog(this)
            Log.w("", "Response" + response.result)
            var intent = Intent(this, EmailVerificationActivity::class.java)
            intent.putExtra("FORGOTACTIVITY", "signupactivity")
            intent.putExtra("EMAIL", response.result.email)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Toast.makeText(this, "Success" + response.result.otp, Toast.LENGTH_LONG).show()
            startActivity(intent)
            this.finish()

        } else {
            response.responseMessage?.let { androidextention.alertBox(it, this) }
//            Toast.makeText(this,"",Toast.LENGTH_LONG).show()

        }


    }

    override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        Toast.makeText(this, "Failure", Toast.LENGTH_LONG).show()
    }

    fun CheckValidations() {
        var confirmPassword = confirmPasswordEt.text.toString()
        var fullnameSignUp = sign_up_full_name.text.toString()
        var email_sign_up = emailSignUp_et.text.toString().trim()
        var username_ett = username_et.text.toString()
        var phone_ett = phone_et.text.toString()
        var password = password_et.text.toString()

        if (Validations.required(fullnameSignUp, nameSignUp) && Validations.user(
                username_ett,
                username_text
            ) && Validations.Email(email_sign_up, emailSignUp_text) && Validations.Email(
                email_sign_up,
                emailSignUp_text
            ) && Validations.Password(
                password,
                password_text
            ) && ConfirmPassword() && checkboxCheck() == true
        ) {


        }
//        Validations.required(
//            fullnameSignUp,
//            nameSignUp
//        )
        //       Validations.Email(email_sign_up, emailSignUp_text)
//        Validations.Email(
//            email_sign_up,
//            emailSignUp_text
////        )
//        Validations.required(
//            username_ett,
//            username_text
//        )
//        Validations.CheckPhoneNumber(
//            phone_ett,
//            phone_text
//        )
//            Validations.Password(password, password_text)

    }

    fun ConfirmPassword(): Boolean {
        var password = password_et.text.toString()
        var confirmPassword = confirmPasswordEt.text.toString()

        if (confirmPassword.length < 6) {


            confirmPasswordTEXT.setText("*Please enter new password more than 6-digits.")
            confirmPasswordTEXT.visibility = View.VISIBLE
        } else if (!confirmPassword.equals(password)) {

            confirmPasswordTEXT.setText("*Both password should match.")

            confirmPasswordTEXT.visibility = View.VISIBLE
            return false
        } else {
            confirmPasswordTEXT.setText("")


            confirmPasswordTEXT.visibility = View.GONE

            return true

        }
        return true
    }

    fun checkboxCheck(): Boolean {
        if (!check.isChecked) {
            check_text.setText("*Accepting checkbox is necessary")
            check_text.visibility = View.VISIBLE
            return false
        } else {
            check_text.setText("")
            check_text.visibility = View.GONE
            return true
        }
        return true
    }

    override fun clickListner(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        bottomSheetDialog: bottomSheetDialog
    ) {
        if (resultCode == Activity.RESULT_CANCELED) {
            return
        }
        try {
            if (requestCode == GALLERY) {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        image = data.data!!
                        circleProfile.setImageURI(image)
                        bottomSheetDialog.dismiss()
                        val path = getPathFromURI(image)
                        if (path != null) {
                            imageFile = File(path)
//                            image = Uri.fromFile(imageFile)

                        }
                        USER_IMAGE_UPLOADED = "ture"
                        uploadUserImageApi()
                    }

                }
            } else if (requestCode == CAMERA) {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        val thumbnail: Bitmap = data?.extras?.get("data") as Bitmap
                        circleProfile.setImageBitmap(thumbnail)
                        bottomSheetDialog.dismiss()

                        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                        val tempUri: Uri =
                            getImageUri(this, thumbnail)!!
                        val path = getPathFromURI(tempUri)
                        if (path != null) {
                            imageFile = File(path)
//                            image = Uri.fromFile(imageFile)

                        }
                        USER_IMAGE_UPLOADED = "ture"
                        uploadUserImageApi()
                    }
                }
            }


        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun getPathFromURI(contentUri: Uri?): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = contentResolver.query(contentUri!!, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            res = cursor.getString(column_index)
        }
        cursor.close()
        return res
    }

    private fun uploadUserImageApi() {
        androidextention.showProgressDialog(mContext)
        callBack =
            ApiCallBack<Responce>(object : ApiResponseListener<Responce> {
                override fun onApiSuccess(response: Responce, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    if (response.responseCode == "200") {
                        userProfile = response.result.mediaUrl
                        imageType = response.result.mediaType
                    } else {
                        Toast.makeText(
                            mContext,
                            response.responseMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    Toast.makeText(
                        mContext,
                        "error response" + response.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onApiFailure(failureMessage: String?, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    Toast.makeText(
                        mContext,
                        "failure response:" + failureMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }

            }, "UploadFile", mContext)

//        imageFile = FileUpload.getImageFile()
        var surveyBody = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
        var uploaded_file: MultipartBody.Part =
            MultipartBody.Part.createFormData("image", imageFile.name, surveyBody)


        try {
            serviceManager.userUploadFile(callBack, uploaded_file)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}