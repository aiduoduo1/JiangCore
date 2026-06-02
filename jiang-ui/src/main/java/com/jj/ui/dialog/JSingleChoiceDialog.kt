package com.jj.ui.dialog

import android.content.Context
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.LifecycleOwner

class JSingleChoiceDialog<T>(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner? = context as? LifecycleOwner,
) {

    private var title: CharSequence? = null
    private var items: List<T> = emptyList()
    private var selectedIndex: Int = -1
    private var itemText: (T) -> CharSequence = { it.toString() }
    private var onConfirm: ((T?) -> Unit)? = null

    fun setTitle(title: CharSequence?): JSingleChoiceDialog<T> {
        this.title = title
        return this
    }

    fun setItems(items: List<T>, itemText: (T) -> CharSequence = { it.toString() }): JSingleChoiceDialog<T> {
        this.items = items
        this.itemText = itemText
        return this
    }

    fun setSelectedIndex(index: Int): JSingleChoiceDialog<T> {
        selectedIndex = index
        return this
    }

    fun setOnConfirm(onConfirm: ((T?) -> Unit)?): JSingleChoiceDialog<T> {
        this.onConfirm = onConfirm
        return this
    }

    fun show(): JBaseDialog {
        val state = JSingleChoiceState(items, selectedIndex)
        val radioGroup = RadioGroup(context).apply {
            orientation = RadioGroup.VERTICAL
            items.forEachIndexed { index, item ->
                addView(
                    RadioButton(context).apply {
                        text = itemText(item)
                        id = index + RADIO_ID_OFFSET
                        isChecked = index == selectedIndex
                    },
                )
            }
            setOnCheckedChangeListener { _, checkedId ->
                state.select(checkedId - RADIO_ID_OFFSET)
            }
        }
        return JBaseDialog(context, lifecycleOwner)
            .setTitle(title)
            .setContentView(radioGroup)
            .addButton(JDialogDefaults.CANCEL_TEXT)
            .addButton(JDialogDefaults.CONFIRM_TEXT, onClick = {
                onConfirm?.invoke(state.selectedItem)
            })
            .show()
    }

    private companion object {
        const val RADIO_ID_OFFSET = 1000
    }
}
