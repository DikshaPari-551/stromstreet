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
    @SerializedName("token") val token: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("followerId") val followerId: String,
    @SerializedName("postId") val postId: String,
    @SerializedName("userResult") val userResult : UserResult,
    @SerializedName("followerCount") val followerCount : Int,
    @SerializedName("followingCount") val followingCount : Int,
    @SerializedName("docs") val docs : List<Docs>,
    @SerializedName("total") val total : Int,
    @SerializedName("limit") val limit : Int,
    @SerializedName("page") val page : Int,
    @SerializedName("pages") val pages : Int,
    @SerializedName("categoryResult") val categoryResult : List<CategoryResult>,
    @SerializedName("postResult") val postResult : PostResult,
    @SerializedName("likeCount") val likeCount : Int,
    @SerializedName("commentCount") val commentCount : Int
)

data class PostResult (

    @SerializedName("location") val location : Location,
    @SerializedName("mediaType") val mediaType : String,
    @SerializedName("description") val description : String,
    @SerializedName("imageLinks") val imageLinks : List<String>,
    @SerializedName("status") val status : String,
    @SerializedName("thumbNail") val thumbNail : String,
    @SerializedName("_id") val _id : String,
    @SerializedName("videoLink") val videoLink : String,
    @SerializedName("userId") val userId : UserId,
    @SerializedName("categoryId") val categoryId : CategoryId,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("__v") val __v : Int
)

data class Location (

    @SerializedName("type") val type : String,
    @SerializedName("coordinates") val coordinates : List<Double>
)
data class UserId (

    @SerializedName("_id") val _id : String,
    @SerializedName("fullName") val fullName : String,
    @SerializedName("email") val email : String,
    @SerializedName("userName") val userName : String
)
data class CategoryId (

    @SerializedName("_id") val _id : String,
    @SerializedName("categoryName") val categoryName : String
)

data class Docs (
    @SerializedName("status") val status : String,
    @SerializedName("request") val request : String,
    @SerializedName("_id") val _id : String,
    @SerializedName("followerIdd") val followerIdd : FollowerId,
    @SerializedName("followerId") val followerId : String,
    @SerializedName("userId") val userId : UserId,
    @SerializedName("postId") val postId : PostId,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("__v") val __v : Int
)


data class PostId (

    @SerializedName("location") val location : Location,
    @SerializedName("mediaType") val mediaType : String,
    @SerializedName("imageLinks") val imageLinks : List<String>,
    @SerializedName("status") val status : String,
    @SerializedName("thumbNail") val thumbNail : String,
    @SerializedName("_id") val _id : String,
    @SerializedName("videoLink") val videoLink : String,
    @SerializedName("userId") val userId : String,
    @SerializedName("categoryId") val categoryId : String,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("__v") val __v : Int
)


data class FollowerId (
    @SerializedName("_id") val _id : String,
    @SerializedName("fullName") val fullName : String,
    @SerializedName("email") val email : String,
    @SerializedName("userName") val userName : String
)

data class UserResult (
    @SerializedName("socialLinks") val socialLinks : SocialLinks,
    @SerializedName("emailType") val emailType : String,
    @SerializedName("phoneNumberType") val phoneNumberType : String,
    @SerializedName("userType") val userType : String,
    @SerializedName("otpVerification") val otpVerification : Boolean,
    @SerializedName("status") val status : String,
    @SerializedName("profileType") val profileType : String,
    @SerializedName("_id") val _id : String,
    @SerializedName("fullName") val fullName : String,
    @SerializedName("countryCode") val countryCode : String,
    @SerializedName("phoneNumber") val phoneNumber : String,
    @SerializedName("email") val email : String,
    @SerializedName("password") val password : String,
    @SerializedName("userName") val userName : String,
    @SerializedName("deviceType") val deviceType : String,
    @SerializedName("deviceToken") val deviceToken : String,
    @SerializedName("profilePic") val profilePic : String,
    @SerializedName("otp") val otp : Int,
    @SerializedName("otpTime") val otpTime : Long,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("__v") val __v : Int,
    @SerializedName("isReset") val isReset : Boolean,
    @SerializedName("token") val token: String,
    @SerializedName("bio") val bio: String
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


data class CategoryResult (

    @SerializedName("status") val status : String,
    @SerializedName("_id") val _id : String,
    @SerializedName("categoryName") val categoryName : String,
    @SerializedName("image") val image : String,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("__v") val __v : Int
)

