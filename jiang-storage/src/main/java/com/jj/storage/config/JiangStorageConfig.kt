package com.jj.storage.config

data class JiangStorageConfig(
    val mmkvRootDir: String? = null,
    val defaultKvId: String = "jiang_default",
    val debug: Boolean = false,
)
