package com.jj.network.download

import java.io.File

data class JiangDownloadConfig(
    val url: String,
    val targetFile: File,
    val mesUpToken: String? = null,
)
