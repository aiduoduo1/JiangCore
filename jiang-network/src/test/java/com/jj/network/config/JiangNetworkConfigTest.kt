package com.jj.network.config

import org.junit.Assert.assertEquals
import org.junit.Test

class JiangNetworkConfigTest {

    @Test
    fun mergedHeadersUsesDynamicHeadersAndLetsDynamicOverrideStatic() {
        val config = JiangNetworkConfig(
            baseUrl = "https://example.com/",
            headers = mapOf(
                "MES-UP-TOKEN" to "old-token",
                "platform" to "android",
            ),
            dynamicHeadersProvider = {
                mapOf("MES-UP-TOKEN" to "new-token")
            },
        )

        assertEquals(
            mapOf(
                "MES-UP-TOKEN" to "new-token",
                "platform" to "android",
            ),
            config.mergedHeaders(),
        )
    }

    @Test
    fun logLevelDefaultsToBasicWhenDebugIsTrue() {
        val config = JiangNetworkConfig(
            baseUrl = "https://example.com/",
            debug = true,
        )

        assertEquals(JiangNetworkLogLevel.BASIC, config.logLevel)
    }

    @Test
    fun sensitiveHeadersIncludeCommonTokenHeadersByDefault() {
        val config = JiangNetworkConfig(baseUrl = "https://example.com/")

        assertEquals(
            setOf("MES-UP-TOKEN", "Authorization", "password", "pwd"),
            config.sensitiveHeaders,
        )
    }
}
