package com.jj.ui.ext

import android.os.SystemClock
import android.view.View

fun View.clickNoRepeat(
    interval: Long = 500L,
    block: (View) -> Unit,
) {
    var lastClickTime = 0L
    setOnClickListener { view ->
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - lastClickTime >= interval) {
            lastClickTime = currentTime
            block(view)
        }
    }
}
