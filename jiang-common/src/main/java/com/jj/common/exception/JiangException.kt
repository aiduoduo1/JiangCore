package com.jj.common.exception

class JiangException(
    val code: Int,
    override val message: String,
    val throwable: Throwable? = null,
) : RuntimeException(message, throwable)
