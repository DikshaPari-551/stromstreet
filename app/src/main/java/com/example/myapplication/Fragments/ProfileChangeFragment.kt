package com.example.myapplication.Fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.myapplication.*
import com.example.myapplication.ValidationExt.Validations
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
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File


class ProfileChangeFragment : Fragment(), ClickListner {
    lateinit var cameraProfileimg: ImageView
    lateinit var userProfile: CircleImageView
    lateinit var fullNameProfileEt: EditText
    lateinit var nameProfileText: TextView
    lateinit var usernameProfileEt: EditText
    lateinit var usernameProfileText: TextView
    lateinit var emailProfileEt: EditText
    lateinit var emailProfileText: TextView
    lateinit var phoneNumberProfileEt: EditText
    lateinit var phoneNumberProfiletext: TextView
    lateinit var userBio: EditText
    private lateinit var etTwitterLink: EditText
    private lateinit var etFacebookLink: EditText
    private lateinit var etInstagramLink: EditText
    private lateinit var etYoutubeLink: EditText
    lateinit var layoutButoonSaveChanges: RelativeLayout
    lateinit var backButton: ImageView
    private lateinit var mContext: Context
    private var etfullName: String? = ""
    private var etuserName: String? = ""
    private var etemail: String? = ""
    private var etphoneNumber: String? = ""
    private var etbio: String? = ""
    private var twitterLink: String? = ""
    private var facebookLink: String? = ""
    private var instagramLink: String? = ""
    private var youtubeLink: String? = ""
    private var userProfileLink: String? = ""
    private var imageType = ""
    lateinit var imageFile: File
    lateinit var serviceManager: ServiceManager
    lateinit var callBack: ApiCallBack<Responce>
    private val GALLERY = 1
    private var CAMERA: Int = 2
    lateinit var image: Uri
    companion object{
        private var USER_IMAGE_UPLOADED: String? = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_profile_change, container, false)
        mContext = activity!!.applicationContext
        serviceManager = ServiceManager(mContext)

        fullNameProfileEt = v.findViewById(R.id.fullname_profile_et)
        nameProfileText = v.findViewById(R.id.name_profile_text)
        usernameProfileEt = v.findViewById(R.id.username_profile_et)
        usernameProfileText = v.findViewById(R.id.username_profile_text)
        emailProfileEt = v.findViewById(R.id.email_profile_etext)
        emailProfileText = v.findViewById(R.id.email_profile_text)
        phoneNumberProfileEt = v.findViewById(R.id.phonenumber_profile_et)
        phoneNumberProfiletext = v.findViewById(R.id.phone_profile_text)
        backButton = v.findViewById(R.id.back_arrow_profile_change)
        userBio = v.findViewById(R.id.user_bio)
        etTwitterLink = v.findViewById(R.id.twitter_link)
        etFacebookLink = v.findViewById(R.id.facebook_link)
        etInstagramLink = v.findViewById(R.id.instagram_link)
        etYoutubeLink = v.findViewById(R.id.youtube_link)
        userProfile = v.findViewById(R.id.cuserProfile)
        cameraProfileimg = v.findViewById(R.id.img_camera_profile)
        layoutButoonSaveChanges = v.findViewById(R.id.layout_butoon_svae_changes)
        mContext = activity!!

