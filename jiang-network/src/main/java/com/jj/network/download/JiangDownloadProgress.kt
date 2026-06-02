package com.jj.network.download

data class JiangDownloadProgress(
    val bytesRead: Long,
    val totalBytes: Long,
) {
    val percent: Int
        get() = if (totalBytes > 0L) {
            ((bytesRead * 100L) / totalBytes).coerceIn(0L, 100L).toInt()
        } else {
            0
        }
}
