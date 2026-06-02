package com.jj.core.permission

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun Context.hasJiangPermission(permission: String): Boolean {
    return JiangPermissionChecker.hasPermission(this, permission)
}

fun Context.hasJiangPermissions(permissions: Collection<String>): Boolean {
    return JiangPermissionChecker.hasPermissions(this, permissions)
}

fun Context.getDeniedJiangPermissions(permissions: Collection<String>): List<String> {
    return JiangPermissionChecker.deniedPermissions(this, permissions)
}

fun Activity.getJiangPermissionRationales(permissions: Collection<String>): List<String> {
    return JiangPermissionChecker.shouldShowRationale(this, permissions)
}

fun Activity.toJiangPermissionResult(grantResult: Map<String, Boolean>): JiangPermissionResult {
    return JiangPermissionChecker.fromGrantResult(this, grantResult)
}

fun Context.createJiangAppSettingsIntent(): Intent {
    return Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.parse("package:$packageName"),
    ).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
}

fun Context.openJiangAppSettings(): Boolean {
    return runCatching {
        startActivity(createJiangAppSettingsIntent())
        true
    }.getOrDefault(false)
}
