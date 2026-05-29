package com.jj.arch.base

import androidx.viewbinding.ViewBinding
import com.jj.arch.state.JiangUiState
import com.jj.arch.vm.JiangViewModel

abstract class JiangStateVmActivity<VB : ViewBinding, VM : JiangViewModel> : JiangVmActivity<VB, VM>() {

    final override fun initObserver() {
        super.initObserver()
        initUiStateObserver()
        initPageObserver()
    }

    protected open fun initUiStateObserver() {
        viewModel.uiState.observe(this) { state ->
            dispatchUiState(state)
        }
    }

    protected open fun initPageObserver() = Unit

    protected open fun dispatchUiState(state: JiangUiState<Any>) {
        when (state) {
            JiangUiState.Idle -> onUiIdle()
            JiangUiState.Loading -> onUiLoading()
            is JiangUiState.Success -> onUiSuccess(state.data)
            is JiangUiState.Error -> onUiError(state.code, state.message, state.throwable)
            JiangUiState.Empty -> onUiEmpty()
        }
    }

    protected open fun onUiIdle() = Unit

    protected open fun onUiLoading() = Unit

    protected open fun onUiSuccess(data: Any) = Unit

    protected open fun onUiError(code: Int, message: String, throwable: Throwable?) = Unit

    protected open fun onUiEmpty() = Unit
}
