package com.jj.core.permission

import android.Manifest
import android.os.Build
import org.junit.Assert.assertEquals
import org.junit.Test

class JiangPermissionTest {

    @Test
    fun cameraPermissionsContainCamera() {
        assertEquals(
            listOf(Manifest.permission.CAMERA),
            JiangPermission.cameraPermissions(),
        )
    }

    @Test
    fun locationPermissionsContainFineAndCoarseLocation() {
        assertEquals(
            listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ),
            JiangPermission.locationPermissions(),
        )
    }

    @Test
    fun bluetoothPermissionsUseAndroid12PermissionsFromS() {
        assertEquals(
            listOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT,
            ),
            JiangPermission.bluetoothPermissions(Build.VERSION_CODES.S),
        )
    }

    @Test
    fun bluetoothPermissionsUseLegacyPermissionsBeforeS() {
        assertEquals(
            listOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            JiangPermission.bluetoothPermissions(Build.VERSION_CODES.R),
        )
    }

    @Test
    fun mediaPermissionsUseReadMediaFromTiramisu() {
        assertEquals(
            listOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO,
            ),
            JiangPermission.mediaPermissions(Build.VERSION_CODES.TIRAMISU),
        )
    }

    @Test
    fun mediaPermissionsUseReadExternalStorageBeforeTiramisu() {
        assertEquals(
            listOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            JiangPermission.mediaPermissions(Build.VERSION_CODES.S),
        )
    }

    @Test
    fun storagePermissionsUseReadOnlyFromAndroid10To12() {
        assertEquals(
            listOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            JiangPermission.storagePermissions(Build.VERSION_CODES.Q),
        )
    }

    @Test
    fun storagePermissionsUseReadAndWriteBeforeAndroid10() {
        assertEquals(
            listOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ),
            JiangPermission.storagePermissions(Build.VERSION_CODES.P),
        )
    }

    @Test
    fun storagePermissionsAreEmptyFromTiramisu() {
        assertEquals(
            emptyList<String>(),
            JiangPermission.storagePermissions(Build.VERSION_CODES.TIRAMISU),
        )
    }

    @Test
    fun manifestOnlyPermissionsContainNetworkAndVibrate() {
        assertEquals(
            listOf(
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
            ),
            JiangPermission.networkPermissions(),
        )
        assertEquals(
            listOf(Manifest.permission.VIBRATE),
            JiangPermission.vibratePermissions(),
        )
    }
}
