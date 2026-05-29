package com.jj.network.config

data class JiangNetworkConfig(
    val baseUrl: String,
    val connectTimeoutSeconds: Long = 15,
    val readTimeoutSeconds: Long = 15,
    val writeTimeoutSeconds: Long = 15,
    val headers: Map<String, String> = emptyMap(),
    val debug: Boolean = false,
)
