package com.example.myapplication.entity.Response

import com.google.gson.annotations.SerializedName

class LocalActivityResponse {

    @SerializedName("result")
    lateinit var result: Resultt

    @SerializedName("responseMessage")
    val responseMessage: String? = null

    @SerializedName("responseCode")
    val responseCode: String? = null
}

data class Resultt (

    @SerializedName("docs") val docs : List<Docss>,
    @SerializedName("total") val total : Int,
    @SerializedName("limit") val limit : Int,
    @SerializedName("page") val page : Int,
    @SerializedName("pages") val pages : Int
)

data class Docss (

    @SerializedName("_id") val _id : String,
    @SerializedName("location") val location : Location,
    @SerializedName("mediaType") val mediaType : String,
    @SerializedName("imageLinks") val imageLinks : List<String>,
    @SerializedName("status") val status : String,
    @SerializedName("thumbNail") val thumbNail : String,
    @SerializedName("videoLink") val videoLink : String,
    @SerializedName("userId") val userId : String,
    @SerializedName("categoryId") val categoryId : String,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("__v") val __v : Int,
    @SerializedName("distance") val distance : Double,
    @SerializedName("userDetails") val userDetails : UserDetailss
)

data class UserDetailss (

    @SerializedName("_id") val _id : String,
    @SerializedName("emailType") val emailType : String,
    @SerializedName("phoneNumberType") val phoneNumberType : String,
    @SerializedName("userType") val userType : String,
    @SerializedName("otpVerification") val otpVerification : Boolean,
    @SerializedName("status") val status : String,
    @SerializedName("profileType") val profileType : String,
    @SerializedName("fullName") val fullName : String,
    @SerializedName("countryCode") val countryCode : String,
    @SerializedName("phoneNumber") val phoneNumber : String,
    @SerializedName("email") val email : String,
    @SerializedName("password") val password : String,
    @SerializedName("userName") val userName : String,
    @SerializedName("bio") val bio : String,
    @SerializedName("deviceType") val deviceType : String,
    @SerializedName("deviceToken") val deviceToken : String,
    @SerializedName("profilePic") val profilePic : String,
    @SerializedName("socialLinks") val socialLinks : SocialLinks,
    @SerializedName("otp") val otp : Int,
    @SerializedName("otpTime") val otpTime : Long,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("__v") val __v : Int
)