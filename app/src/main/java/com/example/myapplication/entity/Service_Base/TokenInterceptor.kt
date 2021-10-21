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
            .header("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYxNzBlZThlZmZmZDU0MGUzYmI2N2E1NiIsImVtYWlsIjoiYW1hbkBnbWFpbC5jb20iLCJ1c2VyVHlwZSI6IlVzZXIiLCJpYXQiOjE2MzQ3OTExNDIsImV4cCI6MTYzNDg3NzU0Mn0.LPpaVu1YBi8uVrROoUo7yXEzhZHYOe3PIuLARcqdgDk")
            .build()

        return chain.proceed(newRequest)
    }
}