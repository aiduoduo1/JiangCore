package com.jj.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.jj.ui.ext.clickNoRepeat

open class JBaseDialog(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner? = context as? LifecycleOwner,
    cancelable: Boolean = true,
) : DefaultLifecycleObserver {

    private val rootLayout: LinearLayout
    private val titleView: TextView
    private val contentContainer: LinearLayout
    private val buttonContainer: LinearLayout

    protected val dialog: Dialog = Dialog(context).apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(cancelable)
    }

    init {
        lifecycleOwner?.lifecycle?.addObserver(this)
        titleView = TextView(context).apply {
            gravity = Gravity.CENTER
            textSize = 18f
            setTextColor(Color.BLACK)
            visibility = View.GONE
            setPadding(20.dp(), 20.dp(), 20.dp(), 8.dp())
        }
        contentContainer = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20.dp(), 12.dp(), 20.dp(), 12.dp())
        }
        buttonContainer = LinearLayout(context).apply {
            gravity = Gravity.END
            orientation = LinearLayout.HORIZONTAL
            setPadding(12.dp(), 4.dp(), 12.dp(), 12.dp())
        }
        rootLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            addView(
                titleView,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                ),
            )
            addView(
                contentContainer,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                ),
            )
            addView(
                buttonContainer,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                ),
            )
        }
        dialog.setContentView(rootLayout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun setTitle(title: CharSequence?): JBaseDialog {
        titleView.text = title
        titleView.visibility = if (title.isNullOrBlank()) View.GONE else View.VISIBLE
        return this
    }

    fun setContentView(view: View): JBaseDialog {
        contentContainer.removeAllViews()
        contentContainer.addView(
            view,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            ),
        )
        return this
    }

    fun setCancelable(cancelable: Boolean): JBaseDialog {
        dialog.setCancelable(cancelable)
        return this
    }

    fun setOnDismiss(onDismiss: (() -> Unit)?): JBaseDialog {
        dialog.setOnDismissListener {
            onDismiss?.invoke()
        }
        return this
    }

    fun setOnCancel(onCancel: (() -> Unit)?): JBaseDialog {
        dialog.setOnCancelListener {
            onCancel?.invoke()
        }
        return this
    }

    fun clearButtons(): JBaseDialog {
        buttonContainer.removeAllViews()
        return this
    }

    fun addButton(
        text: CharSequence,
        onClick: (() -> Unit)? = null,
        dismissAfterClick: Boolean = true,
    ): JBaseDialog {
        val button = Button(context).apply {
            this.text = text
            clickNoRepeat {
                onClick?.invoke()
                if (dismissAfterClick) {
                    dismiss()
                }
            }
        }
        buttonContainer.addView(
            button,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            ),
        )
        return this
    }

    open fun show(): JBaseDialog {
        if (context.isActivityAlive() && lifecycleOwner.isDialogLifecycleAlive() && !dialog.isShowing) {
            dialog.show()
        }
        return this
    }

    fun dismiss() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        dismiss()
        owner.lifecycle.removeObserver(this)
    }

    protected fun Int.dp(): Int {
        return (this * context.resources.displayMetrics.density + 0.5f).toInt()
    }
}
