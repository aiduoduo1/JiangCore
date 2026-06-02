package com.jj.core.permission

import android.Manifest
import android.os.Build
import org.junit.Assert.assertEquals
import org.junit.Test

class JiangPermissionSceneTest {

    @Test
    fun sceneReturnsPermissionsForBusinessActions() {
        assertEquals(
            listOf(Manifest.permission.CAMERA),
            JiangPermissionScene.Camera.permissions(),
        )
        assertEquals(
            listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ),
            JiangPermissionScene.Location.permissions(),
        )
        assertEquals(
            listOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT,
            ),
            JiangPermissionScene.Bluetooth.permissions(Build.VERSION_CODES.S),
        )
        assertEquals(
            listOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO,
            ),
            JiangPermissionScene.Media.permissions(Build.VERSION_CODES.TIRAMISU),
        )
        assertEquals(
            listOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            JiangPermissionScene.Storage.permissions(Build.VERSION_CODES.Q),
        )
    }

    @Test
    fun flowResultUsesGrantedWhenAllPermissionsGranted() {
        val result = JiangPermissionFlowResult.fromPermissionResult(
            JiangPermissionResult(
                granted = listOf(Manifest.permission.CAMERA),
                denied = emptyList(),
            ),
        )

        assertEquals(JiangPermissionFlowResult.Granted, result)
    }

    @Test
    fun flowResultUsesDeniedForeverWhenAnyPermissionIsDeniedForever() {
        val result = JiangPermissionFlowResult.fromPermissionResult(
            JiangPermissionResult(
                granted = emptyList(),
                denied = emptyList(),
                deniedForever = listOf(Manifest.permission.CAMERA),
            ),
        )

        assertEquals(
            JiangPermissionFlowResult.DeniedForever(listOf(Manifest.permission.CAMERA)),
            result,
        )
    }

    @Test
    fun flowResultUsesDeniedWhenPermissionsAreTemporarilyDenied() {
        val result = JiangPermissionFlowResult.fromPermissionResult(
            JiangPermissionResult(
                granted = emptyList(),
                denied = listOf(Manifest.permission.CAMERA),
            ),
        )

        assertEquals(
            JiangPermissionFlowResult.Denied(listOf(Manifest.permission.CAMERA)),
            result,
        )
    }
}
