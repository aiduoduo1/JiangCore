package com.jj.sample.showcase

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.jj.sample.databinding.ActivityShowcaseBinding
import com.jj.ui.base.JiangToolbarStateVmActivity

class ShowcaseActivity : JiangToolbarStateVmActivity<ActivityShowcaseBinding, ShowcaseViewModel>() {

    override fun createViewBinding(inflater: LayoutInflater): ActivityShowcaseBinding {
        return ActivityShowcaseBinding.inflate(inflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnToolbarDemo.setOnClickListener {
            startActivity(Intent(this, ToolbarDemoActivity::class.java))
        }
        binding.btnStateLayoutDemo.setOnClickListener {
            startActivity(Intent(this, StateLayoutDemoActivity::class.java))
        }
        binding.btnNetworkDemo.setOnClickListener {
            startActivity(Intent(this, NetworkDemoActivity::class.java))
        }
        binding.btnScanCodeDemo.setOnClickListener {
            startActivity(Intent(this, ScanCodeDemoActivity::class.java))
        }
        binding.btnApkUpdateDemo.setOnClickListener {
            startActivity(Intent(this, ApkUpdateDemoActivity::class.java))
        }
        binding.btnStorageDemo.setOnClickListener {
            startActivity(Intent(this, StorageDemoActivity::class.java))
        }
        binding.btnUiDemo.setOnClickListener {
            startActivity(Intent(this, UiDemoActivity::class.java))
        }
        binding.btnPermissionDemo.setOnClickListener {
            startActivity(Intent(this, PermissionDemoActivity::class.java))
        }
        binding.btnPermissionFlowDemo.setOnClickListener {
            startActivity(Intent(this, PermissionFlowDemoActivity::class.java))
        }
    }

    override fun toolbarTitle(): CharSequence = "JiangCore Showcase"

    override fun enableStateLayout(): Boolean = false
}
