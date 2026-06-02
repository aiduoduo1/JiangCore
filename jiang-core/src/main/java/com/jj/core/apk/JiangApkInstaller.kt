package com.jj.core.apk

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import java.io.File

object JiangApkInstaller {

    private const val APK_SUFFIX = "apk"
    private const val AUTHORITY_SUFFIX = ".jiang.fileprovider"

    fun isFileExists(file: File): Boolean {
        return file.exists() && file.isFile
    }

    fun isApkFile(file: File): Boolean {
        return file.extension.equals(APK_SUFFIX, ignoreCase = true)
    }

    fun canRequestPackageInstalls(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.packageManager.canRequestPackageInstalls()
        } else {
            true
        }
    }

    fun openUnknownAppSourcesSettings(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return false
        }
        val intent = Intent(
            Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
            Uri.parse("package:${context.packageName}"),
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        return runCatching {
            context.startActivity(intent)
            true
        }.getOrDefault(false)
    }

    fun install(context: Context, apkFile: File): JiangApkInstallResult {
        if (!isFileExists(apkFile)) {
            return JiangApkInstallResult.FileNotFound
        }
        if (!isApkFile(apkFile)) {
            return JiangApkInstallResult.NotApkFile
        }
        if (!canRequestPackageInstalls(context)) {
            return JiangApkInstallResult.PermissionRequired
        }
        return runCatching {
            val apkUri = getApkUri(context, apkFile)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(apkUri, "application/vnd.android.package-archive")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(intent)
            JiangApkInstallResult.Started
        }.getOrElse { throwable ->
            JiangApkInstallResult.Error(throwable)
        }
    }

    fun getApkUri(context: Context, apkFile: File): Uri {
        val fileProviderClass = Class.forName("androidx.core.content.FileProvider")
        val getUriForFile = fileProviderClass.getMethod(
            "getUriForFile",
            Context::class.java,
            String::class.java,
            File::class.java,
        )
        return getUriForFile.invoke(
            null,
            context,
            "${context.packageName}$AUTHORITY_SUFFIX",
            apkFile,
        ) as Uri
    }
}
