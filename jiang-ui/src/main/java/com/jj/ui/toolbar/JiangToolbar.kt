package com.jj.ui.toolbar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView

data class JiangToolbarMenuItem(
    val id: Int,
    val title: CharSequence,
)

class JiangToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private val backView: TextView
    private val titleView: TextView
    private val actionContainer: FrameLayout
    private val menuView: TextView
    private val menuItems = mutableListOf<JiangToolbarMenuItem>()
    private var menuClickListener: ((JiangToolbarMenuItem) -> Unit)? = null

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        setBackgroundColor(Color.WHITE)
        minimumHeight = DEFAULT_HEIGHT_DP.dp()

        backView = TextView(context).apply {
            gravity = Gravity.CENTER
            text = "<"
            textSize = 24f
            setTextColor(Color.BLACK)
        }
        addView(
            backView,
            LayoutParams(DEFAULT_HEIGHT_DP.dp(), LayoutParams.MATCH_PARENT),
        )

        titleView = TextView(context).apply {
            gravity = Gravity.CENTER
            textSize = 18f
            setTextColor(Color.BLACK)
            maxLines = 1
        }
        addView(
            titleView,
            LayoutParams(0, LayoutParams.MATCH_PARENT, 1f),
        )

        actionContainer = FrameLayout(context).apply {
            visibility = View.INVISIBLE
        }
        addView(
            actionContainer,
            LayoutParams(DEFAULT_HEIGHT_DP.dp(), LayoutParams.MATCH_PARENT),
        )

        menuView = TextView(context).apply {
            gravity = Gravity.CENTER
            text = "\u22EE"
            textSize = 24f
            setTextColor(Color.BLACK)
            setOnClickListener {
                showMenu()
            }
        }
        actionContainer.addView(
            menuView,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT,
            ),
        )
    }

    fun setTitleText(title: CharSequence) {
        titleView.text = title
    }

    fun setBackVisible(visible: Boolean) {
        backView.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    fun setOnBackClickListener(listener: OnClickListener?) {
        backView.setOnClickListener(listener)
    }

    fun addMenuItem(
        id: Int,
        title: CharSequence,
        listener: ((JiangToolbarMenuItem) -> Unit)? = null,
    ) {
        menuItems += JiangToolbarMenuItem(id = id, title = title)
        if (listener != null) {
            menuClickListener = listener
        }
        updateMenuVisible()
    }

    fun setMenuItems(
        items: List<JiangToolbarMenuItem>,
        listener: ((JiangToolbarMenuItem) -> Unit)? = null,
    ) {
        menuItems.clear()
        menuItems += items
        menuClickListener = listener
        updateMenuVisible()
    }

    fun clearMenu() {
        menuItems.clear()
        menuClickListener = null
        updateMenuVisible()
    }

    fun setOnMenuClickListener(listener: ((JiangToolbarMenuItem) -> Unit)?) {
        menuClickListener = listener
    }

    private fun showMenu() {
        if (menuItems.isEmpty()) {
            return
        }

        PopupMenu(context, menuView).apply {
            menuItems.forEach { item ->
                menu.add(0, item.id, 0, item.title)
            }
            setOnMenuItemClickListener { menuItem ->
                menuItems.firstOrNull { it.id == menuItem.itemId }?.let { item ->
                    menuClickListener?.invoke(item)
                    true
                } ?: false
            }
            show()
        }
    }

    private fun updateMenuVisible() {
        actionContainer.visibility = if (menuItems.isEmpty()) View.INVISIBLE else View.VISIBLE
    }

    private fun Int.dp(): Int {
        return (this * resources.displayMetrics.density + 0.5f).toInt()
    }

    private companion object {
        const val DEFAULT_HEIGHT_DP = 56
    }
}
