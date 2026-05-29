package com.jj.sample

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.jj.sample.databinding.ActivityMainBinding
import com.jj.sample.showcase.ShowcaseActivity
import com.jj.ui.base.JiangToolbarStateVmActivity

class MainActivity : JiangToolbarStateVmActivity<ActivityMainBinding, MainViewModel>() {

    override fun createViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnEnterShowcase.setOnClickListener {
            startActivity(Intent(this, ShowcaseActivity::class.java))
        }
    }

    override fun toolbarTitle(): CharSequence = "JiangCore Sample"

    override fun showBackButton(): Boolean = false

    override fun enableStateLayout(): Boolean = false
}
