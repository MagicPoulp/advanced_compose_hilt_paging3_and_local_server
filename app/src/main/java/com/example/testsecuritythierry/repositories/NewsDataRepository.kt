package com.example.testsecuritythierry.repositories

import com.example.testsecuritythierry.config.*
import com.example.testsecuritythierry.http.*
import com.example.testsecuritythierry.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent
import java.util.*
import kotlin.system.measureTimeMillis

const val oneMinute = 60 * 1000L

class NewsDataRepository: KoinComponent {

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
