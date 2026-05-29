package com.jj.core

import android.app.Application
import com.jj.core.config.JiangConfig

object JiangCore {

    private var _application: Application? = null
    private var _config: JiangConfig? = null

    val application: Application
        get() = _application ?: error("JiangCore is not initialized.")

    val config: JiangConfig
        get() = _config ?: error("JiangCore is not initialized.")

    val isInitialized: Boolean
        get() = _application != null && _config != null

    fun init(application: Application, config: JiangConfig) {
        _application = application
        _config = config
    }
}
