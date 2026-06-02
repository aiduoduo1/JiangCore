package com.jj.core.apk

import android.content.Context
import java.io.File

fun File.isJiangApkFile(): Boolean {
    return JiangApkInstaller.isApkFile(this)
}

fun File.isJiangApkExists(): Boolean {
    return JiangApkInstaller.isFileExists(this)
}

fun Context.hasJiangApkInstallPermission(): Boolean {
    return JiangApkInstaller.canRequestPackageInstalls(this)
}

fun Context.openJiangUnknownAppSourcesSettings(): Boolean {
    return JiangApkInstaller.openUnknownAppSourcesSettings(this)
}

fun Context.installJiangApk(apkFile: File): JiangApkInstallResult {
    return JiangApkInstaller.install(this, apkFile)
}
