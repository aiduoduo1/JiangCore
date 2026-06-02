package com.jj.sample.login

data class MesBaseResult<T>(
    val code: Int,
    val msg: String? = null,
    val data: T? = null,
) {

    fun isSuccess(): Boolean {
        return code == CODE_SUCCESS
    }

    companion object {
        const val CODE_SUCCESS = 0
    }
}
