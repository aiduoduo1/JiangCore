package com.jj.sample.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jj.arch.event.JiangEvent
import com.jj.arch.state.JiangLoadingMode
import com.jj.arch.vm.JiangViewModel

class ScanCodeViewModel : JiangViewModel() {

    private val repository = ScanRepository()

    private val _message = MutableLiveData("请先扫描第一码")
    val message: LiveData<String> = _message

    private val _scanEvent = MutableLiveData<JiangEvent<ScanUiEvent>>()
    val scanEvent: LiveData<JiangEvent<ScanUiEvent>> = _scanEvent

    private var firstCode: String = ""

    fun submitFirstCode(code: String) {
        launchResult(
            loadingMode = JiangLoadingMode.DIALOG,
            loadingMessage = "校验第一码...",
            block = {
                repository.checkFirstCode(code)
            },
            onSuccess = { message ->
                firstCode = code.trim()
                _message.value = message
                sendScanEvent(ScanUiEvent.FocusSecondInput)
                showToast("第一码通过")
            },
        )
    }

    fun submitSecondCode(code: String) {
        launchResult(
            loadingMode = JiangLoadingMode.DIALOG,
            loadingMessage = "校验第二码...",
            block = {
                repository.checkSecondCode(firstCode, code)
            },
            onSuccess = { message ->
                _message.value = message
                showToast("扫码流程完成")
            },
        )
    }

    fun onUiError(message: String) {
        _message.value = message
        if (firstCode.isBlank()) {
            sendScanEvent(ScanUiEvent.SelectAllFirstInput)
        } else {
            sendScanEvent(ScanUiEvent.SelectAllSecondInput)
        }
    }

    fun reset() {
        firstCode = ""
        _message.value = "已重置，请重新扫描第一码"
        sendScanEvent(ScanUiEvent.FocusFirstInput)
    }

    private fun sendScanEvent(event: ScanUiEvent) {
        _scanEvent.value = JiangEvent(event)
    }
}
