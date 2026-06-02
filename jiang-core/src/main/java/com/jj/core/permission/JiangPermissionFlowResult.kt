package com.jj.core.permission

sealed class JiangPermissionFlowResult {

    data object Granted : JiangPermissionFlowResult()

    data class Denied(
        val permissions: List<String>,
    ) : JiangPermissionFlowResult()

    data class DeniedForever(
        val permissions: List<String>,
    ) : JiangPermissionFlowResult()

    companion object {

        fun fromPermissionResult(result: JiangPermissionResult): JiangPermissionFlowResult {
            return when {
                result.deniedForever.isNotEmpty() -> DeniedForever(result.deniedForever)
                result.denied.isNotEmpty() -> Denied(result.denied)
                else -> Granted
            }
        }
    }
}
