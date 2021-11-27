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
data class Resultt(

    @SerializedName("docs") val docs: List<Docss>,
    @SerializedName("total") val total: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Int

)

data class Docss(

    @SerializedName("_id") val _id: String,
    @SerializedName("location") val location: Location,
    @SerializedName("mediaType") val mediaType: String,
    @SerializedName("imageLinks") val imageLinks: List<String>,
    @SerializedName("status") val status: String,
    @SerializedName("thumbNail") val thumbNail: String,
    @SerializedName("address") val address: String,
    @SerializedName("videoLink") val videoLink: String,
    @SerializedName("shareCount") val shareCount : Int,
    @SerializedName("userId") val userId: String,
    @SerializedName("categoryId") val categoryId: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("__v") val __v: Int,
    @SerializedName("distance") val distance: Double,
    @SerializedName("userDetails") val userDetails: UserDetailss,
    @SerializedName("request") val request: String,
    @SerializedName("followerId") val followerId: FollowerId,
    @SerializedName("likeCount") val likeCount: Int,
    @SerializedName("categoryDetails") val categoryDetails: List<CategoryDetails>,
    @SerializedName("description") val description : String,
    @SerializedName("title") val title : String,
    @SerializedName("isRead") val isRead : Boolean,
    @SerializedName("body") val body : String,
    @SerializedName("commentId") val commentId : CommentId,
    @SerializedName("postId") val postId : PostId,
    @SerializedName("notificationType") val notificationType : String,
    @SerializedName("thumbnails") val thumbnails : String,
    @SerializedName("notificationBy") val notificationBy : NotificationBy,
    @SerializedName("profileId") val profileId : ProfileId

)

data class ProfileId (

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
    @SerializedName("password") val password : String,
    @SerializedName("fullName") val fullName : String,
    @SerializedName("bio") val bio : String,
    @SerializedName("userName") val userName : String,
    @SerializedName("email") val email : String,
    @SerializedName("phoneNumber") val phoneNumber : String,
    @SerializedName("countryCode") val countryCode : String,
    @SerializedName("otp") val otp : Int,
    @SerializedName("otpTime") val otpTime : Long,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("__v") val __v : Int,
    @SerializedName("deviceToken") val deviceToken : String,
    @SerializedName("deviceType") val deviceType : String,
    )


data class CommentId (

    @SerializedName("commentType") val commentType : String,
    @SerializedName("status") val status : String,
    @SerializedName("_id") val _id : String,
    @SerializedName("postId") val postId : String,
    @SerializedName("userId") val userId : String,
    @SerializedName("comment") val comment : String,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("__v") val __v : Int
)

data class NotificationBy (

    @SerializedName("profilePic") val profilePic : String,
    @SerializedName("_id") val _id : String,
    @SerializedName("email") val email : String,
    @SerializedName("userName") val userName : String
)

data class CategoryDetails (
    @SerializedName("_id") val _id : String,
    @SerializedName("status") val status : String,
    @SerializedName("categoryName") val categoryName : String,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("__v") val __v : Int,
    @SerializedName("image") val image : String

    )



data class UserDetailss(

    @SerializedName("_id") val _id: String,
    @SerializedName("emailType") val emailType: String,
    @SerializedName("phoneNumberType") val phoneNumberType: String,
    @SerializedName("userType") val userType: String,
    @SerializedName("otpVerification") val otpVerification: Boolean,
    @SerializedName("status") val status: String,
    @SerializedName("profileType") val profileType: String,
    @SerializedName("fullName") val fullName: String,
    @SerializedName("countryCode") val countryCode: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("online") val online : Boolean,
    @SerializedName("notification") val notification : Boolean,
    @SerializedName("inActive") val inActive : Boolean,
    @SerializedName("bio") val bio: String,
    @SerializedName("deviceType") val deviceType: String,
    @SerializedName("deviceToken") val deviceToken: String,
    @SerializedName("profilePic") val profilePic: String,
    @SerializedName("socialLinks") val socialLinks: SocialLinks,
    @SerializedName("isReset") val isReset : Boolean,
    @SerializedName("otp") val otp: Int,
    @SerializedName("otpTime") val otpTime: Long,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("__v") val __v: Int
)

