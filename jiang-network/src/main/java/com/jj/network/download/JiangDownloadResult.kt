package com.jj.network.download

import java.io.File

data class JiangDownloadResult(
    val file: File,
    val bytesRead: Long,
    val totalBytes: Long,
)
