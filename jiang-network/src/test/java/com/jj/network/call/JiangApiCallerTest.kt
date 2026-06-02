package com.jj.network.call

import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.startCoroutine
import org.junit.Test

class JiangApiCallerTest {

    @Test(expected = CancellationException::class)
    fun safeApiCallRethrowsCancellationException() {
        runSuspend {
            JiangApiCaller.safeApiCall<String> {
                throw CancellationException("cancelled")
            }
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
