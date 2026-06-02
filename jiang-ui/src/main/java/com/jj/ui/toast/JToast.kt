package com.jj.ui.toast

import android.app.Application
import android.widget.Toast

object JToast {

    private var application: Application? = null
    private var currentToast: Toast? = null
    private var lastMessage: CharSequence? = null
    private var lastShowTime: Long = 0L

    fun init(application: Application) {
        this.application = application
    }

    fun show(message: CharSequence) {
        showShort(message)
    }

    fun showShort(message: CharSequence) {
        showInternal(message, Toast.LENGTH_SHORT)
    }

    fun showLong(message: CharSequence) {
        showInternal(message, Toast.LENGTH_LONG)
    }

    fun showOnce(
        message: CharSequence,
        interval: Long = DEFAULT_REPEAT_INTERVAL,
    ) {
        val currentTime = android.os.SystemClock.elapsedRealtime()
        if (message == lastMessage && currentTime - lastShowTime < interval) {
            return
        }
        showInternal(message, Toast.LENGTH_SHORT, currentTime)
    }

    fun cancel() {
        currentToast?.cancel()
        currentToast = null
    }

    private fun requireApplication(): Application {
        return application
            ?: error("JToast has not been initialized. Please call JToast.init(application).")
    }

    private fun showInternal(
        message: CharSequence,
        duration: Int,
        showTime: Long = android.os.SystemClock.elapsedRealtime(),
    ) {
        currentToast?.cancel()
        currentToast = Toast.makeText(requireApplication(), message, duration).apply {
            show()
        }
        lastMessage = message
        lastShowTime = showTime
    }

    private const val DEFAULT_REPEAT_INTERVAL = 1500L
}
