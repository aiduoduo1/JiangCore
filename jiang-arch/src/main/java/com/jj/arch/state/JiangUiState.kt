package com.jj.arch.state

sealed class JiangUiState<out T> {

    data object Idle : JiangUiState<Nothing>()

    data class Loading(
        val mode: JiangLoadingMode = JiangLoadingMode.PAGE,
        val message: CharSequence? = null,
    ) : JiangUiState<Nothing>()

    data class Success<T>(val data: T) : JiangUiState<T>()

    data class Error(
        val code: Int,
        val message: String,
        val throwable: Throwable? = null,
    ) : JiangUiState<Nothing>()

    data object Empty : JiangUiState<Nothing>()
}
