package com.jj.sample.showcase

import android.os.Bundle
import android.view.LayoutInflater
import com.jj.sample.databinding.ActivityStorageDemoBinding
import com.jj.ui.base.JiangToolbarStateVmActivity

class StorageDemoActivity : JiangToolbarStateVmActivity<ActivityStorageDemoBinding, ShowcaseViewModel>() {

    override fun createViewBinding(inflater: LayoutInflater): ActivityStorageDemoBinding {
        return ActivityStorageDemoBinding.inflate(inflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnPutString.setOnClickListener {
            viewModel.putString()
        }
        binding.btnGetString.setOnClickListener {
            viewModel.getString()
        }
        binding.btnRemoveKey.setOnClickListener {
            viewModel.removeKey()
        }
        binding.btnClearStorage.setOnClickListener {
            viewModel.clearStorage()
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

    override fun toolbarTitle(): CharSequence = "Storage 示例"

    override fun enableStateLayout(): Boolean = false
}
