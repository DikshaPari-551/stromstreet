package com.example.myapplication.entity

import android.content.Context
import com.example.myapplication.Activities.SignUpActivity
import com.example.myapplication.R
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.extension.androidextention


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
            apiListener!!.onApiErrorBody(response.errorBody(), apiName)
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        try {
            apiListener!!.onApiFailure(
                mContext!!.getString(R.string.server_not_responding),
                apiName
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}