package com.jj.sample.login

import com.jj.common.exception.JiangErrorCode
import com.jj.common.exception.JiangException
import com.jj.common.result.JiangResult
import com.jj.network.JiangNetwork
import com.jj.network.call.JiangApiCaller
import com.jj.storage.JiangStorage

class LoginRepository(
    private val api: LoginApi = JiangNetwork.createApi(LoginApi::class.java),
) {

    suspend fun login(request: LoginRequest): JiangResult<String> {
        return when (val result = JiangApiCaller.safeApiCall { api.login(request) }) {
            is JiangResult.Success -> handleLoginResult(
                result.data.toJiangResult(
                    emptyMessage = "Login token is empty.",
                ),
            )
            is JiangResult.Error -> result
            JiangResult.Loading -> JiangResult.Loading
            JiangResult.Empty -> JiangResult.Empty
        }
    }

    private fun handleLoginResult(result: JiangResult<String>): JiangResult<String> {
        if (result is JiangResult.Success && result.data.isBlank()) {
            return JiangResult.Error(
                JiangException(
                    code = JiangErrorCode.UNKNOWN,
                    message = "Login token is empty.",
                ),
            )
        }
        if (result is JiangResult.Success) {
            JiangStorage.kv.putString(KEY_MES_UP_TOKEN, result.data)
        }
        return result
    }

    private companion object {
        const val KEY_MES_UP_TOKEN = "mes_up_token"
    }
}
