package com.jj.sample.showcase

import android.os.Bundle
import android.view.LayoutInflater
import com.jj.sample.databinding.ActivityNetworkDemoBinding
import com.jj.ui.base.JiangToolbarStateVmActivity
import com.jj.ui.state.JiangErrorMode

class NetworkDemoActivity :
    JiangToolbarStateVmActivity<ActivityNetworkDemoBinding, ShowcaseViewModel>() {

    override fun createViewBinding(inflater: LayoutInflater): ActivityNetworkDemoBinding {
        return ActivityNetworkDemoBinding.inflate(inflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnRunNetwork.setOnClickListener {
            viewModel.runNetworkDemo()
        }
        binding.btnRunLogin.setOnClickListener {
            viewModel.runLoginDemo()
        }
        observeText(viewModel.message, binding.tvResult)
    }

    override fun errorMode(): JiangErrorMode = JiangErrorMode.TOAST

    override fun toolbarTitle(): CharSequence = "Network 示例"
}
