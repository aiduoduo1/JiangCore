package com.jj.sample.showcase

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import com.jj.core.permission.JiangPermissionFlowResult
import com.jj.core.permission.JiangPermissionResult
import com.jj.core.permission.JiangPermissionScene
import com.jj.core.permission.hasJiangPermissions
import com.jj.core.permission.openJiangAppSettings
import com.jj.core.permission.toJiangPermissionResult
import com.jj.sample.databinding.ActivityPermissionFlowDemoBinding
import com.jj.ui.base.JiangToolbarStateVmActivity

class PermissionFlowDemoActivity :
    JiangToolbarStateVmActivity<ActivityPermissionFlowDemoBinding, ShowcaseViewModel>() {

    private var pendingAction: PermissionBusinessAction? = null

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) { grantResult ->
        val action = pendingAction ?: return@registerForActivityResult
        pendingAction = null
        handlePermissionResult(action, toJiangPermissionResult(grantResult))
    }

    override fun createViewBinding(inflater: LayoutInflater): ActivityPermissionFlowDemoBinding {
        return ActivityPermissionFlowDemoBinding.inflate(inflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnScan.setOnClickListener {
            runWithPermission(PermissionBusinessAction.ScanCode)
        }
        binding.btnBluetooth.setOnClickListener {
            runWithPermission(PermissionBusinessAction.ConnectBluetooth)
        }
        binding.btnLocation.setOnClickListener {
            runWithPermission(PermissionBusinessAction.QueryLocation)
        }
        binding.btnMedia.setOnClickListener {
            runWithPermission(PermissionBusinessAction.PickPhoto)
        }
    }

    override fun toolbarTitle(): CharSequence = "Permission Flow Demo"

    override fun enableStateLayout(): Boolean = false

    private fun runWithPermission(action: PermissionBusinessAction) {
        val permissions = action.scene.permissions()
        if (permissions.isEmpty() || hasJiangPermissions(permissions)) {
            runBusinessAction(action)
            return
        }
        showConfirmDialog(
            title = action.reasonTitle,
            message = action.reasonMessage,
            onConfirm = {
                pendingAction = action
                permissionLauncher.launch(permissions.toTypedArray())
            },
            onCancel = {
                blockBusinessAction(action, "User cancelled permission request.")
            },
        )
    }

    private fun handlePermissionResult(
        action: PermissionBusinessAction,
        result: JiangPermissionResult,
    ) {
        when (val flowResult = JiangPermissionFlowResult.fromPermissionResult(result)) {
            JiangPermissionFlowResult.Granted -> runBusinessAction(action)
            is JiangPermissionFlowResult.Denied -> blockBusinessAction(
                action = action,
                reason = action.deniedMessage,
                permissions = flowResult.permissions,
            )

            is JiangPermissionFlowResult.DeniedForever -> showConfirmDialog(
                title = action.settingsTitle,
                message = action.settingsMessage,
                onConfirm = {
                    openJiangAppSettings()
                    blockBusinessAction(
                        action = action,
                        reason = "Opened settings. Re-run the action after enabling permission.",
                        permissions = flowResult.permissions,
                    )
                },
                onCancel = {
                    blockBusinessAction(
                        action = action,
                        reason = action.deniedForeverMessage,
                        permissions = flowResult.permissions,
                    )
                },
            )
        }
    }

    private fun runBusinessAction(action: PermissionBusinessAction) {
        binding.tvResult.text = buildString {
            appendLine("${action.label}: permission ready.")
            appendLine(action.successMessage)
        }
    }

    private fun blockBusinessAction(
        action: PermissionBusinessAction,
        reason: String,
        permissions: List<String> = emptyList(),
    ) {
        binding.tvResult.text = buildString {
            appendLine("${action.label}: business blocked.")
            appendLine(reason)
            if (permissions.isNotEmpty()) {
                appendLine()
                appendLine("Missing permissions:")
                permissions.forEach { permission ->
                    appendLine("- $permission")
                }
            }
        }
    }

    private sealed class PermissionBusinessAction(
        val label: String,
        val scene: JiangPermissionScene,
        val reasonTitle: String,
        val reasonMessage: String,
        val deniedMessage: String,
        val deniedForeverMessage: String,
        val settingsTitle: String,
        val settingsMessage: String,
        val successMessage: String,
    ) {

        data object ScanCode : PermissionBusinessAction(
            label = "Scan code",
            scene = JiangPermissionScene.Camera,
            reasonTitle = "Camera permission required",
            reasonMessage = "Scanning needs camera permission. Without it, keep manual input selected.",
            deniedMessage = "Camera permission denied. Select all scan input and wait for manual entry.",
            deniedForeverMessage = "Camera permission permanently denied. Manual input remains available.",
            settingsTitle = "Enable camera permission",
            settingsMessage = "Open app settings and enable camera permission before scanning.",
            successMessage = "Start scanner and move focus into the scan box.",
        )

        data object ConnectBluetooth : PermissionBusinessAction(
            label = "Connect bluetooth",
            scene = JiangPermissionScene.Bluetooth,
            reasonTitle = "Bluetooth permission required",
            reasonMessage = "Device scan and connection need bluetooth permission.",
            deniedMessage = "Bluetooth permission denied. Device list stays empty and connect is disabled.",
            deniedForeverMessage = "Bluetooth permission permanently denied. Device connection stays blocked.",
            settingsTitle = "Enable bluetooth permission",
            settingsMessage = "Open app settings and enable bluetooth permission before connecting devices.",
            successMessage = "Start device scan and enable connect action.",
        )

        data object QueryLocation : PermissionBusinessAction(
            label = "Query location",
            scene = JiangPermissionScene.Location,
            reasonTitle = "Location permission required",
            reasonMessage = "Location query needs location permission. Manual address selection can remain available.",
            deniedMessage = "Location permission denied. Do not request location API; keep manual selection available.",
            deniedForeverMessage = "Location permission permanently denied. Use manual address selection.",
            settingsTitle = "Enable location permission",
            settingsMessage = "Open app settings and enable location permission before location query.",
            successMessage = "Request current location and continue location-based query.",
        )

        data object PickPhoto : PermissionBusinessAction(
            label = "Pick photo",
            scene = JiangPermissionScene.Media,
            reasonTitle = "Photo permission required",
            reasonMessage = "Picking photos needs media permission.",
            deniedMessage = "Photo permission denied. Do not open album; keep other upload paths available.",
            deniedForeverMessage = "Photo permission permanently denied. Album entry remains blocked.",
            settingsTitle = "Enable photo permission",
            settingsMessage = "Open app settings and enable photo permission before opening album.",
            successMessage = "Open album picker and continue upload flow.",
        )
    }
}
