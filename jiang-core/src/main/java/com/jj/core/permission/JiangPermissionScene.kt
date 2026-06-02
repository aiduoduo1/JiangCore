package com.jj.core.permission

import android.os.Build

sealed class JiangPermissionScene {

    data object Camera : JiangPermissionScene()
    data object Location : JiangPermissionScene()
    data object Bluetooth : JiangPermissionScene()
    data object Media : JiangPermissionScene()
    data object Storage : JiangPermissionScene()

    fun permissions(sdkInt: Int = Build.VERSION.SDK_INT): List<String> {
        return when (this) {
            Camera -> JiangPermission.cameraPermissions()
            Location -> JiangPermission.locationPermissions()
            Bluetooth -> JiangPermission.bluetoothPermissions(sdkInt)
            Media -> JiangPermission.mediaPermissions(sdkInt)
            Storage -> JiangPermission.storagePermissions(sdkInt)
        }
    }
}
