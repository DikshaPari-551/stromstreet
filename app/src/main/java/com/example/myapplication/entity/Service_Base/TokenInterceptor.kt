package com.example.myapplication.entity.Service_Base

import android.content.Context
import com.example.myapplication.util.SavedPrefManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor(var mContext: Context?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val Token = SavedPrefManager.getStringPreferences(mContext,SavedPrefManager.TOKEN).toString()
        //rewrite the request to add bearer token
        val newRequest: Request = chain.request().newBuilder()
//            .header("token", Token)
            .header("token", Token)
            .build()

        return chain.proceed(newRequest)
    }
}