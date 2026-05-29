package com.jj.network.okhttp

import com.jj.network.config.JiangNetworkConfig
import com.jj.network.okhttp.interceptor.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpFactory {

    fun create(config: JiangNetworkConfig): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(config.connectTimeoutSeconds, TimeUnit.SECONDS)
            .readTimeout(config.readTimeoutSeconds, TimeUnit.SECONDS)
            .writeTimeout(config.writeTimeoutSeconds, TimeUnit.SECONDS)
            .addInterceptor(HeaderInterceptor(config))
            .apply {
                if (config.debug) {
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BASIC
                        },
                    )
                }
            }
            .build()
    }
}
