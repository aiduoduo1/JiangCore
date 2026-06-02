package com.jj.arch.event

sealed class JiangUiEvent {
    data class Toast(val message: CharSequence) : JiangUiEvent()
    data class ShowLoadingDialog(val message: CharSequence) : JiangUiEvent()
    data object DismissLoadingDialog : JiangUiEvent()
}
