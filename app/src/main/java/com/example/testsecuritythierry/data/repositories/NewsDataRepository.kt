package com.example.testsecuritythierry.data.repositories

import com.example.testsecuritythierry.data.config.newsBaseUrl
import com.example.testsecuritythierry.data.custom_structures.ResultOf
import com.example.testsecuritythierry.data.http.NewsApi
import com.example.testsecuritythierry.data.http.RetrofitHelper
import com.example.testsecuritythierry.data.models.DataNewsElement
import com.example.testsecuritythierry.ui.setup.safeSubList
import javax.inject.Inject

const val oneMinute = 60 * 1000L

class NewsDataRepository @Inject constructor() {

    private lateinit var api: NewsApi
    private var initialized = false

    private fun createApi() = run {
        api = RetrofitHelper.getInstance(
            baseUrl = newsBaseUrl,
            //requestHeader = Pair(first = "x-apikey", second = virusTotalApiKey)
        ).create(NewsApi::class.java)
    }

    /*
    fun getNewsFlow(pageSize: Int, pageOffset: Int): Flow<List<DataNewsElement>> = flow {
        if (!initialized) {
            initialized = true
            createApi()
        }
        val response = api.getNews(pageSize, pageOffset)
        if (response.isSuccessful) {
            response.body()?.let { emit(it.elements.filter { it2 -> it2.titre != null }) }
            return@flow
        }
    }*/

    suspend fun getNewsPaged(pageSize: Int, pageOffset: Int): ResultOf<List<DataNewsElement>> {
        try {
            if (!initialized) {
                initialized = true
                createApi()
            }
            val response = api.getNewsPaged(pageSize, pageOffset)
            if (response.isSuccessful) {
                response.body()?.let {
                    return ResultOf.Success(it.elements.filter { it2 -> it2.titre != null }
                    .safeSubList((pageOffset - 1) * pageSize, pageOffset * pageSize)) }
            }
            return ResultOf.Failure(response.message(), null)
        } catch (e: Exception) {
            return ResultOf.Failure(e.message, e)
        }
    }

}
