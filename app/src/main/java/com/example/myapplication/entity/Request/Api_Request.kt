package com.example.myapplication.entity.Request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import java.io.File

class Api_Request {

    @SerializedName("fullName")
    var fullName: String? = null

    @SerializedName("countryCode")
    var countryCode: String? = null

    @SerializedName("phoneNumber")
    var phoneNumber: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("emailUserName")
    var emailUserName: String? = null

    @SerializedName("commentType")
    var commentType: String? = null

    @SerializedName("comment")
    var comment: String? = null

    @SerializedName("password")
    var password: String? = null

    @SerializedName("newPassword")
    var newPassword: String? = null

    @SerializedName("oldPassword")
    var oldPassword: String? = null

    @SerializedName("userName")
    var userName: String? = null

    @SerializedName("bio")
    var bio: String? = null

    @SerializedName("deviceType")
    var deviceType: String? = null

    @SerializedName("deviceToken")
    var deviceToken: String? = null

    @SerializedName("lastName")
    var lastName: String? = null


    @SerializedName("socialLinks")
    var socialLinks: SocialLinks? = null

    @SerializedName("mediaType")
    var mediaType: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("categoryId")
    var categoryId: String? = null

    @SerializedName("videoLink")
    var videoLink: String? = null

    @SerializedName("profilePic")
    var profilePic: String? = null

    @SerializedName("imageLinks")
    var imageLinks: List<String>? = null

    @SerializedName("location")
    var location: Location? = null

    @SerializedName("otp")
    @Expose
    var otp: Int? = null

    @SerializedName("uploaded_file")
    @Expose
    var uploaded_file: MultipartBody.Part? = null
}

data class SocialLinks(

    @SerializedName("facebook")
    var facebook: String? = null,

    @SerializedName("twitter")
    var twitter: String? = null,

    @SerializedName("instagram")
    var instagram: String? = null,

    @SerializedName("youtube")
    var youtube: String? = null
)

data class Location(
    @SerializedName("type") val type: String,
    @SerializedName("coordinates") val coordinates: List<Double>
)

//    @SerializedName("fname")
//    @Expose
//    var fname:String?=null
//
//    @SerializedName("lname")
//    @Expose
//    var lname:String?=null
//
//    @SerializedName("email")
//    @Expose
//    var email:String?=null
//
//    @SerializedName("password")
//    @Expose
//    var password:String?=null
//
//    @SerializedName("password_confirmation")
//    @Expose
//    var password_confirmation:String?=null
//
//    @SerializedName("new_password")
//    @Expose
//    var new_password:String?=null
//
//    @SerializedName("confirm_password")
//    @Expose
//    var confirm_password:String?=null
//
//    @SerializedName("otp")
//    @Expose
//    var otp:String?=null
//
//    @SerializedName("page")
//    @Expose
//    var page:String?=null
//
//    @SerializedName("sub_page")
//    @Expose
//    var sub_page:String?=null
//
//    @SerializedName("cat1")
//    @Expose
//    var cat1:String?=null
//
//    @SerializedName("val1")
//    @Expose
//    var val1:String?=null
//
//    @SerializedName("token")
//    @Expose
//    var token:String?=null
//
//    @SerializedName("feedback")
//    @Expose
//    var feedback:String?=null
//
//    @SerializedName("image1")
//    @Expose
//    var image1:String?=null
//
//    @SerializedName("image2")
//    @Expose
//    var image2:String?=null
//
//    @SerializedName("image3")
//    @Expose
//    var image3:String?=null
//
//    @SerializedName("profile_pic")
//    @Expose
//    var profile_pic:String?=null
//
//    @SerializedName("contact")
//    @Expose
//     var contact:String?=null
//
//    @SerializedName("subject_code")
//    @Expose
//     var subject_code:String?=null
//
//    @SerializedName("tone_duration")
//    @Expose
//     var tone_duration:String?=null
//
//    @SerializedName("between_tone_duration")
//    @Expose
//     var between_tone_duration:String?=null
//
//    @SerializedName("tone_resposnse_sensitivity")
//    @Expose
//     var tone_resposnse_sensitivity:String?=null
//
//    @SerializedName("wake_method")
//    @Expose
//     var wake_method:String?=null
//
//    @SerializedName("sleep_trial_duration")
//    @Expose
//     var sleep_trial_duration:String?=null
//
//    @SerializedName("actual_sleep_duration")
//    @Expose
//     var actual_sleep_duration:String?=null
//
//    @SerializedName("between_trial_duration")
//    @Expose
//     var between_trial_duration:String?=null
//
//    @SerializedName("display_between_trials_timer")
//    @Expose
//     var display_between_trials_timer:String?=null
//
//    @SerializedName("number_of_sleep_trials")
//    @Expose
//     var number_of_sleep_trials:String?=null
//
//    @SerializedName("trail_time")
//    @Expose
//    var trail_time:String?=null
//
//    @SerializedName("status")
//    @Expose
//    var status:String?=null
//
//    @SerializedName("current_time")
//    @Expose
//    var current_time:String?=null
//
//    @SerializedName("trail_id")
//    @Expose
//    var trail_id:String?=null
//
//    @SerializedName("date")
//    @Expose
//    var date:String?=null


