package com.jj.sample.scan

sealed class ScanUiEvent {

    data object FocusFirstInput : ScanUiEvent()

    data object FocusSecondInput : ScanUiEvent()

    data object SelectAllFirstInput : ScanUiEvent()

    data object SelectAllSecondInput : ScanUiEvent()
}
