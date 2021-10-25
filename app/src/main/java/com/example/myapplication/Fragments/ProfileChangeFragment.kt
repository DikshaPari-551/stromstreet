package com.example.myapplication.Fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Binder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.myapplication.*
import com.example.myapplication.ValidationExt.Validations
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Request.SocialLinks
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.AppConst
import com.example.myapplication.util.FileUpload
import com.example.myapplication.util.SavedPrefManager
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.io.File
import java.io.IOException


class ProfileChangeFragment : Fragment() {
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


        mContext = activity!!

        getProfile()

        if (SavedPrefManager.getStringPreferences(
                mContext,
                AppConst.USER_IMAGE_UPLOADED
            ) == "true"
        ) {
            userProfile.setImageURI(FileUpload.getImageFile())
        }

        backButton.setOnClickListener {
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                ProfileFragment()
            )
                ?.commit()
        }

        layoutButoonSaveChanges = v.findViewById(R.id.layout_butoon_svae_changes)


        layoutButoonSaveChanges.setOnClickListener {

        }
        cameraProfileimg = v.findViewById(R.id.img_camera_profile)
        cameraProfileimg.setOnClickListener {
            var bottomsheet =
                bottomSheetDialog("profilechange", userProfile)
            fragmentManager?.let { it1 -> bottomsheet.show(it1, "bottomsheet") }
        }

        layoutButoonSaveChanges.setOnClickListener {
            CheckValidations()
        }
        return v
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Glide.with(mContext).load(SavedPrefManager.getStringPreferences(activity, AppConst.USER_IMAGE_LINK))
            .placeholder(R.drawable.circleprofile).into(userProfile)
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
            Validations.Email(etemail!!, emailProfileText) &&
            Validations.CheckPhoneNumber(
                etphoneNumber!!,
                phoneNumberProfiletext
            )
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

                override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    Toast.makeText(
                        mContext,
                        "error response:" + response.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onApiFailure(failureMessage: String?, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    Toast.makeText(mContext, "failure respone:" + failureMessage, Toast.LENGTH_LONG)
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
                        Toast.makeText(
                            activity,
                            response.responseMessage,
                            Toast.LENGTH_LONG
                        ).show()
                        getFragmentManager()?.beginTransaction()?.replace(
                            R.id.linear_layout,
                            EditProfileFragment()
                        )
                            ?.commit()
                    } else {
                        Toast.makeText(
                            activity,
                            response.responseMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
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
        apiRequest.profilePic =
            SavedPrefManager.getStringPreferences(mContext, AppConst.USER_IMAGE_LINK)
        apiRequest.socialLinks = socialLinks


        try {
            serviceManager.updateUserDetails(callBack, apiRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}