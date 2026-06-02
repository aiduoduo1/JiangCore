package com.jj.core.apk

sealed class JiangApkInstallResult {
    data object Started : JiangApkInstallResult()
    data object FileNotFound : JiangApkInstallResult()
    data object NotApkFile : JiangApkInstallResult()
    data object PermissionRequired : JiangApkInstallResult()
    data class Error(val throwable: Throwable) : JiangApkInstallResult()
}
