package com.jj.sample.login

import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("/ca/auth/login")
    suspend fun login(
        @Body request: LoginRequest,
    ): MesBaseResult<String>
}
