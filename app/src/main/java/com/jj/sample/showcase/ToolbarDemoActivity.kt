package com.jj.sample.showcase

import android.os.Bundle
import android.view.LayoutInflater
import com.jj.sample.databinding.ActivityToolbarDemoBinding
import com.jj.ui.base.JiangToolbarStateVmActivity

class ToolbarDemoActivity : JiangToolbarStateVmActivity<ActivityToolbarDemoBinding, ShowcaseViewModel>() {

    override fun createViewBinding(inflater: LayoutInflater): ActivityToolbarDemoBinding {
        return ActivityToolbarDemoBinding.inflate(inflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.addMenuItem(1, "设置")
        toolbar.addMenuItem(2, "保存")
        toolbar.addMenuItem(3, "更多")
        toolbar.setOnMenuClickListener {
            binding.tvResult.text = "点击菜单: ${it.title}"
        }
    }

    override fun toolbarTitle(): CharSequence = "Toolbar 示例"

    override fun enableStateLayout(): Boolean = false
}
