package com.example.myapplication.entity.Service_Base

import android.content.Context
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Response.Responce
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Callback


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



//
//    fun updateprofile(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
//        mContext?.let { Remotedatasource.current(it,true)!!.update(jsonObject) }!!.enqueue(callBack)
//
//    }

    fun getProfile(callBack: ApiCallBack<Responce>, token : String) {
        mContext?.let { Remotedatasource.current(it, true)!!.getUserProfile(token) }!!.enqueue(callBack)
    }

    fun getFollower(callBack: ApiCallBack<Responce>) {
        mContext?.let { Remotedatasource.current(it, true)!!.followerUser() }!!.enqueue(callBack)
    }

    fun getFollowing(callBack: ApiCallBack<Responce>,  userId : String) {
        mContext?.let { Remotedatasource.current(it, true)!!.followingUser(userId) }!!.enqueue(callBack)
    }

    fun getFollowunfollow(callBack: ApiCallBack<Responce>,_id : String) {
        mContext?.let { Remotedatasource.current(it, true)!!.followingUnfollow(_id) }!!.enqueue(callBack)
    }



    fun getLikeunlike(callBack: ApiCallBack<Responce>,_id : String) {
        mContext?.let { Remotedatasource.current(it, true)!!.likeUnlike(_id) }!!.enqueue(callBack)
    }
  fun getSavepost(callBack: ApiCallBack<Responce>,postId : String) {
        mContext?.let { Remotedatasource.current(it, true)!!.saveUnsave(postId) }!!.enqueue(callBack)
    }

    fun userUploadMedia( callback: Callback<Responce>, image: MultipartBody.Part?) {
        mContext?.let { Remotedatasource.current(it, true)!!.uploadMedia(image) }!!.enqueue(callback)
    }

}

