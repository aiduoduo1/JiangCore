package com.jj.arch.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.ComponentActivity
import androidx.viewbinding.ViewBinding

abstract class JiangActivity<VB : ViewBinding> : ComponentActivity() {

    private var _binding: VB? = null

    protected val binding: VB
        get() = _binding ?: error("ViewBinding is only valid between onCreate and onDestroy.")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = createViewBinding(layoutInflater)
        setContentView(createRootView(binding))
        initView(savedInstanceState)
        initObserver()
        initData()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    protected abstract fun createViewBinding(inflater: LayoutInflater): VB

    protected open fun createRootView(binding: VB): View {
        return binding.root
    }

    protected open fun initView(savedInstanceState: Bundle?) = Unit

    protected open fun initObserver() = Unit

    protected open fun initData() = Unit
}
