package com.example.myapplication.entity.Response

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class Responce {


    @SerializedName("result")
    lateinit var result: Result

    @SerializedName("responseMessage")
    val responseMessage: String? = null

    @SerializedName("responseCode")
    val responseCode: String? = null
}

data class Result(

    @SerializedName("emailType") val emailType: String,
    @SerializedName("phoneNumberType") val phoneNumberType: String,
    @SerializedName("userType") val userType: String,
    @SerializedName("otpVerification") val otpVerification: Boolean,
    @SerializedName("status") val status: String,
    @SerializedName("profileType") val profileType: String,
    @SerializedName("_id") val _id: String,
    @SerializedName("fullName") val fullName: String,
    @SerializedName("countryCode") val countryCode: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("deviceType") val deviceType: String,
    @SerializedName("deviceToken") val deviceToken: String,
    @SerializedName("socialLinks") var socialLinks: SocialLinks,
    @SerializedName("otp") val otp: Int,
    @SerializedName("otpTime") val otpTime: Long,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("result") val otpresult: OtpResult,
    @SerializedName("responseMessage") val responseMessage: String,
    @SerializedName("responseCode") val responseCode: Int,
    @SerializedName("__v") val __v: Int,
    @SerializedName("isReset") val isReset: Boolean,
    @SerializedName("token") val token: String
)

data class OtpResult(

    @SerializedName("_id") val _id: String,
    @SerializedName("fullName") val fullName: String,
    @SerializedName("email") val email: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("userType") val userType : String,
    @SerializedName("otpVerification") val otpVerification: Boolean,
    @SerializedName("token") val token: String
)

data class SocialLinks(

    @SerializedName("facebook") val facebook: String,
    @SerializedName("twitter") val twitter: String,
    @SerializedName("instagram") val instagram: String,
    @SerializedName("youtube") val youtube: String
)
//    @SerializedName("status")
//    var status: String,
//    @SerializedName("images")
//    var images: ArrayList<Data> = ArrayList<Data>()

//    @SerializedName("data")
//    lateinit var data: Data
//
//
//    @SerializedName("result")
//    var result: Result? = null
//
//    @SerializedName("user")
//    var user: User? = null
//
//    @SerializedName("website_det")
//    lateinit var website_det: Website_det
//
//    @SerializedName("user_feedback")
//    lateinit var user_feedback: User_feedback
//
//    @SerializedName("access_token")
//    var access_token: String = ""
//
//    @SerializedName("status")
//    var status: Boolean = false
//
//    @SerializedName("message")
//    var message: String = ""
//
//    @SerializedName("response_code")
//    var response_code: Int = 0
//
//    @SerializedName("sleep_training")
//    lateinit var sleep_training: Sleep_Training
//
//    @SerializedName("sleep_recording")
//    lateinit var sleep_recording: Sleep_Recording
//
//    @SerializedName("total")
//    var total: Int = 0
//
//
//    @SerializedName("trailData")
//    lateinit var trailData: List<TrailData>
//
//    @SerializedName("createTrailData")
//    lateinit var createTrailData: TrailData
//}
//
//class TrailData {
//    @SerializedName("id")
//    var id: Int = 0
//
//    @SerializedName("date")
//    var date: String = ""
//
//    @SerializedName("trail_time")
//    var trail_time: String = ""
//
//    @SerializedName("current_time")
//    var current_time: String = ""
//
//    @SerializedName("status")
//    var status: String = ""
//
//    @SerializedName("user_id")
//    var user_id: Int = 0
//
//}
//
//
//class User {
//    @SerializedName("fname")
//    var fname: String = ""
//
//    @SerializedName("lname")
//    var lname: String = ""
//
//    @SerializedName("email")
//    lateinit var email: String
//
//    @SerializedName("updated_at")
//    lateinit var updated_at: String
//
//    @SerializedName("created_at")
//    lateinit var created_at: String
//
//    @SerializedName("id")
//    var id: Int = 0
//
//    @SerializedName("otp_code")
//    var otp_code: Int = 0
//
//    @SerializedName("profile_pic")
//    var profile_pic: String = ""
//
//    @SerializedName("email_verified_at")
//    var email_verified_at: String = ""
//
//    @SerializedName("country_code")
//    var country_code: String = ""
//
//    @SerializedName("role")
//    var role: String = ""
//
//    @SerializedName("contact")
//    var contact: String = ""
//
//    @SerializedName("is_otp_varified")
//    var is_otp_varified: Boolean = false
//
//}
//
//class Website_det {
//    @SerializedName("page")
//    var page: String = ""
//
//    @SerializedName("sub_page")
//    var sub_page: String = ""
//
//    @SerializedName("cat1")
//    var cat1: String = ""
//
//    @SerializedName("val1")
//    var val1: String = ""
//
//    @SerializedName("updated_at")
//    var updated_at: String = ""
//
//    @SerializedName("created_at")
//    var created_at: String = ""
//
//    @SerializedName("id")
//    var id: Int = 0
//}
//
//class Data {
//    @SerializedName("otp_code")
//    var otp_code: String = ""
//
//    @SerializedName("email")
//    lateinit var email: String
//
//}
//
//class User_feedback {
//
//    @SerializedName("id")
//    var id: Int = 0
//
//    @SerializedName("feedback")
//    var feedback: String = ""
//
//    @SerializedName("user_id")
//    var user_id: Int = 0
//
//
//    @SerializedName("updated_at")
//    var updated_at: String = ""
//
//
//    @SerializedName("created_at")
//    var created_at: String = ""
//}
//
//class Sleep_Training {
//
//    @SerializedName("subject_code")
//    var subject_code: String = ""
//
//
//    @SerializedName("tone_duration")
//    var tone_duration: String = ""
//
//
//    @SerializedName("between_tone_duration")
//    var between_tone_duration: String = ""
//
//
//    @SerializedName("tone_resposnse_sensitivity")
//    var tone_resposnse_sensitivity: String = ""
//
//
//    @SerializedName("wake_method")
//    var wake_method: String = ""
//
//
//    @SerializedName("sleep_trial_duration")
//    var sleep_trial_duration: String = ""
//
//
//    @SerializedName("actual_sleep_duration")
//    var actual_sleep_duration: String = ""
//
//
//    @SerializedName("between_trial_duration")
//    var between_trial_duration: String = ""
//
//
//    @SerializedName("display_between_trials_timer")
//    var display_between_trials_timer: String = ""
//
//    @SerializedName("number_of_sleep_trials")
//    var number_of_sleep_trials: String = ""
//
//    @SerializedName("user_id")
//    var user_id: Int = 0
//
//    @SerializedName("updated_at")
//    var updated_at: String = ""
//
//    @SerializedName("created_at")
//    var created_at: String = ""
//
//    @SerializedName("id")
//    var id: Int = 0
//}
//
//class Sleep_Recording {
//
//    @SerializedName("between_tone_duration")
//    var between_tone_duration: String = ""
//
//
//    @SerializedName("subject_code")
//    var subject_code: String = ""
//
//
//    @SerializedName("tone_duration")
//    var tone_duration: String = ""
//
//
//    @SerializedName("tone_resposnse_sensitivity")
//    var tone_resposnse_sensitivity: String = ""
//
//
//    @SerializedName("wake_detection_sensitivity")
//    var wake_detection_sensitivity: String = ""
//}
//
//class Result {
//    @SerializedName("accessToken")
//    var accessToken: String = ""
//}
//