        //clicks
        backButton.setOnClickListener {
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                EditProfileFragment()
            )
                ?.commit()
        }

        layoutButoonSaveChanges.setOnClickListener {

        }

        cameraProfileimg.setOnClickListener {
            RequestPermission.requestMultiplePermissions(mContext)
            var bottomsheet =
                bottomSheetDialog("profilechange", this)
            fragmentManager?.let { it1 -> bottomsheet.show(it1, "bottomsheet") }
        }
        userProfile.setOnClickListener {
            RequestPermission.requestMultiplePermissions(mContext)
            var bottomsheet =
                bottomSheetDialog("profilechange", this)
            fragmentManager?.let { it1 -> bottomsheet.show(it1, "bottomsheet") }
        }

        layoutButoonSaveChanges.setOnClickListener {
            CheckValidations()
        }

        //api
        getProfile()

        return v
    }

    fun CheckValidations() {
        etfullName = fullNameProfileEt.text.toString()
        etuserName = usernameProfileEt.text.toString()
        etemail = emailProfileEt.text.toString().trim()
        etphoneNumber = phoneNumberProfileEt.text.toString()
        etbio = userBio.text.toString()
        twitterLink = etTwitterLink.text.toString().trim()
        facebookLink = etFacebookLink.text.toString().trim()
        instagramLink = etInstagramLink.text.toString().trim()
        youtubeLink = etYoutubeLink.text.toString().trim()

        if (Validations.required(etfullName!!, nameProfileText) &&
            Validations.required(
                etuserName!!, usernameProfileText
            ) &&
            Validations.Email(etemail!!, emailProfileText)
        ) {
            if (SavedPrefManager.getStringPreferences(mContext, AppConst.USER_IMAGE_LINK) != null) {
                updateProfileDeatils()
            } else {
                updateProfileDeatils()
            }
        }

    }

    fun getProfile() {
        androidextention.showProgressDialog(mContext)
        val serviceManager = ServiceManager(mContext)
        val callBack: ApiCallBack<Responce> =
            ApiCallBack<Responce>(object : ApiResponseListener<Responce> {
                override fun onApiSuccess(response: Responce, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    if (response.responseCode == "200") {
                        fullNameProfileEt.setText(response.result.userResult.fullName)
                        usernameProfileEt.setText(response.result.userResult.userName)
                        emailProfileEt.setText(response.result.userResult.email)
                        phoneNumberProfileEt.setText(response.result.userResult.phoneNumber)
                        userBio.setText(response.result.userResult.bio)
                        etTwitterLink.setText(response.result.userResult.socialLinks.twitter)
                        etFacebookLink.setText(response.result.userResult.socialLinks.facebook)
                        etInstagramLink.setText(response.result.userResult.socialLinks.instagram)
                        etYoutubeLink.setText(response.result.userResult.socialLinks.youtube)
                        Glide.with(mContext).load(response.result.userResult.profilePic)
                            .placeholder(R.drawable.circleprofile).into(userProfile)

                    } else {
                        Toast.makeText(activity, response.responseMessage, Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onApiErrorBody(response: String?, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    Toast.makeText(
                        mContext,
                        "Something Went Wrong" + response.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onApiFailure(failureMessage: String?, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    Toast.makeText(mContext, "Server not responding" + failureMessage, Toast.LENGTH_LONG)
                        .show()
                }

            }, "GetProfile", mContext)

        try {
            serviceManager.getUserDetails(callBack)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun updateProfileDeatils() {
        androidextention.showProgressDialog(mContext)
        val serviceManager = ServiceManager(mContext)
        val callBack: ApiCallBack<Responce> =
            ApiCallBack<Responce>(object : ApiResponseListener<Responce> {
                override fun onApiSuccess(response: Responce, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    if (response.responseCode == "200") {
                        USER_IMAGE_UPLOADED = "false"

                        Toast.makeText(
                            activity,
                            response.responseMessage,
                            Toast.LENGTH_LONG
                        ).show()
//                        getFragmentManager()?.beginTransaction()?.replace(
//                            R.id.linear_layout,
//                            EditProfileFragment()
//                        )
//                            ?.commit()
                    } else {
                        Toast.makeText(
                            activity,
                            response.responseMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onApiErrorBody(response: String?, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    Toast.makeText(
                        activity,
                        "error response" + response.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onApiFailure(failureMessage: String?, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    Toast.makeText(
                        activity,
                        "failure response:" + failureMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }

            }, "EditProfile", mContext)

        val apiRequest = Api_Request()
        val socialLinks = SocialLinks(facebookLink, twitterLink, instagramLink, youtubeLink)
        apiRequest.fullName = etfullName
        apiRequest.lastName = ""
        apiRequest.countryCode = ""
        apiRequest.phoneNumber = etphoneNumber
        apiRequest.email = etemail
        apiRequest.userName = etuserName
        apiRequest.bio = etbio
        if(USER_IMAGE_UPLOADED.equals("true")) {
            apiRequest.profilePic = userProfileLink
        }
        apiRequest.socialLinks = socialLinks


        try {
            serviceManager.updateUserDetails(callBack, apiRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun uploadUserImageApi() {
        androidextention.showProgressDialog(mContext)
        callBack =
            ApiCallBack<Responce>(object : ApiResponseListener<Responce> {
                override fun onApiSuccess(response: Responce, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    if (response.responseCode == "200") {
                        USER_IMAGE_UPLOADED = "true"
                        userProfileLink = response.result.mediaUrl
                        imageType = response.result.mediaType
//                        updateProfileDeatils()
                        CheckValidations()
                    } else {
                        Toast.makeText(
                            mContext,
                            response.responseMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onApiErrorBody(response: String?, apiName: String?) {
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

        var surveyBody = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
        var uploaded_file: MultipartBody.Part =
            MultipartBody.Part.createFormData("image", imageFile.name, surveyBody)


        try {
            serviceManager.userUploadFile(callBack, uploaded_file)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun clickListner(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        bottomSheetDialog: bottomSheetDialog,
        imagePath: String
    ) {
        if (resultCode == Activity.RESULT_CANCELED) {
            return
        }
        try {
            if (requestCode == GALLERY) {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        image = data.data!!
                        Glide.with(mContext).load(image).into(userProfile)
//                        userProfile.setImageURI(image)
                        bottomSheetDialog.dismiss()
                        val path = getPathFromURI(image)
                        if (path != null) {
                            imageFile = File(path)
                        }
                        uploadUserImageApi()
                    }

                }
            } else if (requestCode == CAMERA) {
                if (resultCode == Activity.RESULT_OK) {
                    imageFile = File(imagePath)
                    Glide.with(mContext).load(imageFile).into(userProfile)
//                    userProfile.setImageURI(Uri.fromFile(imageFile))
                    bottomSheetDialog.dismiss()
//                    USER_IMAGE_UPLOADED = "ture"
                    uploadUserImageApi()
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun getPathFromURI(contentUri: Uri?): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            activity!!.getContentResolver().query(contentUri!!, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            res = cursor.getString(column_index)
        }
        cursor.close()
        return res
    }


}