package com.jj.network.config

import com.jj.common.exception.JiangException
import com.jj.network.header.JiangNetworkHeaders

data class JiangNetworkConfig(
    val baseUrl: String,
    val connectTimeoutSeconds: Long = 15,
    val readTimeoutSeconds: Long = 15,
    val writeTimeoutSeconds: Long = 15,
    val headers: Map<String, String> = emptyMap(),
    val dynamicHeadersProvider: () -> Map<String, String> = { emptyMap() },
    val debug: Boolean = false,
    val logLevel: JiangNetworkLogLevel = if (debug) {
        JiangNetworkLogLevel.BASIC
    } else {
        JiangNetworkLogLevel.NONE
    },
    val sensitiveHeaders: Set<String> = setOf(
        JiangNetworkHeaders.MES_UP_TOKEN,
        JiangNetworkHeaders.AUTHORIZATION,
        "password",
        "pwd",
    ),
    val unauthorizedHandler: ((JiangException) -> Unit)? = null,
) {

    fun mergedHeaders(): Map<String, String> {
        return headers + dynamicHeadersProvider()
    }
}
