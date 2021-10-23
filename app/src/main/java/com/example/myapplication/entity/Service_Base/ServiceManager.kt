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

    fun getLogout(callBack: ApiCallBack<Responce>) {
        mContext?.let { Remotedatasource.current(it, true)!!.logoutUser() }!!.enqueue(callBack)
    }

    fun getSavedList(callBack: ApiCallBack<Responce>) {
        mContext?.let { Remotedatasource.current(it, true)!!.savedList() }!!.enqueue(callBack)
    }

    fun commentOnPost(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?, postId : String) {
        mContext?.let { Remotedatasource.current(it, true)!!.comment(postId,jsonObject) }!!
            .enqueue(callBack)
    }
    fun getPostDetails(callBack: ApiCallBack<Responce>,postId: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.postDetails(postId) }!!.enqueue(callBack)
    }

    fun userUploadMedia(callBack: ApiCallBack<Responce>, image: MultipartBody.Part) {
        mContext?.let { Remotedatasource.current(it, true)!!.uploadMedia(image) }!!
            .enqueue(callBack)
    }

    fun userUploadFile(callBack: ApiCallBack<Responce>, apiRequest: Api_Request) {
        mContext?.let { Remotedatasource.current(it, false)!!.uploadFile(apiRequest) }!!
            .enqueue(callBack)
    }

//    fun getFollower(callBack: ApiCallBack<Responce>, token : String) {
//        mContext?.let { Remotedatasource.current(it, true)!!.followerUser(token) }!!.enqueue(callBack)
//    }
//    fun googleSignUp(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
//        mContext?.let { Remotedatasource.current(it, false)!!.googleSignUp(jsonObject) }!!
//            .enqueue(callBack)
//
//    }
//    fun getLogout(callBack: ApiCallBack<Responce>) {
//        mContext?.let { Remotedatasource.current(it, true)!!.getlogout() }!!.enqueue(callBack)
//
//    }
//    fun sendfeedback(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
//        mContext?.let { Remotedatasource.current(it, true)!!.sendFeedback(jsonObject) }!!
//            .enqueue(callBack)
//
//    }
//    fun deleteacc(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
//        mContext?.let { Remotedatasource.current(it, false)!!.deleteAccount(jsonObject) }!!
//            .enqueue(callBack)
//
//    }
//
//    //sleep trainig
//
//    fun sleepTraining(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
//        mContext?.let { Remotedatasource.current(it, true)!!.sleeptraining(jsonObject) }!!
//            .enqueue(callBack)
//    }
//
//    fun sleepTrainingRecording(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
//        mContext?.let { Remotedatasource.current(it, true)!!.sleeptrainingrecording(jsonObject) }!!
//            .enqueue(callBack)
//    }
//
//    fun facebookSignUp(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
//        mContext?.let { Remotedatasource.current(it, false)!!.facebooksignup(jsonObject) }!!
//            .enqueue(callBack)
//    }
//
//    fun getSleepTraining(callBack: ApiCallBack<Responce>) {
//        mContext?.let { Remotedatasource.current(it, true)!!.getsleeptraining() }!!.enqueue(callBack)
//    }
//    fun getSleepTrainingRecording(callBack: ApiCallBack<Responce>) {
//        mContext?.let { Remotedatasource.current(it, true)!!.getsleeptrainingrecording() }!!.enqueue(callBack)
//    }
//
//    fun createTrailList(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
//        mContext?.let { Remotedatasource.current(it, true)!!.createTrail(jsonObject) }!!
//            .enqueue(callBack)
//    }
//
//    fun getTrailList(callBack: ApiCallBack<Responce>) {
//        mContext?.let { Remotedatasource.current(it, true)!!.trailList() }!!.enqueue(callBack)
//    }
//
//    fun trailDetail(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
//        mContext?.let { Remotedatasource.current(it, true)!!.trailDetail(jsonObject) }!!
//            .enqueue(callBack)
//    }

}

