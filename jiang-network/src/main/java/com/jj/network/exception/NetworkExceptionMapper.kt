package com.jj.network.exception

import com.google.gson.JsonSyntaxException
import com.jj.common.exception.JiangErrorCode
import com.jj.common.exception.JiangException
import com.jj.common.result.JiangResult
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object NetworkExceptionMapper {

    fun map(throwable: Throwable): JiangResult.Error {
        val exception = when (throwable) {
            is SocketTimeoutException -> JiangException(
                code = JiangErrorCode.TIMEOUT,
                message = "Network request timed out.",
                throwable = throwable,
            )

            is UnknownHostException -> JiangException(
                code = JiangErrorCode.NOT_FOUND,
                message = "Network host is unavailable.",
                throwable = throwable,
            )

            is HttpException -> JiangException(
                code = throwable.code(),
                message = throwable.message(),
                throwable = throwable,
            )

            is JsonSyntaxException -> JiangException(
                code = JiangErrorCode.UNKNOWN,
                message = "Network response parse failed.",
                throwable = throwable,
            )

            is IOException -> JiangException(
                code = JiangErrorCode.UNKNOWN,
                message = throwable.message ?: "Network IO error.",
                throwable = throwable,
            )

            else -> JiangException(
                code = JiangErrorCode.UNKNOWN,
                message = throwable.message ?: "Unknown network error.",
                throwable = throwable,
            )
        }
        return JiangResult.Error(exception)
    }
}
