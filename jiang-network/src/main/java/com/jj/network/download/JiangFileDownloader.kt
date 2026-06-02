package com.jj.network.download

import com.jj.common.exception.JiangErrorCode
import com.jj.network.exception.JiangApiException
import com.jj.network.exception.NetworkExceptionMapper
import com.jj.network.header.JiangNetworkHeaders
import java.io.File
import kotlin.coroutines.cancellation.CancellationException

class JiangFileDownloader(
    private val api: JiangDownloadApi,
    private val mesUpTokenProvider: () -> String? = { null },
) {

    suspend fun download(
        config: JiangDownloadConfig,
        onProgress: ((JiangDownloadProgress) -> Unit)? = null,
    ): JiangDownloadResult {
        val targetFile = config.targetFile
        val tempFile = File(targetFile.parentFile, "${targetFile.name}.tmp")
        return try {
            targetFile.parentFile?.mkdirs()
            if (tempFile.exists()) {
                tempFile.delete()
            }
            val response = api.download(config.url, buildHeaders(config))
            if (!response.isSuccessful) {
                throw JiangApiException(
                    code = response.code(),
                    message = response.message().ifBlank { "Download request failed." },
                )
            }
            val responseBody = response.body()
                ?: throw JiangApiException(
                    code = JiangErrorCode.UNKNOWN,
                    message = "Download response body is empty.",
                )
            val totalBytes = responseBody.contentLength()
            var bytesRead = 0L
            responseBody.byteStream().use { input ->
                tempFile.outputStream().use { output ->
                    val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                    while (true) {
                        val read = input.read(buffer)
                        if (read == -1) {
                            break
                        }
                        output.write(buffer, 0, read)
                        bytesRead += read
                        onProgress?.invoke(JiangDownloadProgress(bytesRead, totalBytes))
                    }
                }
            }
            if (targetFile.exists()) {
                targetFile.delete()
            }
            if (!tempFile.renameTo(targetFile)) {
                throw JiangApiException(
                    code = JiangErrorCode.UNKNOWN,
                    message = "Rename temp download file failed.",
                )
            }
            JiangDownloadResult(
                file = targetFile,
                bytesRead = bytesRead,
                totalBytes = totalBytes,
            )
        } catch (throwable: CancellationException) {
            if (tempFile.exists()) {
                tempFile.delete()
            }
            throw throwable
        } catch (throwable: Throwable) {
            if (tempFile.exists()) {
                tempFile.delete()
            }
            throw throwable.toJiangApiException()
        }
    }

    private fun buildHeaders(config: JiangDownloadConfig): Map<String, String> {
        val token = config.mesUpToken ?: mesUpTokenProvider()
        return if (token.isNullOrBlank()) {
            emptyMap()
        } else {
            mapOf(JiangNetworkHeaders.MES_UP_TOKEN to token)
        }
    }

    private fun Throwable.toJiangApiException(): JiangApiException {
        if (this is JiangApiException) {
            return this
        }
        val error = NetworkExceptionMapper.map(this)
        val exception = error.exception
        return JiangApiException(
            code = exception.code,
            message = exception.message,
            throwable = exception.throwable ?: this,
        )
    }

    private companion object {
        const val DEFAULT_BUFFER_SIZE = 8 * 1024
    }
}
