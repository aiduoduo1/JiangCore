package com.jj.sample.showcase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jj.arch.state.JiangLoadingMode
import com.jj.arch.vm.JiangViewModel
import com.jj.common.exception.JiangErrorCode
import com.jj.network.JiangNetwork
import com.jj.network.call.JiangApiCaller
import com.jj.sample.SampleApi
import com.jj.sample.login.LoginRepository
import com.jj.sample.login.LoginRequest
import com.jj.storage.JiangStorage

class ShowcaseViewModel : JiangViewModel() {

    private val _message = MutableLiveData("请选择一个示例")
    val message: LiveData<String> = _message
    private val loginRepository by lazy { LoginRepository() }

    fun showLoadingDemo() {
        setLoading()
    }

    fun showEmptyDemo() {
        setEmpty()
    }

    fun showErrorDemo() {
        setError(
            code = JiangErrorCode.UNKNOWN,
            message = "这是错误状态",
        )
    }

    fun showContentDemo() {
        setSuccess("这是正常内容区域")
    }

    fun runNetworkDemo() {
        launchResult(
            block = {
                JiangApiCaller.safeApiCall {
                    JiangNetwork.createApi(SampleApi::class.java)
                    "JiangNetwork createApi + safeApiCall 调用成功"
                }
            },
            onSuccess = {
                _message.value = it
            },
        )
    }

    fun runLoginDemo() {
        launchResult(
            loadingMode = JiangLoadingMode.DIALOG,
            loadingMessage = "登录中...",
            block = {
                loginRepository.login(
                    LoginRequest(
                        account = "demo",
                        password = "123456",
                    ),
                )
            },
            onSuccess = { token ->
                _message.value = "Login success, token saved: $token"
            },
        )
    }

    fun putString() {
        JiangStorage.kv.putString(SAMPLE_KEY, "Hello Storage")
        _message.value = "写入完成: Hello Storage"
        setSuccess(_message.value.orEmpty())
    }

    fun getString() {
        val value = JiangStorage.kv.getString(SAMPLE_KEY, "未读取到数据")
        _message.value = "读取结果: $value"
        setSuccess(_message.value.orEmpty())
    }

    fun removeKey() {
        JiangStorage.kv.remove(SAMPLE_KEY)
        _message.value = "已删除 sample_key"
        setSuccess(_message.value.orEmpty())
    }

    fun clearStorage() {
        JiangStorage.kv.clear()
        _message.value = "已清空 Storage"
        setSuccess(_message.value.orEmpty())
    }

    private companion object {
        const val SAMPLE_KEY = "sample_key"
    }
}
