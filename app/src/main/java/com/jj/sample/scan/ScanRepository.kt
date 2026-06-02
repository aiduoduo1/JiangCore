package com.jj.sample.scan

import com.jj.common.exception.JiangErrorCode
import com.jj.common.exception.JiangException
import com.jj.common.result.JiangResult
import kotlinx.coroutines.delay

class ScanRepository {

    suspend fun checkFirstCode(code: String): JiangResult<String> {
        delay(SIMULATED_REQUEST_DELAY_MS)
        val normalizedCode = code.trim()
        return when {
            normalizedCode.isBlank() -> error("第一扫码框不能为空")
            normalizedCode.startsWith(ERROR_PREFIX, ignoreCase = true) -> error("第一码校验失败，请重新扫描")
            else -> JiangResult.Success("第一码通过: $normalizedCode")
        }
    }

    suspend fun checkSecondCode(
        firstCode: String,
        secondCode: String,
    ): JiangResult<String> {
        delay(SIMULATED_REQUEST_DELAY_MS)
        val normalizedFirstCode = firstCode.trim()
        val normalizedSecondCode = secondCode.trim()
        return when {
            normalizedFirstCode.isBlank() -> error("请先扫描第一码")
            normalizedSecondCode.isBlank() -> error("第二扫码框不能为空")
            normalizedSecondCode.startsWith(ERROR_PREFIX, ignoreCase = true) -> error("第二码校验失败，请重新扫描")
            normalizedFirstCode == normalizedSecondCode -> error("第二码不能与第一码相同")
            else -> JiangResult.Success("扫码流程完成: $normalizedFirstCode -> $normalizedSecondCode")
        }
    }

    private fun error(message: String): JiangResult.Error {
        return JiangResult.Error(
            JiangException(
                code = JiangErrorCode.PARAMS_INVALID,
                message = message,
            ),
        )
    }

    private companion object {
        const val ERROR_PREFIX = "ERR"
        const val SIMULATED_REQUEST_DELAY_MS = 500L
    }
}
