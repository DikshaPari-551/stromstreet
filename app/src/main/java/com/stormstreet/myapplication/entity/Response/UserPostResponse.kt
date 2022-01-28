package com.stormstreet.myapplication.entity.Response

import com.google.gson.annotations.SerializedName

class UserPostResponse {
    @SerializedName("result")
    lateinit var result : UserPostResult

    @SerializedName("responseMessage")
    val responseMessage: String? = null

    @SerializedName("responseCode")
    val responseCode: String? = null
}

data class UserPostResult (

    @SerializedName("docs") val docs : List<UserPostDocs>,
    @SerializedName("total") val total : Int,
    @SerializedName("limit") val limit : Int,
    @SerializedName("page") val page : Int,
    @SerializedName("pages") val pages : Int
)

data class UserPostDocs (

    @SerializedName("location") val location : UserPostLocation,
    @SerializedName("mediaType") val mediaType : String,
    @SerializedName("description") val description : String,
    @SerializedName("imageLinks") val imageLinks : List<String>,
    @SerializedName("status") val status : String,
    @SerializedName("thumbNail") val thumbNail : String,
    @SerializedName("shareCount") val shareCount : Int,
    @SerializedName("_id") val _id : String,
    @SerializedName("userId") val userId : UserPostUserId,
    @SerializedName("postId") val postId : UserPostPostId,
    @SerializedName("address") val address : String,
    @SerializedName("categoryId") val categoryId : UserPostCategoryId,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("__v") val __v : Int
)

data class UserPostLocation (

    @SerializedName("type") val type : String,
    @SerializedName("coordinates") val coordinates : List<Double>
)


data class UserPostCategoryId (

    @SerializedName("status") val status : String,
    @SerializedName("_id") val _id : String,
    @SerializedName("categoryName") val categoryName : String,
    @SerializedName("image") val image : String,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("__v") val __v : Int
)

data class UserPostUserId (

    @SerializedName("socialLinks") val socialLinks : UserPostSocialLinks,
    @SerializedName("emailType") val emailType : String,
    @SerializedName("phoneNumberType") val phoneNumberType : String,
    @SerializedName("userType") val userType : String,
    @SerializedName("otpVerification") val otpVerification : Boolean,
    @SerializedName("status") val status : String,
    @SerializedName("profilePic") val profilePic : String,
    @SerializedName("profileType") val profileType : String,
    @SerializedName("online") val online : Boolean,
    @SerializedName("notification") val notification : Boolean,
    @SerializedName("inActive") val inActive : Boolean,
    @SerializedName("_id") val _id : String,
    @SerializedName("phoneNumber") val phoneNumber : String,
    @SerializedName("countryCode") val countryCode : String,
    @SerializedName("bio") val bio : String,
    @SerializedName("userName") val userName : String,
    @SerializedName("email") val email : String,
    @SerializedName("fullName") val fullName : String,
    @SerializedName("password") val password : String,
    @SerializedName("otp") val otp : Int,
    @SerializedName("otpTime") val otpTime : Long,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("__v") val __v : Int,
    @SerializedName("deviceToken") val deviceToken : String,
    @SerializedName("deviceType") val deviceType : String
)

data class UserPostSocialLinks (

    @SerializedName("facebook") val facebook : String,
    @SerializedName("twitter") val twitter : String,
    @SerializedName("youtube") val youtube : String,
    @SerializedName("instagram") val instagram : String
)

data class UserPostPostId (

    @SerializedName("location") val location : UserPostLocation,
    @SerializedName("mediaType") val mediaType : String,
    @SerializedName("description") val description : String,
    @SerializedName("imageLinks") val imageLinks : List<String>,
    @SerializedName("status") val status : String,
    @SerializedName("thumbNail") val thumbNail : String,
    @SerializedName("shareCount") val shareCount : Int,
    @SerializedName("_id") val _id : String,
    @SerializedName("userId") val userId : UserId,
    @SerializedName("address") val address : String,
    @SerializedName("categoryId") val categoryId : UserPostCategoryId,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("__v") val __v : Int
)