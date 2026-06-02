package com.jj.network.download

import com.jj.common.exception.JiangErrorCode
import com.jj.network.exception.JiangApiException
import java.io.File

class JiangApkDownloader(
    private val fileDownloader: JiangFileDownloader,
) {

    suspend fun downloadApk(
        url: String,
        targetFile: File,
        mesUpToken: String? = null,
        onProgress: ((JiangDownloadProgress) -> Unit)? = null,
    ): JiangDownloadResult {
        if (!targetFile.extension.equals(APK_SUFFIX, ignoreCase = true)) {
            throw JiangApiException(
                code = JiangErrorCode.PARAMS_INVALID,
                message = "APK download target file must use .apk suffix.",
            )
        }
        return fileDownloader.download(
            config = JiangDownloadConfig(
                url = url,
                targetFile = targetFile,
                mesUpToken = mesUpToken,
            ),
            onProgress = onProgress,
        )
    }

    private companion object {
        const val APK_SUFFIX = "apk"
    }
}
