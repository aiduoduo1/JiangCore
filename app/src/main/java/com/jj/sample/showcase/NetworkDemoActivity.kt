package com.jj.sample.showcase

import android.os.Bundle
import android.view.LayoutInflater
import com.jj.sample.databinding.ActivityNetworkDemoBinding
import com.jj.ui.base.JiangToolbarStateVmActivity

class NetworkDemoActivity : JiangToolbarStateVmActivity<ActivityNetworkDemoBinding, ShowcaseViewModel>() {

    override fun createViewBinding(inflater: LayoutInflater): ActivityNetworkDemoBinding {
        return ActivityNetworkDemoBinding.inflate(inflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnRunNetwork.setOnClickListener {
            viewModel.runNetworkDemo()
        }
    }

    override fun initPageObserver() {
        viewModel.message.observe(this) {
            binding.tvResult.text = it
        }
    }

    override fun onUiSuccess(data: Any) {
        super.onUiSuccess(data)
        binding.tvResult.text = data.toString()
    }

    override fun onUiError(code: Int, message: String, throwable: Throwable?) {
        super.onUiError(code, message, throwable)
        binding.tvResult.text = message
    }

    override fun onStateRetryClick() {
        viewModel.runNetworkDemo()
    }

    override fun toolbarTitle(): CharSequence = "Network 示例"
}
