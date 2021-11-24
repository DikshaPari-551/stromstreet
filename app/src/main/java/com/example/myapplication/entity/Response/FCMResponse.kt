package com.example.myapplication.entity.Response
import com.google.gson.annotations.SerializedName

data class FCMResponse (
    @SerializedName("thumbnails") val thumbnails: String,
    @SerializedName("postId") val postId: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("body") val body: String,
    @SerializedName("from") val from: String,
    @SerializedName("title") val title: String,
    @SerializedName("notification_body") val notification_body: String,
    @SerializedName("notificationBy") val notificationBy: String,
    @SerializedName("notificationType") val notificationType: String,
    @SerializedName("commentId") val commentId: String
    )



