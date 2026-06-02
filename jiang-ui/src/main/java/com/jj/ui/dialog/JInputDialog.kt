package com.jj.ui.dialog

import android.content.Context
import android.text.InputType
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.LifecycleOwner

open class JInputDialog(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner? = context as? LifecycleOwner,
) {

    protected val editText: EditText = EditText(context).apply {
        setSingleLine(true)
        inputType = InputType.TYPE_CLASS_TEXT
    }

    private var title: CharSequence? = null
    private var confirmText: CharSequence = JDialogDefaults.CONFIRM_TEXT
    private var cancelText: CharSequence = JDialogDefaults.CANCEL_TEXT
    private var onConfirm: ((String) -> Unit)? = null
    private var onCancel: (() -> Unit)? = null
    private var dialog: JBaseDialog? = null

    open fun setTitle(title: CharSequence?): JInputDialog {
        this.title = title
        return this
    }

    open fun setHint(hint: CharSequence?): JInputDialog {
        editText.hint = hint
        return this
    }

    open fun setText(text: CharSequence?): JInputDialog {
        editText.setText(text)
        editText.setSelection(editText.text.length)
        return this
    }

    open fun setInputType(inputType: Int): JInputDialog {
        editText.inputType = inputType
        return this
    }

    open fun setConfirmText(confirmText: CharSequence): JInputDialog {
        this.confirmText = confirmText
        return this
    }

    open fun setCancelText(cancelText: CharSequence): JInputDialog {
        this.cancelText = cancelText
        return this
    }

    fun setOnConfirm(onConfirm: ((String) -> Unit)?): JInputDialog {
        this.onConfirm = onConfirm
        return this
    }

    open fun setOnCancel(onCancel: (() -> Unit)?): JInputDialog {
        this.onCancel = onCancel
        return this
    }

    fun selectAll(): JInputDialog {
        editText.selectAll()
        return this
    }

    open fun show(): JBaseDialog {
        dialog = JBaseDialog(context, lifecycleOwner)
            .setTitle(title)
            .setContentView(editText)
            .addButton(cancelText, onCancel)
            .addButton(confirmText, onClick = {
                onConfirm?.invoke(editText.text.toString())
            })
            .show()
        editText.post {
            editText.requestFocus()
            val inputMethodManager = context.getSystemService(InputMethodManager::class.java)
            inputMethodManager?.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }
        return dialog ?: error("Input dialog was not created.")
    }
}
