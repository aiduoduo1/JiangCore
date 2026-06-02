package com.jj.network.download

import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.Response
import java.io.File
import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.startCoroutine

class JiangFileDownloaderTest {

    @Test(expected = CancellationException::class)
    fun downloadRethrowsCancellationException() {
        runSuspend {
            val downloader = JiangFileDownloader(
                api = object : JiangDownloadApi {
                    override suspend fun download(
                        url: String,
                        headers: Map<String, String>,
                    ): Response<ResponseBody> {
                        throw CancellationException("download cancelled")
                    }
                },
            )

            downloader.download(
                JiangDownloadConfig(
                    url = "https://example.com/app.apk",
                    targetFile = File("build/tmp/cancel/app.apk"),
                ),
            )
        }
    }

    private fun <T> runSuspend(block: suspend () -> T): T {
        var result: Result<T>? = null
        block.startCoroutine(
            object : Continuation<T> {
                override val context = EmptyCoroutineContext

                override fun resumeWith(resumedResult: Result<T>) {
                    result = resumedResult
                }
            },
        )
        return result!!.getOrThrow()
    }
}
