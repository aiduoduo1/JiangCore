package com.jj.storage

import android.app.Application
import com.jj.storage.config.JiangStorageConfig
import com.jj.storage.kv.JiangKeyValueStore
import com.jj.storage.kv.mmkv.MmkvKeyValueStore
import com.tencent.mmkv.MMKV

object JiangStorage {

    private var keyValueStore: JiangKeyValueStore? = null

    val kv: JiangKeyValueStore
        get() = keyValueStore ?: error(
            "JiangStorage has not been initialized. Please call JiangStorage.init(application).",
        )

    fun init(
        application: Application,
        config: JiangStorageConfig = JiangStorageConfig(),
    ) {
        if (config.mmkvRootDir == null) {
            MMKV.initialize(application)
        } else {
            MMKV.initialize(application, config.mmkvRootDir)
        }
        keyValueStore = MmkvKeyValueStore(MMKV.mmkvWithID(config.defaultKvId))
    }
}
