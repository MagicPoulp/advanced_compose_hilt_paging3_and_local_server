package com.example.testsecuritythierry.http

// Retrofit interface

import com.example.testsecuritythierry.models.DataVirusTotalFile
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface VirusTotalApi {
    // https://developers.virustotal.com/reference/file-info
    // due to a bug we cannot move /api/v3 to the baseUrl
    @GET("/api/v3/files/{id}")
    suspend fun analyseFileHash(@Path("id") idStr: String): Response<DataVirusTotalFile>
}
