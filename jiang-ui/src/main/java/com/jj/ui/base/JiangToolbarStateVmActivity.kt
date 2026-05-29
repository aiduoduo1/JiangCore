package com.jj.ui.base

import android.view.View
import androidx.viewbinding.ViewBinding
import com.jj.arch.state.JiangUiState
import com.jj.arch.vm.JiangViewModel
import com.jj.ui.state.JiangStateLayout

abstract class JiangToolbarStateVmActivity<VB : ViewBinding, VM : JiangViewModel> :
    JiangToolbarVmActivity<VB, VM>() {

    private var stateLayout: JiangStateLayout? = null

    override fun createRootView(binding: VB): View {
        if (!enableStateLayout()) {
            return super.createRootView(binding)
        }

        stateLayout = JiangStateLayout(this).apply {
            setContentView(binding.root)
            setOnRetryClickListener {
                onStateRetryClick()
            }
        }

        return super.createRootView(
            object : ViewBinding {
                override fun getRoot(): View = requireStateLayout()
            } as VB,
        )
    }

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

    protected open fun enableStateLayout(): Boolean = true

    protected fun showContentPage() {
        stateLayout?.showContent()
    }

    protected fun showLoadingPage() {
        stateLayout?.showLoading()
    }

    protected fun showEmptyPage(text: CharSequence? = null) {
        stateLayout?.showEmpty(text)
    }

    protected fun showErrorPage(text: CharSequence? = null) {
        stateLayout?.showError(text)
    }

    protected open fun onStateRetryClick() = Unit

    protected open fun onUiIdle() {
        showContentPage()
    }

    protected open fun onUiLoading() {
        showLoadingPage()
    }

    protected open fun onUiSuccess(data: Any) {
        showContentPage()
    }

    protected open fun onUiError(code: Int, message: String, throwable: Throwable?) {
        showErrorPage(message)
    }

    protected open fun onUiEmpty() {
        showEmptyPage()
    }

    private fun requireStateLayout(): JiangStateLayout {
        return stateLayout ?: error("JiangStateLayout is not initialized.")
    }
}
