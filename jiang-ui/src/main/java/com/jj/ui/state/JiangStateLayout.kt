package com.jj.ui.state

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView

class JiangStateLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val config = JiangStateViewConfig()
    private var contentView: View? = null
    private val loadingTextView: TextView
    private val emptyTextView: TextView
    private val errorTextView: TextView
    private val retryButton: Button
    private val loadingView: View
    private val emptyView: View
    private val errorView: View

    init {
        loadingTextView = createTextView(config.loadingText)
        loadingView = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            addView(ProgressBar(context))
            addView(
                loadingTextView,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                ).apply {
                    topMargin = 12.dp()
                },
            )
        }

        emptyTextView = createTextView(config.emptyText)
        emptyView = LinearLayout(context).apply {
            gravity = Gravity.CENTER
            addView(emptyTextView)
        }

        errorTextView = createTextView(config.errorText)
        retryButton = Button(context).apply {
            text = config.retryText
        }
        errorView = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            addView(errorTextView)
            addView(
                retryButton,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                ).apply {
                    topMargin = 12.dp()
                },
            )
        }

        addView(loadingView, defaultLayoutParams())
        addView(emptyView, defaultLayoutParams())
        addView(errorView, defaultLayoutParams())
        showContent()
    }

    fun setContentView(view: View) {
        contentView?.let(::removeView)
        contentView = view
        addView(view, 0, defaultLayoutParams())
        showContent()
    }

    fun showContent() {
        contentView?.visibility = View.VISIBLE
        loadingView.visibility = View.GONE
        emptyView.visibility = View.GONE
        errorView.visibility = View.GONE
    }

    fun showLoading() {
        contentView?.visibility = View.GONE
        loadingTextView.text = config.loadingText
        loadingView.visibility = View.VISIBLE
        emptyView.visibility = View.GONE
        errorView.visibility = View.GONE
    }

    fun showEmpty(text: CharSequence? = null) {
        contentView?.visibility = View.GONE
        emptyTextView.text = text ?: config.emptyText
        loadingView.visibility = View.GONE
        emptyView.visibility = View.VISIBLE
        errorView.visibility = View.GONE
    }

    fun showError(text: CharSequence? = null) {
        contentView?.visibility = View.GONE
        errorTextView.text = text ?: config.errorText
        loadingView.visibility = View.GONE
        emptyView.visibility = View.GONE
        errorView.visibility = View.VISIBLE
    }

    fun setOnRetryClickListener(listener: OnClickListener?) {
        retryButton.setOnClickListener(listener)
    }

    private fun createTextView(textValue: CharSequence): TextView {
        return TextView(context).apply {
            gravity = Gravity.CENTER
            setTextColor(Color.BLACK)
            textSize = 16f
            text = textValue
        }
    }

    private fun defaultLayoutParams(): LayoutParams {
        return LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    private fun Int.dp(): Int {
        return (this * resources.displayMetrics.density + 0.5f).toInt()
    }
}
