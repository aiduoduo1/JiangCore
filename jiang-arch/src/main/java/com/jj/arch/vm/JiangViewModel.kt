package com.jj.arch.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.arch.event.JiangEvent
import com.jj.arch.event.JiangUiEvent
import com.jj.arch.state.JiangLoadingMode
import com.jj.arch.state.JiangUiState
import com.jj.common.exception.JiangErrorCode
import com.jj.common.exception.JiangException
import com.jj.common.result.JiangResult
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

open class JiangViewModel : ViewModel() {

    private val _uiState = MutableLiveData<JiangUiState<Any>>(JiangUiState.Idle)
    val uiState: LiveData<JiangUiState<Any>> = _uiState

    private val _uiEvent = MutableLiveData<JiangEvent<Any>>()
    val uiEvent: LiveData<JiangEvent<Any>> = _uiEvent

    protected fun setLoading(
        mode: JiangLoadingMode = JiangLoadingMode.PAGE,
        message: CharSequence? = null,
    ) {
        if (mode != JiangLoadingMode.NONE) {
            _uiState.value = JiangUiState.Loading(mode, message)
        }
    }

    protected fun setEmpty() {
        _uiState.value = JiangUiState.Empty
    }

    protected fun setError(code: Int, message: String, throwable: Throwable? = null) {
        _uiState.value = JiangUiState.Error(
            code = code,
            message = message,
            throwable = throwable,
        )
    }

    protected fun setSuccess(data: Any) {
        _uiState.value = JiangUiState.Success(data)
    }

    protected fun sendEvent(event: Any) {
        _uiEvent.value = JiangEvent(event)
    }

    protected fun showToast(message: CharSequence) {
        sendEvent(JiangUiEvent.Toast(message))
    }

    protected fun showLoadingDialog(message: CharSequence) {
        sendEvent(JiangUiEvent.ShowLoadingDialog(message))
    }

    protected fun dismissLoadingDialog() {
        sendEvent(JiangUiEvent.DismissLoadingDialog)
    }

    protected fun launch(
        showLoading: Boolean = true,
        loadingMode: JiangLoadingMode = if (showLoading) {
            JiangLoadingMode.PAGE
        } else {
            JiangLoadingMode.NONE
        },
        loadingMessage: CharSequence? = null,
        block: suspend () -> Unit,
    ) {
        viewModelScope.launch {
            setLoading(loadingMode, loadingMessage)
            try {
                block()
            } catch (throwable: Throwable) {
                handleThrowable(throwable)
            }
        }
    }

    protected fun <T> handleResult(
        result: JiangResult<T>,
        onSuccess: (T) -> Unit = {},
    ) {
        when (result) {
            is JiangResult.Success -> {
                onSuccess(result.data)
                if (result.data == null) {
                    setEmpty()
                } else {
                    setSuccess(result.data as Any)
                }
            }

            is JiangResult.Error -> setError(
                code = result.exception.code,
                message = result.exception.message,
                throwable = result.exception.throwable,
            )

            JiangResult.Loading -> setLoading()
            JiangResult.Empty -> setEmpty()
        }
    }

    protected fun <T> launchResult(
        showLoading: Boolean = true,
        loadingMode: JiangLoadingMode = if (showLoading) {
            JiangLoadingMode.PAGE
        } else {
            JiangLoadingMode.NONE
        },
        loadingMessage: CharSequence? = null,
        block: suspend () -> JiangResult<T>,
        onSuccess: (T) -> Unit = {},
    ) {
        viewModelScope.launch {
            setLoading(loadingMode, loadingMessage)
            try {
                handleResult(block(), onSuccess)
            } catch (throwable: Throwable) {
                handleThrowable(throwable)
            }
        }
    }

    private fun handleThrowable(throwable: Throwable) {
        if (throwable is CancellationException) {
            throw throwable
        }

        val exception = throwable as? JiangException
        setError(
            code = exception?.code ?: JiangErrorCode.UNKNOWN,
            message = exception?.message ?: throwable.message ?: "Unknown error",
            throwable = exception?.throwable ?: throwable,
        )
    }
}
