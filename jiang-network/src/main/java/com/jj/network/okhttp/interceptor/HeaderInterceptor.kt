package com.jj.network.okhttp.interceptor

import com.jj.network.config.JiangNetworkConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(
    private val config: JiangNetworkConfig,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        config.headers.forEach { (name, value) ->
            requestBuilder.header(name, value)
        }
        return chain.proceed(requestBuilder.build())
    }
}
