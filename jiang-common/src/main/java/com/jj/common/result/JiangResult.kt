package com.jj.common.result

import com.jj.common.exception.JiangException

sealed class JiangResult<out T> {

    data class Success<T>(val data: T) : JiangResult<T>()

    data class Error(val exception: JiangException) : JiangResult<Nothing>()

    data object Loading : JiangResult<Nothing>()

    data object Empty : JiangResult<Nothing>()
}
