package com.example.testsecuritythierry.repositories

import com.example.testsecuritythierry.config.newsBaseUrl
import com.example.testsecuritythierry.http.NewsApi
import com.example.testsecuritythierry.http.RetrofitHelper
import com.example.testsecuritythierry.models.DataNewsElement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

const val oneMinute = 60 * 1000L

class NewsDataRepository @Inject constructor() {

    private lateinit var api: NewsApi
    private var initialized = false

    fun init() {
        //virusTotalRawApiKey = virusTotalRawApiKeyIn
        createApi()
    }

    private fun createApi() = run {
        api = RetrofitHelper.getInstance(
            baseUrl = newsBaseUrl,
            //requestHeader = Pair(first = "x-apikey", second = virusTotalApiKey)
        ).create(NewsApi::class.java)
    }

    fun getNews(): Flow<List<DataNewsElement>> = flow {
        if (!initialized) {
            initialized = true
            createApi()
        }
        val response = api.getNews()
        if (response.isSuccessful) {
            response.body()?.let { emit(it.elements.filter { it2 -> it2.titre != null }) }
            return@flow
        }
    /*
                val elapsed = measureTimeMillis {
                    try {
                        mapHashToVirusStatusHistory[hash]?.let { rememberedValue ->
                            emit(hash to rememberedValue)
                            // we do not delay because we did not use the API
                            return@flow
                        }
                    } catch(e: Throwable) {
                    //    val result = AnalysisResultError()
                    //    emit(hash to result)
                    }
                }
                //println(elapsed)
                // we make sure the flow task takes more than one minute
                delay(maxOf(0L, oneMinute - elapsed))
        */
    }
}
