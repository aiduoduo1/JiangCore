package com.jj.core.permission

data class JiangPermissionResult(
    val granted: List<String>,
    val denied: List<String>,
    val deniedForever: List<String> = emptyList(),
) {

    val isAllGranted: Boolean
        get() = denied.isEmpty() && deniedForever.isEmpty()
}
