package com.example.testsecuritythierry.http

// Retrofit interface

import com.example.testsecuritythierry.models.DataVirusTotalFile
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface VirusTotalApi {
    // https://developers.virustotal.com/reference/file-info
    @GET("/files/{id}")
    suspend fun analyseFileHash(@Path("id") idStr: String): Response<DataVirusTotalFile>
}
