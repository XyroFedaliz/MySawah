package com.mysawah.predict.data.remote

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.mysawah.predict.data.local.SaveToken

object ApiConfig {

    const val LARAVELCUY = "http://192.168.1.108:8000/api/v1/"
    const val FASTAPI = "http://192.168.1.6:8080/"

    fun getLaravelApi(context: Context): ApiService {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val token = SaveToken.getToken(context)
                val requestBuilder = original.newBuilder()
                if (!token.isNullOrEmpty()) {
                    requestBuilder.header("Authorization", "Bearer $token")
                }

                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(LARAVELCUY)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }

    val laravelapi: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(LARAVELCUY)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    val fastApi: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(FASTAPI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

