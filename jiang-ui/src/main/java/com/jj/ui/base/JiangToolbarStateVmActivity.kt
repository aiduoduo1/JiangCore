package com.jj.ui.base

import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import com.jj.arch.event.JiangUiEvent
import com.jj.arch.state.JiangLoadingMode
import com.jj.arch.state.JiangUiState
import com.jj.arch.vm.JiangViewModel
import com.jj.ui.dialog.JConfirmDialog
import com.jj.ui.dialog.JDialogDefaults
import com.jj.ui.dialog.JLoadingDialog
import com.jj.ui.state.JiangErrorMode
import com.jj.ui.state.JiangStateLayout
import com.jj.ui.toast.JToast

abstract class JiangToolbarStateVmActivity<VB : ViewBinding, VM : JiangViewModel> :
    JiangToolbarVmActivity<VB, VM>() {

    private var stateLayout: JiangStateLayout? = null
    private var loadingDialog: JLoadingDialog? = null

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

        return createToolbarRootView(requireStateLayout())
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
        viewModel.uiEvent.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                dispatchUiEvent(it)
            }
        }
    }

    protected open fun initPageObserver() = Unit

    protected open fun dispatchUiState(state: JiangUiState<Any>) {
        when (state) {
            JiangUiState.Idle -> onUiIdle()
            is JiangUiState.Loading -> onUiLoading(state.mode, state.message)
            is JiangUiState.Success -> onUiSuccess(state.data)
            is JiangUiState.Error -> onUiError(state.code, state.message, state.throwable)
            JiangUiState.Empty -> onUiEmpty()
        }
    }

    protected open fun dispatchUiEvent(event: Any) {
        when (event) {
            is JiangUiEvent.Toast -> showToast(event.message)
            is JiangUiEvent.ShowLoadingDialog -> showLoadingDialog(event.message)
            JiangUiEvent.DismissLoadingDialog -> dismissLoadingDialog()
            else -> Unit
        }
    }

    protected open fun enableStateLayout(): Boolean = true

    protected open fun errorMode(): JiangErrorMode = JiangErrorMode.PAGE

    protected open fun defaultLoadingMode(): JiangLoadingMode = JiangLoadingMode.PAGE

    protected fun observeText(source: LiveData<out CharSequence>, target: TextView) {
        source.observe(this) {
            target.text = it
        }
    }

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

    protected fun showLoadingDialog(message: CharSequence = JDialogDefaults.LOADING_MESSAGE) {
        if (loadingDialog == null) {
            loadingDialog = JLoadingDialog(this, this, message)
        } else {
            loadingDialog?.setMessage(message)
        }
        loadingDialog?.show()
    }

    protected fun dismissLoadingDialog() {
        loadingDialog?.dismiss()
    }

    protected fun showConfirmDialog(
        title: CharSequence? = null,
        message: CharSequence? = null,
        confirmText: CharSequence = JDialogDefaults.CONFIRM_TEXT,
        cancelText: CharSequence = JDialogDefaults.CANCEL_TEXT,
        onConfirm: (() -> Unit)? = null,
        onCancel: (() -> Unit)? = null,
    ) {
        JConfirmDialog(this, this)
            .setTitle(title)
            .setMessage(message)
            .setConfirmText(confirmText)
            .setCancelText(cancelText)
            .setOnConfirm(onConfirm)
            .setOnCancel(onCancel)
            .show()
    }

    protected fun showToast(message: CharSequence) {
        JToast.show(message)
    }

    protected open fun onUiIdle() {
        showContentPage()
    }

    protected open fun onUiLoading(
        mode: JiangLoadingMode = defaultLoadingMode(),
        message: CharSequence? = null,
    ) {
        when (mode) {
            JiangLoadingMode.NONE -> Unit
            JiangLoadingMode.PAGE -> showLoadingPage()
            JiangLoadingMode.DIALOG -> showLoadingDialog(message ?: JDialogDefaults.LOADING_MESSAGE)
        }
    }

    protected open fun onUiSuccess(data: Any) {
        dismissLoadingDialog()
        showContentPage()
    }

    protected open fun onUiError(code: Int, message: String, throwable: Throwable?) {
        dismissLoadingDialog()
        when (errorMode()) {
            JiangErrorMode.PAGE -> showErrorPage(message)
            JiangErrorMode.TOAST -> {
                showContentPage()
                showToast(message)
            }

            JiangErrorMode.NONE -> showContentPage()
        }
    }

    protected open fun onUiEmpty() {
        dismissLoadingDialog()
        showEmptyPage()
    }

    override fun onDestroy() {
        dismissLoadingDialog()
        loadingDialog = null
        super.onDestroy()
    }

    private fun requireStateLayout(): JiangStateLayout {
        return stateLayout ?: error("JiangStateLayout is not initialized.")
    }
}
