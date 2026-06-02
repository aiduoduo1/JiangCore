package com.jj.sample.showcase

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import com.jj.core.permission.JiangPermission
import com.jj.core.permission.JiangPermissionResult
import com.jj.core.permission.hasJiangPermissions
import com.jj.core.permission.openJiangAppSettings
import com.jj.core.permission.toJiangPermissionResult
import com.jj.core.system.JiangVibrator
import com.jj.sample.databinding.ActivityPermissionDemoBinding
import com.jj.ui.base.JiangToolbarStateVmActivity

class PermissionDemoActivity :
    JiangToolbarStateVmActivity<ActivityPermissionDemoBinding, ShowcaseViewModel>() {

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) { grantResult ->
        renderPermissionResult(toJiangPermissionResult(grantResult))
    }

    override fun createViewBinding(inflater: LayoutInflater): ActivityPermissionDemoBinding {
        return ActivityPermissionDemoBinding.inflate(inflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnCamera.setOnClickListener {
            requestPermissions("Camera", JiangPermission.cameraPermissions())
        }
        binding.btnLocation.setOnClickListener {
            requestPermissions("Location", JiangPermission.locationPermissions())
        }
        binding.btnBluetooth.setOnClickListener {
            requestPermissions("Bluetooth", JiangPermission.bluetoothPermissions())
        }
        binding.btnMedia.setOnClickListener {
            requestPermissions("Photos / Videos", JiangPermission.mediaPermissions())
        }
        binding.btnStorage.setOnClickListener {
            requestPermissions("File Storage", JiangPermission.storagePermissions())
        }
        binding.btnSettings.setOnClickListener {
            val opened = openJiangAppSettings()
            binding.tvResult.text = if (opened) {
                "Opened app settings."
            } else {
                "Open app settings failed."
            }
        }
        binding.btnVibrate.setOnClickListener {
            val vibrated = JiangVibrator.vibrate(this)
            binding.tvResult.text = if (vibrated) {
                "Vibration requested."
            } else {
                "Device does not support vibration or request failed."
            }
        }
    }

    override fun toolbarTitle(): CharSequence = "Permission Demo"

    override fun enableStateLayout(): Boolean = false

    private fun requestPermissions(label: String, permissions: List<String>) {
        if (permissions.isEmpty()) {
            binding.tvResult.text = "$label does not need runtime permission on this Android version."
            return
        }
        if (hasJiangPermissions(permissions)) {
            binding.tvResult.text = "$label already granted:\n${permissions.joinToString("\n")}"
            return
        }
        binding.tvResult.text = "Requesting $label..."
        permissionLauncher.launch(permissions.toTypedArray())
    }

    private fun renderPermissionResult(result: JiangPermissionResult) {
        binding.tvResult.text = buildString {
            appendLine("All granted: ${result.isAllGranted}")
            appendSection("Granted", result.granted)
            appendSection("Denied", result.denied)
            appendSection("Denied forever", result.deniedForever)
        }
    }

    private fun StringBuilder.appendSection(title: String, permissions: List<String>) {
        appendLine()
        appendLine(title)
        if (permissions.isEmpty()) {
            appendLine("- none")
        } else {
            permissions.forEach { permission ->
                appendLine("- $permission")
            }
        }
    }
}
