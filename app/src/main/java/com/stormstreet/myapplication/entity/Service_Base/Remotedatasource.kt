package com.stormstreet.myapplication.entity.Service_Base

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class Remotedatasource
{

    companion object {
        private var _service: Api_interface? = null
        private val BASE_URL: String? = null
        private var _client: Remotedatasource? = null
        private var mContext: Context? = null
        private var retrofit: Retrofit? = null
        var condition :Boolean = false

        fun getApiService(): Api_interface? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(ServiceConstant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(Api_interface::class.java)
        }

        fun current(Context: Context, flag: Boolean): Api_interface? {
            condition=flag
            Remotedatasource!!.mContext = Context
            return getInstance().Remotedatasource()
        }

        private fun getInstance(): Remotedatasource
        {
            _client = Remotedatasource()
            return _client as Remotedatasource
        }

        private fun getService(): Api_interface? {
            return _service
        }
    }
        fun Remotedatasource(): Api_interface? {
            val interceptor = TokenInterceptor(mContext)
            if(condition){
                val client: OkHttpClient =  OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .callTimeout(30, TimeUnit.MINUTES)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(1000, TimeUnit.SECONDS)
                    .writeTimeout(1000, TimeUnit.SECONDS)
                    .build()
                retrofit = Retrofit.Builder()
                    .baseUrl(ServiceConstant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            }


          else{
                val client: OkHttpClient =  OkHttpClient.Builder()
                    .callTimeout(50, TimeUnit.MINUTES)
                    .connectTimeout(50, TimeUnit.SECONDS)
                    .readTimeout(1000, TimeUnit.SECONDS)
                    .writeTimeout(1000, TimeUnit.SECONDS)
                    .build()
                retrofit = Retrofit.Builder()
                    .baseUrl(ServiceConstant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            }
            _service=retrofit!!.create(Api_interface::class.java)
           return _service
//            if (retrofit == null) {
//                retrofit = Retrofit.Builder()
//                    .baseUrl(ServiceConstant.BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//            }
//            _service=retrofit!!.create(Api_interface::class.java)
//            return _service

        }

}