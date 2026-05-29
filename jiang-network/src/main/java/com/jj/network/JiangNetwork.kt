package com.jj.network

import com.jj.network.config.JiangNetworkConfig
import com.jj.network.okhttp.OkHttpFactory
import com.jj.network.retrofit.RetrofitFactory
import retrofit2.Retrofit

object JiangNetwork {

    private var retrofit: Retrofit? = null

    fun init(config: JiangNetworkConfig) {
        val okHttpClient = OkHttpFactory.create(config)
        retrofit = RetrofitFactory.create(config, okHttpClient)
    }

    fun <T> createApi(serviceClass: Class<T>): T {
        return currentRetrofit().create(serviceClass)
    }

    private fun currentRetrofit(): Retrofit {
        return retrofit ?: error(
            "JiangNetwork has not been initialized. Please call JiangNetwork.init(config).",
        )
    }
}
