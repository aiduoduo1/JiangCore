package com.jj.network.okhttp.interceptor

import com.jj.network.config.JiangNetworkConfig
import com.jj.network.header.JiangNetworkHeaders
import okhttp3.Call
import okhttp3.Connection
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.util.concurrent.TimeUnit

class HeaderInterceptorTest {

    @Test
    fun noTokenHeaderSkipsMesUpTokenAndRemovesInternalHeader() {
        val config = JiangNetworkConfig(
            baseUrl = "https://example.com/",
            headers = mapOf("MES-UP-TOKEN" to "token"),
        )
        val request = Request.Builder()
            .url("https://example.com/user")
            .header(JiangNetworkHeaders.NO_TOKEN, "true")
            .build()
        val chain = FakeChain(request)

        HeaderInterceptor(config).intercept(chain)

        assertNull(chain.proceededRequest?.header("MES-UP-TOKEN"))
        assertNull(chain.proceededRequest?.header(JiangNetworkHeaders.NO_TOKEN))
    }

    @Test
    fun keepsMesUpTokenWhenNoTokenHeaderIsAbsent() {
        val config = JiangNetworkConfig(
            baseUrl = "https://example.com/",
            headers = mapOf("MES-UP-TOKEN" to "token"),
        )
        val chain = FakeChain(
            Request.Builder()
                .url("https://example.com/user")
                .build(),
        )

        HeaderInterceptor(config).intercept(chain)

        assertEquals("token", chain.proceededRequest?.header("MES-UP-TOKEN"))
    }

    private class FakeChain(
        private val request: Request,
    ) : Interceptor.Chain {

        var proceededRequest: Request? = null

        override fun request(): Request = request

        override fun proceed(request: Request): Response {
            proceededRequest = request
            return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .build()
        }

        override fun connection(): Connection? = null

        override fun call(): Call {
            throw UnsupportedOperationException()
        }

        override fun connectTimeoutMillis(): Int = 0

        override fun withConnectTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain = this

        override fun readTimeoutMillis(): Int = 0

        override fun withReadTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain = this

        override fun writeTimeoutMillis(): Int = 0

        override fun withWriteTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain = this
    }
}
