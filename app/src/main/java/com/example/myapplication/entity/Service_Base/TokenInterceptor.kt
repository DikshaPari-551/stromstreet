package com.example.myapplication.entity.Service_Base

import android.content.Context
import com.example.myapplication.util.SavedPrefManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor(var mContext: Context?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        //rewrite the request to add bearer token
        //rewrite the request to add bearer token
        val newRequest: Request = chain.request().newBuilder()
            .header("accept", "application/json")
            .build()

        return chain.proceed(newRequest)
    }
}