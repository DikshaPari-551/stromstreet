package com.example.myapplication.entity.Service_Base
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Response.LocalActivityResponse
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Response.UserPostResponse
import okhttp3.MultipartBody

import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET

interface Api_interface {

    @POST("user/signUp")
    fun getRegisterApi(@Body apiRequest: Api_Request?): Call<Responce>?

    @POST("user/login")
    fun getloginApi(@Body apiRequest: Api_Request?): Call<Responce>?

//    @POST("user/login")
//    fun getloginApi(
//        @Header("Content-Type") value: String,
//        @Body apiRequest: Api_Request?
//    ): Call<Responce>?

    @POST("user/resendOTP")
    fun getotp(@Body apiRequest: Api_Request?): Call<Responce>?

    @POST("user/verifyOTP")
    fun verifyOtp(@Body apiRequest: Api_Request?): Call<Responce>?

    @PUT("user/resetPassword/{token}")
    fun resetPassword(@Path("token") token: String, @Body apiRequest: Api_Request?): Call<Responce>?

    @PATCH("user/changePassword")
    fun changePassword(@Body apiRequest: Api_Request?): Call<Responce>?

    @POST("user/forgotPassword")
    fun forgetPassword(@Body apiRequest: Api_Request?): Call<Responce>?

    @GET("user/getProfile")
    fun getUserProfile(@Header("token") value: String): Call<Responce>?

    @GET("user/seeOtherProfile")
    fun getOtherProfile(@Query("_id") _id: String): Call<Responce>?

    @GET("user/followerList")
    fun followerUser(): Call<LocalActivityResponse>?

    @GET("user/followingList")
    fun followingUser(): Call<Responce>?

    @POST("user/followUser")
    fun followingUnfollow(@Query("_id") _id: String): Call<Responce>?

    @GET("user/likeUnlikePost")
    fun likeUnlike(@Query("postId") postId: String): Call<Responce>?

    @POST("user/savePost")
    fun saveUnsave(@Query("postId") postId: String): Call<Responce>?

//    @POST("user/forgotPassword")
//    fun forgetPassword(@Header("Content-Type","accept") accept:String  , @Body apiRequest: Api_Request?): Call<Responce>?

    @POST("user/editProfile")
    fun userDetails(@Body apiRequest: Api_Request?): Call<Responce>?

    @GET("user/getProfile")
    fun getProfile(): Call<Responce>?

    @GET("user/categoryList")
    fun categoryList(): Call<Responce>?

    @POST("user/addPost")
    fun addPost(@Body jsonObject: Api_Request?): Call<Responce>?

    @PUT("user/logOut")
    fun logoutUser(): Call<Responce>?

    @GET("user/savePostList")
    fun savedList(): Call<UserPostResponse>?

    @POST("user/comment")
    fun comment(@Query("postId") postId: String?, @Query("commentId") commentId: String?, @Body apiRequest: Api_Request?): Call<Responce>?

    @POST("user/comment")
    fun replyComment(@Query("commentId") commentId: String?, @Body apiRequest: Api_Request?): Call<Responce>?
    @POST("user/createReport")
    fun reportPost(@Query("_id") _id: String, @Body apiRequest: Api_Request?): Call<Responce>?

    @GET("user/getPost")
    fun postDetails(@Query("postId") postId : String): Call<Responce>?

    @POST("user/uploadMedia")
    fun uploadMedia(@Part image: MultipartBody.Part): Call<Responce>?

    @Multipart
    @POST("admin/uploadFile")
    fun uploadFile(@Part file : MultipartBody.Part?): Call<Responce>?

    @Multipart
    @POST("admin/uploadMultipleFile")
    fun addUPost(@Part file: ArrayList<MultipartBody.Part>?): Call<Responce>?

    @POST("user/localActivities")
    fun localActivity(@Query("lat") lat : Double?,@Query("lng") lng : Double?, @Body apiRequest: Api_Request?,@Query("page") page : String?,@Query("limit") limit : String?): Call<LocalActivityResponse>?

    @POST("user/trendingPostList")
    fun trendingPost(
        @Query("lat") latitude: Double?,
        @Query("lng") longitude: Double?,
        @Body apiRequest: Api_Request?,

    ): Call<LocalActivityResponse>?

    @POST("user/followingActivity")
    fun followingActivity(@Query("lat") latitude: Double?,@Query("lng") longitude: Double?, @Body apiRequest: Api_Request?): Call<LocalActivityResponse>?

    @GET("user/seeOtherProfile")
    fun otherUserProfile(@Query("_id") _id: String): Call<Responce>?

    @GET("user/othersPostList")
    fun otherUserPost(@Query("userId") userId: String): Call<Responce>?

    @POST("user/userPostList")
    fun getPostList(@Body apiRequest: Api_Request): Call<UserPostResponse>?

    @GET("user/othersPostList")
    fun getOtherPostList(@Query("userId") userId: String): Call<UserPostResponse>?

    @GET("user/commentList")
    fun getCommentList(@Query("postId") postId: String): Call<Responce>?

    @GET("user/commentReplies")
    fun getRepliesCommentlist(@Query("commentId") commentId: String): Call<Responce>?

    @GET("user/likeUnlikeComment")
    fun commentLikes(@Query("commentId") commentId: String): Call<Responce>?

}
