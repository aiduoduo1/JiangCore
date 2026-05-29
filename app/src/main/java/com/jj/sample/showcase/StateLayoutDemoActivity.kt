package com.jj.sample.showcase

import android.os.Bundle
import android.view.LayoutInflater
import com.jj.sample.databinding.ActivityStateLayoutDemoBinding
import com.jj.ui.base.JiangToolbarStateVmActivity

class StateLayoutDemoActivity :
    JiangToolbarStateVmActivity<ActivityStateLayoutDemoBinding, ShowcaseViewModel>() {

    override fun createViewBinding(inflater: LayoutInflater): ActivityStateLayoutDemoBinding {
        return ActivityStateLayoutDemoBinding.inflate(inflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnLoading.setOnClickListener {
            viewModel.showLoadingDemo()
        }
        binding.btnEmpty.setOnClickListener {
            viewModel.showEmptyDemo()
        }
        binding.btnError.setOnClickListener {
            viewModel.showErrorDemo()
        }
        binding.btnContent.setOnClickListener {
            viewModel.showContentDemo()
        }
    }

    override fun initData() {
        viewModel.showContentDemo()
    }

    override fun onUiSuccess(data: Any) {
        super.onUiSuccess(data)
        binding.tvContent.text = data.toString()
    }

    override fun onStateRetryClick() {
        viewModel.showContentDemo()
    }

    override fun toolbarTitle(): CharSequence = "StateLayout 示例"
}
