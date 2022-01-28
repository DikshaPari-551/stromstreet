package com.stormstreet.myapplication.entity

import android.content.Context
import com.stormstreet.myapplication.R
import com.stormstreet.myapplication.entity.Service_Base.ApiResponseListener
import com.stormstreet.myapplication.extension.androidextention


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class ApiCallBack<T>(
    var apiListener: ApiResponseListener<T>?,
    var apiName: String,
    var mContext: Context
) : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {

        androidextention.disMissProgressDialog(mContext)
        if (response.isSuccessful()) {

            response.body()?.let { apiListener!!.onApiSuccess(it, apiName) }
        } else {
            apiListener!!.onApiErrorBody(response.message().toString(), apiName)
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        try { apiListener!!.onApiFailure(
                mContext!!.getString(R.string.server_not_responding),
                apiName
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}