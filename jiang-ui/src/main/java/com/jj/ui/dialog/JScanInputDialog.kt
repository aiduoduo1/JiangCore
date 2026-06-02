package com.jj.ui.dialog

import android.content.Context
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.LifecycleOwner

class JScanInputDialog(
    context: Context,
    lifecycleOwner: LifecycleOwner? = context as? LifecycleOwner,
) : JInputDialog(context, lifecycleOwner) {

    private var onSubmit: ((String) -> Unit)? = null

    init {
        editText.imeOptions = EditorInfo.IME_ACTION_DONE
        editText.setOnEditorActionListener { _, actionId, event ->
            val isDone = actionId == EditorInfo.IME_ACTION_DONE
            val isEnter = event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP
            if (isDone || isEnter) {
                onSubmit?.invoke(editText.text.toString())
                true
            } else {
                false
            }
        }
    }

    fun setOnSubmit(onSubmit: ((String) -> Unit)?): JScanInputDialog {
        this.onSubmit = onSubmit
        setOnConfirm(onSubmit)
        return this
    }

    override fun setTitle(title: CharSequence?): JScanInputDialog {
        super.setTitle(title)
        return this
    }

    override fun setHint(hint: CharSequence?): JScanInputDialog {
        super.setHint(hint)
        return this
    }

    override fun setText(text: CharSequence?): JScanInputDialog {
        super.setText(text)
        return this
    }

    override fun setConfirmText(confirmText: CharSequence): JScanInputDialog {
        super.setConfirmText(confirmText)
        return this
    }

    override fun setCancelText(cancelText: CharSequence): JScanInputDialog {
        super.setCancelText(cancelText)
        return this
    }

    override fun setOnCancel(onCancel: (() -> Unit)?): JScanInputDialog {
        super.setOnCancel(onCancel)
        return this
    }

    fun selectAllInput(): JScanInputDialog {
        selectAll()
        return this
    }
}
