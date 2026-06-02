package com.jj.core.permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

object JiangPermissionChecker {

    fun hasPermission(context: Context, permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
            context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    fun hasPermissions(context: Context, permissions: Collection<String>): Boolean {
        return permissions.all { permission ->
            hasPermission(context, permission)
        }
    }

    fun deniedPermissions(context: Context, permissions: Collection<String>): List<String> {
        return permissions.filterNot { permission ->
            hasPermission(context, permission)
        }
    }

    fun shouldShowRationale(activity: Activity, permissions: Collection<String>): List<String> {
        return permissions.filter { permission ->
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                activity.shouldShowRequestPermissionRationale(permission)
        }
    }

    fun fromGrantResult(
        activity: Activity,
        grantResult: Map<String, Boolean>,
    ): JiangPermissionResult {
        val granted = grantResult.filterValues { isGranted -> isGranted }.keys.toList()
        val denied = grantResult.filterValues { isGranted -> !isGranted }.keys.toList()
        val deniedForever = denied.filterNot { permission ->
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                activity.shouldShowRequestPermissionRationale(permission)
        }
        return JiangPermissionResult(
            granted = granted,
            denied = denied - deniedForever.toSet(),
            deniedForever = deniedForever,
        )
    }
}
