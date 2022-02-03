package com.stormstreet.myapplication.entity.Service_Base

import android.content.Context
import com.stormstreet.myapplication.entity.ApiCallBack
import com.stormstreet.myapplication.entity.Request.Api_Request
import com.stormstreet.myapplication.entity.Request.Api_Request_AddPostpackage
import com.stormstreet.myapplication.entity.Response.LocalActivityResponse
import com.stormstreet.myapplication.entity.Response.Responce
import com.stormstreet.myapplication.entity.Response.UserPostResponse
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
    fun socialLoginUser(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
        mContext?.let { Remotedatasource.current(it, true)!!.getsocialoginApi(jsonObject) }!!
                .enqueue(callBack)
    }
//    fun LoginUser(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?, s: String) {
//        mContext?.let { Remotedatasource.current(it, false)!!.getloginApi(s,jsonObject) }!!.enqueue(callBack)
//
//    }

    fun otp(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
        mContext?.let { Remotedatasource.current(it, false)!!.getotp(jsonObject) }!!
            .enqueue(callBack)
    }

    fun vOtp(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
        mContext?.let { Remotedatasource.current(it, false)!!.verifyOtp(jsonObject) }!!
            .enqueue(callBack)
    }

    fun userRestPassword(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?, token: String) {
        mContext?.let { Remotedatasource.current(it, false)!!.resetPassword(token, jsonObject) }!!
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
    fun blocklist(callBack: ApiCallBack<LocalActivityResponse>) {
        mContext?.let { Remotedatasource.current(it, true)!!.blocklist() }!!
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

    fun userAddPost(callBack: ApiCallBack<Responce>, jsonObject: Api_Request_AddPostpackage?) {
        mContext?.let { Remotedatasource.current(it, true)!!.addPost(jsonObject) }!!
            .enqueue(callBack)

    }

    fun getProfile(callBack: ApiCallBack<Responce>, token: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.getUserProfile(token) }!!
            .enqueue(callBack)
    }

    fun getFollower(
            callBack: ApiCallBack<LocalActivityResponse>,
            page: String?,
            limit: String?
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.followerUser(page, limit) }!!.enqueue(callBack)
    }

    fun getFollowing(
            callBack: ApiCallBack<Responce>,
            page: String?,
            limit: String?
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.followingUser(page, limit) }!!
            .enqueue(callBack)
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



    fun commentOnPost(
            callBack: ApiCallBack<Responce>,
            jsonObject: Api_Request?,
            postId: String?,
            commentId: String?
    ) {
        mContext?.let {
            Remotedatasource.current(it, true)!!.comment(postId, commentId, jsonObject)
        }!!
            .enqueue(callBack)
    }

    fun replyCommentOnPost(
            callBack: ApiCallBack<Responce>,
            jsonObject: Api_Request?,
            commentId: String?
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.replyComment(commentId, jsonObject) }!!
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
    fun deletepost(callBack: ApiCallBack<Responce>, _id: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.deletePost(_id) }!!
            .enqueue(callBack)
    }

    fun getPostlist(
            callBack: ApiCallBack<UserPostResponse>, apiRequest: Api_Request, page: String?,
            limit: String?
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.getPostList(apiRequest, page, limit) }!!
            .enqueue(callBack)
    }

    fun getSavedList(callBack: ApiCallBack<UserPostResponse>, page: String?,
                     limit: String?) {
        mContext?.let { Remotedatasource.current(it, true)!!.savedList(page, limit) }!!.enqueue(callBack)
    }

    fun getLocalActivity(
            callBack: ApiCallBack<LocalActivityResponse>,
            lat: Double?,
            lng: Double?,
            apiRequest: Api_Request?,
            page: String?,
            limit: String?
    ) {
        mContext?.let {
            Remotedatasource.current(it, true)!!.localActivity(37.7873589 , -122.408227, apiRequest, page, limit)
        }!!.enqueue(callBack)
    }

    fun getTrendingPost(
            callBack: ApiCallBack<LocalActivityResponse>,
            latitude: Double?,
            longitude: Double?,
            apiRequest: Api_Request?,
            page: String?,
            limit: String?
    ) {
        mContext?.let {
            Remotedatasource.current(it, true)!!
                .trendingPost(latitude, longitude, apiRequest, page, limit)
        }!!.enqueue(callBack)
    }

    fun getFollowingActivity(
            callBack: ApiCallBack<LocalActivityResponse>,
            latitude: Double?,
            longitude: Double?,
            apiRequest: Api_Request?,
            page: String?,
            limit: String?
    ) {
        mContext?.let {
            Remotedatasource.current(it, true)!!
                .followingActivity(latitude, longitude, apiRequest, page, limit)
        }!!.enqueue(callBack)
    }

    fun userUploadMedia(callBack: ApiCallBack<Responce>, image: MultipartBody.Part) {
        mContext?.let { Remotedatasource.current(it, true)!!.uploadMedia(image) }!!
            .enqueue(callBack)
    }

    fun userUploadFile(callBack: ApiCallBack<Responce>, file: MultipartBody.Part?) {
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
    fun blockOtherUser(callBack: ApiCallBack<Responce>, _id: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.blockOtherUser(_id) }!!
            .enqueue(callBack)
    }
    fun UnblockOtherUser(callBack: ApiCallBack<LocalActivityResponse>, _id: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.UnblockOtherUser(_id) }!!
            .enqueue(callBack)
    }
    fun getOtherUserPost(callBack: ApiCallBack<Responce>, userId: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.otherUserPost(userId) }!!
            .enqueue(callBack)
    }



    fun getOtherPostlist(
            callBack: ApiCallBack<UserPostResponse>, userId: String, page: String?,
            limit: String?
    ) {
        mContext?.let {
            Remotedatasource.current(it, true)!!.getOtherPostList(userId, page, limit)
        }!!
            .enqueue(callBack)
    }

    fun getCommentlist(callBack: ApiCallBack<Responce>, postId: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.getCommentList(postId) }!!
            .enqueue(callBack)
    }

    fun getRepliesCommentlist(callBack: ApiCallBack<Responce>, commentId: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.getRepliesCommentlist(commentId) }!!
            .enqueue(callBack)
    }

    fun getCommentLikes(callBack: ApiCallBack<Responce>, commentId: String) {
        mContext?.let { Remotedatasource.current(it, true)!!.commentLikes(commentId) }!!
            .enqueue(callBack)
    }

    fun getNotification(
            callBack: ApiCallBack<LocalActivityResponse>,
            page: String?,
            limit: String?
    ) {
        mContext?.let { Remotedatasource.current(it, true)!!.notification(page, limit) }!!.enqueue(callBack)
    }

    fun getNotificationcount(callBack: ApiCallBack<LocalActivityResponse>) {
        mContext?.let { Remotedatasource.current(it, true)!!.getNotificationCount() }!!.enqueue(callBack)
    }
}

