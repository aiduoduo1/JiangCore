package com.jj.arch.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class JiangFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null

    protected val binding: VB
        get() = _binding ?: error("ViewBinding is only valid between onCreateView and onDestroyView.")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = createViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
        initObserver()
        initData()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    protected abstract fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    protected open fun initView(savedInstanceState: Bundle?) = Unit

    protected open fun initObserver() = Unit

    protected open fun initData() = Unit
}
