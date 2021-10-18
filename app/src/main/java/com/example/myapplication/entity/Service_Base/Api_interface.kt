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

//    @POST("login")
//    fun getloginApi(@Body apiRequest: Api_Request?): Call<Responce>?
//
//    @POST("resend_otp")
//    fun getotp(@Body apiRequest: Api_Request?): Call<Responce>?
//
//    @POST("verify_otp")
//    fun verifyOtp(@Body apiRequest: Api_Request?): Call<Responce>?
//
//    @POST("reset_password")
//    fun resetPassword(@Body apiRequest: Api_Request?): Call<Responce>?
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
