package com.jj.ui.base

import android.view.View
import android.widget.LinearLayout
import androidx.viewbinding.ViewBinding
import com.jj.arch.base.JiangActivity
import com.jj.ui.toolbar.JiangToolbar

abstract class JiangToolbarActivity<VB : ViewBinding> : JiangActivity<VB>() {

    protected lateinit var toolbar: JiangToolbar
        private set

    override fun createRootView(binding: VB): View {
        if (!showToolbar()) {
            return binding.root
        }

        toolbar = JiangToolbar(this).apply {
            setTitleText(toolbarTitle())
            setBackVisible(showBackButton())
            setOnBackClickListener {
                onToolbarBackClick()
            }
        }

        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            addView(
                toolbar,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    DEFAULT_TOOLBAR_HEIGHT_DP.dp(),
                ),
            )
            addView(
                binding.root,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0,
                    1f,
                ),
            )
        }
    }

    protected open fun toolbarTitle(): CharSequence = ""

    protected open fun showToolbar(): Boolean = true

    protected open fun showBackButton(): Boolean = true

    protected open fun onToolbarBackClick() {
        finish()
    }

    private fun Int.dp(): Int {
        return (this * resources.displayMetrics.density + 0.5f).toInt()
    }

    private companion object {
        const val DEFAULT_TOOLBAR_HEIGHT_DP = 56
    }
}
