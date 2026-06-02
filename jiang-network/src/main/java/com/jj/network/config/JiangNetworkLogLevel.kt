package com.jj.network.config

import okhttp3.logging.HttpLoggingInterceptor

enum class JiangNetworkLogLevel {
    NONE,
    BASIC,
    HEADERS,
    BODY,
}

internal fun JiangNetworkLogLevel.toOkHttpLevel(): HttpLoggingInterceptor.Level {
    return when (this) {
        JiangNetworkLogLevel.NONE -> HttpLoggingInterceptor.Level.NONE
        JiangNetworkLogLevel.BASIC -> HttpLoggingInterceptor.Level.BASIC
        JiangNetworkLogLevel.HEADERS -> HttpLoggingInterceptor.Level.HEADERS
        JiangNetworkLogLevel.BODY -> HttpLoggingInterceptor.Level.BODY
    }
}
