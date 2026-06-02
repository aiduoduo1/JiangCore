package com.jj.sample.showcase

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.jj.sample.databinding.ActivityScanCodeDemoBinding
import com.jj.sample.scan.ScanCodeViewModel
import com.jj.sample.scan.ScanUiEvent
import com.jj.ui.base.JiangToolbarStateVmActivity
import com.jj.ui.state.JiangErrorMode

class ScanCodeDemoActivity :
    JiangToolbarStateVmActivity<ActivityScanCodeDemoBinding, ScanCodeViewModel>() {

    override fun createViewBinding(inflater: LayoutInflater): ActivityScanCodeDemoBinding {
        return ActivityScanCodeDemoBinding.inflate(inflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.etFirstCode.requestFocus()
        binding.etFirstCode.setScanSubmitListener {
            viewModel.submitFirstCode(binding.etFirstCode.text.toString())
        }
        binding.etSecondCode.setScanSubmitListener {
            viewModel.submitSecondCode(binding.etSecondCode.text.toString())
        }
        binding.btnSubmitFirst.setOnClickListener {
            viewModel.submitFirstCode(binding.etFirstCode.text.toString())
        }
        binding.btnSubmitSecond.setOnClickListener {
            viewModel.submitSecondCode(binding.etSecondCode.text.toString())
        }
        binding.btnReset.setOnClickListener {
            binding.etFirstCode.text?.clear()
            binding.etSecondCode.text?.clear()
            viewModel.reset()
        }
    }

    override fun initPageObserver() {
        observeText(viewModel.message, binding.tvResult)
        viewModel.scanEvent.observe(this) { event ->
            event.getContentIfNotHandled()?.let(::dispatchScanEvent)
        }
    }

    override fun onUiError(code: Int, message: String, throwable: Throwable?) {
        super.onUiError(code, message, throwable)
        viewModel.onUiError(message)
    }

    override fun errorMode(): JiangErrorMode = JiangErrorMode.TOAST

    override fun toolbarTitle(): CharSequence = "扫码业务示例"

    override fun enableStateLayout(): Boolean = false

    private fun dispatchScanEvent(event: ScanUiEvent) {
        when (event) {
            ScanUiEvent.FocusFirstInput -> binding.etFirstCode.focusAndSelectAll()
            ScanUiEvent.FocusSecondInput -> binding.etSecondCode.focusAndSelectAll()
            ScanUiEvent.SelectAllFirstInput -> binding.etFirstCode.focusAndSelectAll()
            ScanUiEvent.SelectAllSecondInput -> binding.etSecondCode.focusAndSelectAll()
        }
    }

    private fun EditText.setScanSubmitListener(onSubmit: () -> Unit) {
        setOnEditorActionListener { _, actionId, event ->
            val isDone = actionId == EditorInfo.IME_ACTION_DONE
            val isEnter = event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP
            if (isDone || isEnter) {
                onSubmit()
                true
            } else {
                false
            }
        }
    }

    private fun EditText.focusAndSelectAll() {
        requestFocus()
        selectAll()
    }
}
