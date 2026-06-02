package com.jj.ui.dialog

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner

class JLoadingDialog(
    context: Context,
    lifecycleOwner: LifecycleOwner? = context as? LifecycleOwner,
    message: CharSequence = JDialogDefaults.LOADING_MESSAGE,
    cancelable: Boolean = true,
) {

    private val baseDialog = JBaseDialog(
        context = context,
        lifecycleOwner = lifecycleOwner,
        cancelable = cancelable,
    )

    private val messageView: TextView = TextView(context).apply {
        text = message
        textSize = 16f
        gravity = Gravity.CENTER
        setPadding(16.dp(context), 0, 0, 0)
    }

    init {
        val contentView = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(4.dp(context), 8.dp(context), 4.dp(context), 8.dp(context))
            addView(
                ProgressBar(context),
                LinearLayout.LayoutParams(32.dp(context), 32.dp(context)),
            )
            addView(
                messageView,
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                ),
            )
        }
        baseDialog.setContentView(contentView)
        baseDialog.clearButtons()
    }

    fun setMessage(message: CharSequence): JLoadingDialog {
        messageView.text = message
        return this
    }

    fun setCancelable(cancelable: Boolean): JLoadingDialog {
        baseDialog.setCancelable(cancelable)
        return this
    }

    fun show(): JLoadingDialog {
        baseDialog.show()
        return this
    }

    fun dismiss() {
        baseDialog.dismiss()
    }

    private fun Int.dp(context: Context): Int {
        return (this * context.resources.displayMetrics.density + 0.5f).toInt()
    }
}
