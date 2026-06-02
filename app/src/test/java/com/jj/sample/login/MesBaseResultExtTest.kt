package com.jj.sample.login

import com.jj.common.result.JiangResult
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MesBaseResultExtTest {

    @Test
    fun toJiangResultReturnsSuccessWhenBusinessCodeIsSuccess() {
        val result = MesBaseResult(
            code = MesBaseResult.CODE_SUCCESS,
            data = "token",
        ).toJiangResult()

        assertTrue(result is JiangResult.Success<*>)
        assertEquals("token", (result as JiangResult.Success<*>).data)
    }

    @Test
    fun toJiangResultReturnsErrorWhenBusinessCodeFails() {
        val result = MesBaseResult<String>(
            code = 500,
            msg = "Login failed.",
        ).toJiangResult()

        assertTrue(result is JiangResult.Error)
        assertEquals(500, (result as JiangResult.Error).exception.code)
        assertEquals("Login failed.", result.exception.message)
    }
}
