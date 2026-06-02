package com.jj.ui.dialog

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner

class JListDialog<T>(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner? = context as? LifecycleOwner,
) {

    private var title: CharSequence? = null
    private var items: List<T> = emptyList()
    private var itemText: (T) -> CharSequence = { it.toString() }
    private var onItemClick: ((T) -> Unit)? = null

    fun setTitle(title: CharSequence?): JListDialog<T> {
        this.title = title
        return this
    }

    fun setItems(items: List<T>, itemText: (T) -> CharSequence = { it.toString() }): JListDialog<T> {
        this.items = items
        this.itemText = itemText
        return this
    }

    fun setOnItemClick(onItemClick: ((T) -> Unit)?): JListDialog<T> {
        this.onItemClick = onItemClick
        return this
    }

    fun show(): JBaseDialog {
        lateinit var baseDialog: JBaseDialog
        val listView = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            items.forEach { item ->
                addView(
                    TextView(context).apply {
                        text = itemText(item)
                        textSize = 16f
                        setPadding(8.dp(), 12.dp(), 8.dp(), 12.dp())
                        setOnClickListener {
                            onItemClick?.invoke(item)
                            baseDialog.dismiss()
                        }
                    },
                )
            }
        }
        baseDialog = JBaseDialog(context, lifecycleOwner)
            .setTitle(title)
            .setContentView(listView)
            .clearButtons()
            .show()
        return baseDialog
    }

    private fun Int.dp(): Int {
        return (this * context.resources.displayMetrics.density + 0.5f).toInt()
    }
}
