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
            .header("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYxNzExOWZiZDgzMDk4NmRkNGE0NTNhNSIsImVtYWlsIjoiYWpheUBnbWFpbC5jb20iLCJ1c2VyVHlwZSI6IlVzZXIiLCJpYXQiOjE2MzQ4ODczMzEsImV4cCI6MTYzNDk3MzczMX0.kSAg6l-nDzrHdwj2n9MLx7dOBzt0wW9XXqhqb9w7Hjc")
            .build()

        return chain.proceed(newRequest)
    }
}