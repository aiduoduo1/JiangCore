package com.jj.network

import com.jj.network.config.JiangNetworkConfig
import com.jj.network.download.JiangApkDownloader
import com.jj.network.download.JiangDownloadApi
import com.jj.network.download.JiangFileDownloader
import com.jj.network.exception.NetworkExceptionMapper
import com.jj.network.header.JiangNetworkHeaders
import com.jj.network.okhttp.OkHttpFactory
import com.jj.network.retrofit.RetrofitFactory
import retrofit2.Retrofit

object JiangNetwork {

    private var retrofit: Retrofit? = null
    private var config: JiangNetworkConfig? = null

    fun init(config: JiangNetworkConfig) {
        val okHttpClient = OkHttpFactory.create(config)
        retrofit = RetrofitFactory.create(config, okHttpClient)
        this.config = config
        NetworkExceptionMapper.setUnauthorizedHandler(config.unauthorizedHandler)
    }

    fun <T> createApi(serviceClass: Class<T>): T {
        return currentRetrofit().create(serviceClass)
    }

    fun createFileDownloader(): JiangFileDownloader {
        return JiangFileDownloader(
            api = createApi(JiangDownloadApi::class.java),
            mesUpTokenProvider = {
                currentConfig().mergedHeaders()[JiangNetworkHeaders.MES_UP_TOKEN]
            },
        )
    }

    fun createApkDownloader(): JiangApkDownloader {
        return JiangApkDownloader(createFileDownloader())
    }

    private fun currentRetrofit(): Retrofit {
        return retrofit ?: error(
            "JiangNetwork has not been initialized. Please call JiangNetwork.init(config).",
        )
    }

    private fun currentConfig(): JiangNetworkConfig {
        return config ?: error(
            "JiangNetwork has not been initialized. Please call JiangNetwork.init(config).",
        )
    }
}
