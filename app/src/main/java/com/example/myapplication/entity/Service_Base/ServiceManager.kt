package com.example.myapplication.entity.Service_Base

import android.content.Context
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Response.LocalActivityResponse
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Response.UserPostDocs
import com.example.myapplication.entity.Response.UserPostResponse
import okhttp3.MultipartBody


class ServiceManager(var mContext: Context?) {

    private val accessToken: String? = null

    companion object {

        private var mServiceManager: ServiceManager? = null
        fun getInstance(context: Context?): ServiceManager? {
            if (mServiceManager == null) {
                mServiceManager = ServiceManager(context)
            }
            return mServiceManager
        }
    }

    fun requestLoginUser(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
        mContext?.let { Remotedatasource.current(it, false)!!.getRegisterApi(jsonObject) }!!
            .enqueue(callBack)

    }

    fun LoginUser(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
        mContext?.let { Remotedatasource.current(it, true)!!.getloginApi(jsonObject) }!!
            .enqueue(callBack)
    }
//    fun LoginUser(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?, s: String) {
//        mContext?.let { Remotedatasource.current(it, false)!!.getloginApi(s,jsonObject) }!!.enqueue(callBack)
//
//    }

    fun otp(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
        mContext?.let { Remotedatasource.current(it, false)!!.getotp(jsonObject) }!!.enqueue(callBack)
    }

    fun vOtp(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
        mContext?.let { Remotedatasource.current(it, false)!!.verifyOtp(jsonObject) }!!.enqueue(callBack)
    }

    fun userRestPassword(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?, token : String) {
        mContext?.let { Remotedatasource.current(it, false)!!.resetPassword(token,jsonObject) }!!
            .enqueue(callBack)

    }

    fun userChangePassword(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
        mContext?.let { Remotedatasource.current(it, true)!!.changePassword(jsonObject) }!!
            .enqueue(callBack)

    }

    fun forget(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
        mContext?.let { Remotedatasource.current(it, false)!!.forgetPassword(jsonObject) }!!
            .enqueue(callBack)

//        fun forget(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?,Header : String) {
//            mContext?.let { Remotedatasource.current(it, false)!!.forgetPassword(Header,jsonObject) }!!
//                .enqueue(callBack)

    }

    fun getUserDetails(callBack: ApiCallBack<Responce>) {
        mContext?.let { Remotedatasource.current(it, true)!!.getProfile() }!!
            .enqueue(callBack)

    }

    fun updateUserDetails(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
        mContext?.let { Remotedatasource.current(it, true)!!.userDetails(jsonObject) }!!
            .enqueue(callBack)

    }

    fun getCategoryList(callBack: ApiCallBack<Responce>) {
        mContext?.let { Remotedatasource.current(it, true)!!.categoryList() }!!
            .enqueue(callBack)

    }

    fun userAddPost(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
        mContext?.let { Remotedatasource.current(it, true)!!.addPost(jsonObject) }!!
            .enqueue(callBack)

    }

    fun getProfile(callBack: ApiCallBack<Responce>, token: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.getUserProfile(token) }!!
            .enqueue(callBack)
    }

    fun getFollower(callBack: ApiCallBack<LocalActivityResponse>) {
        mContext?.let { Remotedatasource.current(it, true)!!.followerUser() }!!.enqueue(callBack)
    }

    fun getFollowing(callBack: ApiCallBack<Responce>) {
        mContext?.let { Remotedatasource.current(it, true)!!.followingUser() }!!.enqueue(callBack)
    }

    fun getFollowunfollow(callBack: ApiCallBack<Responce>, _id: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.followingUnfollow(_id) }!!
            .enqueue(callBack)
    }


    fun getLikeunlike(callBack: ApiCallBack<Responce>, _id: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.likeUnlike(_id) }!!.enqueue(callBack)
    }

    fun getSavepost(callBack: ApiCallBack<Responce>, postId: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.saveUnsave(postId) }!!
            .enqueue(callBack)
    }

    fun getLogout(callBack: ApiCallBack<Responce>) {
        mContext?.let { Remotedatasource.current(it, true)!!.logoutUser() }!!.enqueue(callBack)
    }

    fun getSavedList(callBack: ApiCallBack<UserPostResponse>) {
        mContext?.let { Remotedatasource.current(it, true)!!.savedList() }!!.enqueue(callBack)
    }

    fun commentOnPost(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?, postId: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.comment(postId, jsonObject) }!!
            .enqueue(callBack)
    }

    fun reportPostApi(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?, _id: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.reportPost(_id, jsonObject) }!!
            .enqueue(callBack)
    }

    fun getPostDetails(callBack: ApiCallBack<Responce>, postId: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.postDetails(postId) }!!
            .enqueue(callBack)
    }

    fun getLocalActivity(
        callBack: ApiCallBack<LocalActivityResponse>,
        lat: Double?,
        lng: Double?,
        apiRequest: Api_Request?
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.localActivity(lat,lng,apiRequest) }!!.enqueue(callBack)
    }
    fun getTrendingPost(
        callBack: ApiCallBack<LocalActivityResponse>,
        latitude: Double?,
        longitude: Double?,
        apiRequest: Api_Request?
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.trendingPost(latitude,longitude,apiRequest) }!!.enqueue(callBack)
    }

    fun getFollowingActivity(
        callBack: ApiCallBack<LocalActivityResponse>,
        latitude: Double?,
        longitude: Double?,
        apiRequest: Api_Request?
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.followingActivity(latitude,longitude,apiRequest) }!!.enqueue(callBack)
    }

    fun userUploadMedia(callBack: ApiCallBack<Responce>, image: MultipartBody.Part) {
        mContext?.let { Remotedatasource.current(it, true)!!.uploadMedia(image) }!!
            .enqueue(callBack)
    }

    fun userUploadFile(callBack: ApiCallBack<Responce>, file : MultipartBody.Part?) {
        mContext?.let { Remotedatasource.current(it, false)!!.uploadFile(file!!) }!!
            .enqueue(callBack)
    }

    fun uploadMultiData(callBack: ApiCallBack<Responce>, file: ArrayList<MultipartBody.Part>?) {
        mContext?.let { Remotedatasource.current(it, true)!!.addUPost(file!!) }!!
            .enqueue(callBack)
    }

    fun getOtherProfile(callBack: ApiCallBack<Responce>, _id: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.getOtherProfile(_id) }!!
            .enqueue(callBack)
    }

    fun getOtherUserProfile(callBack: ApiCallBack<Responce>, _id: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.otherUserProfile(_id) }!!
            .enqueue(callBack)
    }

    fun getOtherUserPost(callBack: ApiCallBack<Responce>, userId: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.otherUserPost(userId) }!!
            .enqueue(callBack)
    }

    fun getPostlist(callBack: ApiCallBack<UserPostResponse>) {
        mContext?.let { Remotedatasource.current(it, true)!!.getPostList() }!!.enqueue(callBack)
    }

    fun getOtherPostlist(callBack: ApiCallBack<UserPostResponse>, userId: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.getOtherPostList(userId) }!!
            .enqueue(callBack)
    }

    fun getCommentlist(callBack: ApiCallBack<Responce>, postId: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.getCommentList(postId) }!!
            .enqueue(callBack)
    }


}

