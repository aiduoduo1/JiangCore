package com.jj.common.log

import android.util.Log

object JiangLog {

    var enable: Boolean = true

    fun d(tag: String, message: String, throwable: Throwable? = null) {
        if (enable) {
            Log.d(tag, message, throwable)
        }
    }

    fun i(tag: String, message: String, throwable: Throwable? = null) {
        if (enable) {
            Log.i(tag, message, throwable)
        }
    }

    fun w(tag: String, message: String, throwable: Throwable? = null) {
        if (enable) {
            Log.w(tag, message, throwable)
        }
    }

    fun e(tag: String, message: String, throwable: Throwable? = null) {
        if (enable) {
            Log.e(tag, message, throwable)
        }
    }
}
