package com.jj.arch.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class JiangVmActivity<VB : ViewBinding, VM : ViewModel> : JiangActivity<VB>() {

    protected val viewModel: VM by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this)[findViewModelClass()]
    }

    @Suppress("UNCHECKED_CAST")
    private fun findViewModelClass(): Class<VM> {
        var currentType = javaClass.genericSuperclass
        while (currentType != null) {
            if (currentType is ParameterizedType) {
                val viewModelType = currentType.actualTypeArguments.getOrNull(1)
                val viewModelClass = when (viewModelType) {
                    is Class<*> -> viewModelType
                    is ParameterizedType -> viewModelType.rawType as? Class<*>
                    else -> null
                }
                if (viewModelClass != null && ViewModel::class.java.isAssignableFrom(viewModelClass)) {
                    return viewModelClass as Class<VM>
                }
            }
            currentType = (currentType as? Class<*>)?.genericSuperclass
        }
        error("Unable to resolve ViewModel generic type.")
    }
}
