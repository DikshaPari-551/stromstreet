package com.example.myapplication.entity.Service_Base



import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Response.Responce

import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET




interface Api_interface
{

    @POST("user/signUp")
    fun getRegisterApi(@Body apiRequest: Api_Request?): Call<Responce>?

    @POST("user/login")
    fun getloginApi(@Path("token") token: String,@Body apiRequest: Api_Request?): Call<Responce>?

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
    fun getUserProfile(@Header("token")value: String): Call<Responce>?

    @GET("user/followerList")
    fun followerUser(): Call<Responce>?

    @GET("user/followerList")
    fun followingUser(@Query("userId")userId : String): Call<Responce>?

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
//
//    @POST("profile")
//    fun update(@Body apiRequest: Api_Request?): Call<Responce>?
//
//    @GET("profile")
//     fun getUserProfile(): Call<Responce>?
//
//    @GET("website")
//    fun getWebsite(): Call<Responce>?
//
//    @POST("website")
//    fun website(@Body apiRequest: Api_Request?): Call<Responce>?
//
//    @POST("login/google")
//    fun googleSignUp(@Body apiRequest: Api_Request?): Call<Responce>?
//
//    @POST("logout")
//    fun getlogout(): Call<Responce>?
//
//    @POST("add_feedback")
//    fun sendFeedback(@Body apiRequest: Api_Request?): Call<Responce>?
//
//    @POST("delete")
//    fun deleteAccount(@Body apiRequest: Api_Request?): Call<Responce>?
//
//    @POST("sleep_training")
//    fun sleeptraining(@Body apiRequest: Api_Request?): Call<Responce>?
//
//    @POST("sleep_recording")
//    fun sleeptrainingrecording(@Body apiRequest: Api_Request?): Call<Responce>?
//
//    @GET("get_sleep_training")
//    fun getsleeptraining(): Call<Responce>?
//
//    @GET("get_sleep_recording")
//    fun getsleeptrainingrecording(): Call<Responce>?
//
//    @POST("login/facebook")
//    fun facebooksignup(@Body apiRequest: Api_Request?): Call<Responce>?
//
//    @POST("create-trail")
//    fun createTrail(@Body apiRequest: Api_Request?): Call<Responce>?
//
//    @GET("trail-list")
//    fun trailList(): Call<Responce>?
//
//    @POST("trail-detail")
//    fun trailDetail(@Body apiRequest: Api_Request?): Call<Responce>?
}
