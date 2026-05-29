package com.jj.network.retrofit

import com.jj.network.config.JiangNetworkConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    fun create(config: JiangNetworkConfig, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(config.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
