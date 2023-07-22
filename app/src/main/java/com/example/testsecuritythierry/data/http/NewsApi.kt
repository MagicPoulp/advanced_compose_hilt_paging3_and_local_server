package com.example.testsecuritythierry.data.http

// Retrofit interface

import com.example.testsecuritythierry.data.models.DataNewsFull
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    // https://aec.lemonde.fr/ws/8/mobile/www/android-phone/en_continu/index.json
    // due to a bug we cannot move /api/v3 to the baseUrl
    @GET("/ws/8/mobile/www/android-phone/en_continu/index.json")
    suspend fun getNews(): Response<DataNewsFull>

    @GET("/ws/8/mobile/www/android-phone/en_continu/index.json")
    suspend fun getNewsPaged(@Query("pageSize") pageSize: Int, @Query("pageOffset") pageOffset: Int): Response<DataNewsFull>
}
