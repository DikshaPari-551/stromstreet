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
            .header("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYxNmZmZWVlMDMzYWI1NjdiODYxMGIwMyIsImVtYWlsIjoia2FyYW4xMjM0NUBnbWFpbC5jb20iLCJ1c2VyVHlwZSI6IlVzZXIiLCJpYXQiOjE2MzQ3MzYwMTUsImV4cCI6MTYzNDgyMjQxNX0.0Y9ylQO5O9zkxV963RjdjGYq36POOjs-yOSnvNDwgiE")
            .build()

        return chain.proceed(newRequest)
    }
}