package com.example.myapplication.entity.Response

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class Chatlist {
    @SerializedName("response_message")
    val responseMessage: String? = null

    @SerializedName("response_code")
    val responseCode: String? = null

    @SerializedName("result")
    lateinit  var categoryResult: ArrayList<Chalist>

}

data class Chalist(

            @SerializedName("clearStatus") val clearStatus: Boolean,
            @SerializedName("_id") val _id: String,
            @SerializedName("status") val status: String,
            @SerializedName("image") val image: String,
            @SerializedName("senderId") val senderId: SenderId,
            @SerializedName("receiverId") val receiverId: ReceiverId,
            @SerializedName("totalUnreadMsg") val totalUnreadMsg: Int,

            @SerializedName("__v") val __v: Int
    )
data class SenderId(
        @SerializedName("_id") val _id: String,
        @SerializedName("fullName") val fullName: String,
        @SerializedName("profilePic") val profilePic: String,

)
data class ReceiverId(
        @SerializedName("_id") val _id: String,
        @SerializedName("fullName") val fullName: String,
        @SerializedName("profilePic") val profilePic: String,
)