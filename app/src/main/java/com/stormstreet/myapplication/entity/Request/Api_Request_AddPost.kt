package com.stormstreet.myapplication.entity.Request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

class Api_Request_AddPostpackage {

    @SerializedName("fullName")
    var fullName: String? = null

    @SerializedName("countryCode")
    var countryCode: String? = null

    @SerializedName("phoneNumber")
    var phoneNumber: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("maxDistance")
    var maxDistance: String? = null

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

    @SerializedName("type")
    var type: String? = null

    @SerializedName("deviceToken")
    var deviceToken: String? = null

    @SerializedName("lastName")
    var lastName: String? = null

    @SerializedName("search")
    var search: String? = null


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

    @SerializedName("address")
    var address: String? = null

    @SerializedName("page")
    var page: String? = null

    @SerializedName("limit")
    var limit: String? = null

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

    @SerializedName("reportType")
    var reportType: String? = null

}

