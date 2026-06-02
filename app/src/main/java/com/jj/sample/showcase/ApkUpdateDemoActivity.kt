package com.jj.sample.showcase

import android.os.Bundle
import android.view.LayoutInflater
import com.jj.core.apk.JiangApkInstallResult
import com.jj.core.apk.JiangApkInstaller
import com.jj.network.JiangNetwork
import com.jj.sample.databinding.ActivityApkUpdateDemoBinding
import com.jj.ui.base.JiangToolbarStateVmActivity
import java.io.File

class ApkUpdateDemoActivity :
    JiangToolbarStateVmActivity<ActivityApkUpdateDemoBinding, ShowcaseViewModel>() {

    override fun createViewBinding(inflater: LayoutInflater): ActivityApkUpdateDemoBinding {
        return ActivityApkUpdateDemoBinding.inflate(inflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnShowDownloadUsage.setOnClickListener {
            binding.tvResult.text = "示例 URL 已留在 ApkUpdateDemoActivity.downloadAndInstallApkDemo()。"
        }
        binding.btnOpenInstallSettings.setOnClickListener {
            if (JiangApkInstaller.canRequestPackageInstalls(this)) {
                binding.tvResult.text = "当前 App 已有未知应用安装权限。"
            } else {
                JiangApkInstaller.openUnknownAppSourcesSettings(this)
                binding.tvResult.text = "已跳转到当前 App 的未知应用安装权限页。"
            }
        }
    }

    @Suppress("unused")
    private suspend fun downloadAndInstallApkDemo() {
        val apkFile = File(getExternalFilesDir("apk"), "jiang-sample-update.apk")
        val result = JiangNetwork.createApkDownloader().downloadApk(
            url = DEMO_APK_URL,
            targetFile = apkFile,
        ) { progress ->
            runOnUiThread {
                binding.tvResult.text = "下载进度：${progress.percent}%"
            }
        }
        when (val installResult = JiangApkInstaller.install(this, result.file)) {
            JiangApkInstallResult.Started -> {
                binding.tvResult.text = "已打开系统安装界面。"
            }

            JiangApkInstallResult.PermissionRequired -> {
                JiangApkInstaller.openUnknownAppSourcesSettings(this)
                binding.tvResult.text = "需要先允许当前 App 安装未知应用。"
            }

            JiangApkInstallResult.FileNotFound -> {
                binding.tvResult.text = "APK 文件不存在。"
            }

            JiangApkInstallResult.NotApkFile -> {
                binding.tvResult.text = "目标文件不是 APK。"
            }

            is JiangApkInstallResult.Error -> {
                binding.tvResult.text = installResult.throwable.message ?: "安装引导失败。"
            }
        }
    }

    override fun toolbarTitle(): CharSequence = "APK Update 绀轰緥"

    override fun enableStateLayout(): Boolean = false

    private companion object {
        private const val DEMO_APK_URL = "https://example.com/app-release.apk"
    }
}
