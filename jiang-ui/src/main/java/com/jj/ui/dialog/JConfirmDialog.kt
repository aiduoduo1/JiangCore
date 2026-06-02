package com.jj.ui.dialog

import android.content.Context
import android.view.Gravity
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner

class JConfirmDialog(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner? = context as? LifecycleOwner,
) {

    private var title: CharSequence? = null
    private var message: CharSequence? = null
    private var confirmText: CharSequence = JDialogDefaults.CONFIRM_TEXT
    private var cancelText: CharSequence = JDialogDefaults.CANCEL_TEXT
    private var cancelable: Boolean = true
    private var showCancelButton: Boolean = true
    private var onConfirm: (() -> Unit)? = null
    private var onCancel: (() -> Unit)? = null
    private var onDismiss: (() -> Unit)? = null

    fun setTitle(title: CharSequence?): JConfirmDialog {
        this.title = title
        return this
    }

    fun setMessage(message: CharSequence?): JConfirmDialog {
        this.message = message
        return this
    }

    fun setConfirmText(confirmText: CharSequence): JConfirmDialog {
        this.confirmText = confirmText
        return this
    }

    fun setCancelText(cancelText: CharSequence): JConfirmDialog {
        this.cancelText = cancelText
        return this
    }

    fun setCancelable(cancelable: Boolean): JConfirmDialog {
        this.cancelable = cancelable
        return this
    }

    fun setShowCancelButton(showCancelButton: Boolean): JConfirmDialog {
        this.showCancelButton = showCancelButton
        return this
    }

    fun setOnConfirm(onConfirm: (() -> Unit)?): JConfirmDialog {
        this.onConfirm = onConfirm
        return this
    }

    fun setOnCancel(onCancel: (() -> Unit)?): JConfirmDialog {
        this.onCancel = onCancel
        return this
    }

    fun setOnDismiss(onDismiss: (() -> Unit)?): JConfirmDialog {
        this.onDismiss = onDismiss
        return this
    }

    fun show(): JBaseDialog {
        val messageView = TextView(context).apply {
            text = message ?: ""
            gravity = Gravity.CENTER_VERTICAL
            textSize = 16f
        }
        return JBaseDialog(context, lifecycleOwner, cancelable)
            .setTitle(title)
            .setContentView(messageView)
            .setOnCancel(onCancel)
            .setOnDismiss(onDismiss)
            .apply {
                if (showCancelButton) {
                    addButton(cancelText, onCancel)
                }
                addButton(confirmText, onConfirm)
            }
            .show()
    }
}
