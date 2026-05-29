package com.jj.network.call

import com.jj.common.result.JiangResult
import com.jj.network.exception.NetworkExceptionMapper

object JiangApiCaller {

    suspend fun <T> safeApiCall(block: suspend () -> T): JiangResult<T> {
        return try {
            JiangResult.Success(block())
        } catch (throwable: Throwable) {
            NetworkExceptionMapper.map(throwable)
        }
    }
}
