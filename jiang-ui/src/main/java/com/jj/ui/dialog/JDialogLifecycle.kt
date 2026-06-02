package com.jj.ui.dialog

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

internal fun Context.findActivity(): Activity? {
    var current: Context? = this
    while (current is ContextWrapper) {
        if (current is Activity) {
            return current
        }
        current = current.baseContext
    }
    return null
}

internal fun Context.isActivityAlive(): Boolean {
    val activity = findActivity() ?: return true
    return !activity.isFinishing && !activity.isDestroyed
}

internal fun LifecycleOwner?.isDialogLifecycleAlive(): Boolean {
    return this == null || lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)
}
