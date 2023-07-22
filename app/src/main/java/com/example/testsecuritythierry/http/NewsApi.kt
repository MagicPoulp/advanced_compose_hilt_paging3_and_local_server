package com.example.testsecuritythierry.http

// Retrofit interface

import com.example.testsecuritythierry.models.DataNewsFull
import retrofit2.Response
import retrofit2.http.GET

interface NewsApi {
    // https://aec.lemonde.fr/ws/8/mobile/www/android-phone/en_continu/index.json
    // due to a bug we cannot move /api/v3 to the baseUrl
    @GET("/ws/8/mobile/www/android-phone/en_continu/index.json")
    suspend fun getNews(): Response<DataNewsFull>
}