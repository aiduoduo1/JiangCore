package com.jj.network.okhttp.interceptor

import com.jj.network.config.JiangNetworkConfig
import com.jj.network.header.JiangNetworkHeaders
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(
    private val config: JiangNetworkConfig,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val skipToken = chain.request().header(JiangNetworkHeaders.NO_TOKEN).toBoolean()
        val requestBuilder = chain.request().newBuilder()
            .removeHeader(JiangNetworkHeaders.NO_TOKEN)
        config.mergedHeaders()
            .filterNot { (name, _) ->
                skipToken && name.equals(JiangNetworkHeaders.MES_UP_TOKEN, ignoreCase = true)
            }
            .forEach { (name, value) ->
                requestBuilder.header(name, value)
            }
        return chain.proceed(requestBuilder.build())
    }
}
