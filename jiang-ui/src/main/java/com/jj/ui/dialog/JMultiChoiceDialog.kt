package com.jj.ui.dialog

import android.content.Context
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner

class JMultiChoiceDialog<T>(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner? = context as? LifecycleOwner,
) {

    private var title: CharSequence? = null
    private var items: List<T> = emptyList()
    private var selectedIndexes: Set<Int> = emptySet()
    private var itemText: (T) -> CharSequence = { it.toString() }
    private var onConfirm: ((List<T>) -> Unit)? = null

    fun setTitle(title: CharSequence?): JMultiChoiceDialog<T> {
        this.title = title
        return this
    }

    fun setItems(items: List<T>, itemText: (T) -> CharSequence = { it.toString() }): JMultiChoiceDialog<T> {
        this.items = items
        this.itemText = itemText
        return this
    }

    fun setSelectedIndexes(indexes: Set<Int>): JMultiChoiceDialog<T> {
        selectedIndexes = indexes
        return this
    }

    fun setOnConfirm(onConfirm: ((List<T>) -> Unit)?): JMultiChoiceDialog<T> {
        this.onConfirm = onConfirm
        return this
    }

    fun show(): JBaseDialog {
        val state = JMultiChoiceState(items, selectedIndexes)
        val listView = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            items.forEachIndexed { index, item ->
                addView(
                    CheckBox(context).apply {
                        text = itemText(item)
                        isChecked = state.isSelected(index)
                        setOnCheckedChangeListener { _, _ ->
                            state.toggle(index)
                        }
                    },
                )
            }
        }
        return JBaseDialog(context, lifecycleOwner)
            .setTitle(title)
            .setContentView(listView)
            .addButton(JDialogDefaults.CANCEL_TEXT)
            .addButton(JDialogDefaults.CONFIRM_TEXT, onClick = {
                onConfirm?.invoke(state.selectedItems)
            })
            .show()
    }
}
