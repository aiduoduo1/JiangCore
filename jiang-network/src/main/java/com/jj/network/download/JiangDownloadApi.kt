package com.jj.network.download

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Streaming
import retrofit2.http.Url

interface JiangDownloadApi {

    @Streaming
    @GET
    suspend fun download(
        @Url url: String,
        @HeaderMap headers: Map<String, String>,
    ): Response<ResponseBody>
}
