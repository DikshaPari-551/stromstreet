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
            .header("token", Token)
//            .header("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYxNzJjMzJmNWEzZTc3YmIwMjc0NWU5MCIsImVtYWlsIjoic3RyaW5nMUBnbWFpbC5jb20iLCJ1c2VyVHlwZSI6IlVzZXIiLCJpYXQiOjE2MzQ5NzQ1OTMsImV4cCI6MTYzNTA2MDk5M30.A0SS9_XpeejTsBIeo55ztB2eE65nZOaSRgdv2bSmsvo")
            .build()

        return chain.proceed(newRequest)
    }
}