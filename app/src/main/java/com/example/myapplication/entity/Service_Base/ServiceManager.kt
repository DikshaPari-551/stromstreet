package com.example.myapplication.entity.Service_Base

import android.content.Context
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Response.Responce


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
        mContext?.let { Remotedatasource.current(it, false)!!.getloginApi("application/json",jsonObject) }!!.enqueue(callBack)

    }

    fun otp(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
        mContext?.let { Remotedatasource.current(it, false)!!.getotp(jsonObject) }!!.enqueue(callBack)
    }

    fun vOtp(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
        mContext?.let { Remotedatasource.current(it, false)!!.verifyOtp(jsonObject) }!!.enqueue(callBack)
    }

    fun reset(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?, token : String) {
        mContext?.let { Remotedatasource.current(it, false)!!.resetPassword(token,jsonObject) }!!
            .enqueue(callBack)

    }
    fun forget(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
        mContext?.let { Remotedatasource.current(it, false)!!.forgetPassword(jsonObject) }!!
            .enqueue(callBack)

    }

    fun getUserDetails(callBack: ApiCallBack<Responce>) {
        mContext?.let { Remotedatasource.current(it, true)!!.getProfile() }!!
            .enqueue(callBack)

    }

    fun updateUserDetails(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
        mContext?.let { Remotedatasource.current(it, true)!!.userDetails(jsonObject) }!!
            .enqueue(callBack)

    }
//
//    fun updateprofile(callBack: ApiCallBack<Responce>, jsonObject: Api_Request?) {
//        mContext?.let { Remotedatasource.current(it,true)!!.update(jsonObject) }!!.enqueue(callBack)
//
//    }
//
//    fun getProfile(callBack: ApiCallBack<Responce>) {
//        mContext?.let { Remotedatasource.current(it, true)!!.getUserProfile() }!!.enqueue(callBack)
//
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

