package com.example.testsecuritythierry.http

// Retrofit interface

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VirusTotalApi {
    // https://developers.virustotal.com/reference/file-info
    @GET("/files/{id}")
    suspend fun analyseFileHash(@Path("id") idStr: String): Response<DataVirusTotalFile>
}
