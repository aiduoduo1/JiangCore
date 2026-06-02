package com.jj.network.exception

import com.jj.common.exception.JiangErrorCode
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

class NetworkExceptionMapperTest {

    @Test
    fun mapHttp401CallsUnauthorizedHandler() {
        var unauthorizedCode: Int? = null
        val throwable = HttpException(
            Response.error<String>(
                JiangErrorCode.UNAUTHORIZED,
                "expired".toResponseBody("text/plain".toMediaType()),
            ),
        )

        val result = NetworkExceptionMapper.map(
            throwable = throwable,
            unauthorizedHandler = { unauthorizedCode = it.code },
        )

        assertEquals(JiangErrorCode.UNAUTHORIZED, result.exception.code)
        assertEquals(JiangErrorCode.UNAUTHORIZED, unauthorizedCode)
    }

    @Test
    fun mapUnknownHostAsNetworkUnavailable() {
        val throwable = UnknownHostException("host")

        val result = NetworkExceptionMapper.map(throwable)

        assertEquals(JiangErrorCode.NETWORK_UNAVAILABLE, result.exception.code)
        assertSame(throwable, result.exception.throwable)
    }
}
