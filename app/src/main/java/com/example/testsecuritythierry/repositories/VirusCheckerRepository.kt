package com.example.testsecuritythierry.repositories

import com.example.testsecuritythierry.config.maxConcurrentConnectionsOnVirusTotal
import com.example.testsecuritythierry.config.virusTotalBaseUrl
import com.example.testsecuritythierry.http.*
import com.example.testsecuritythierry.models.AnalysisResultError
import com.example.testsecuritythierry.models.AnalysisResultNoThreat
import com.example.testsecuritythierry.models.AnalysisResultVirusFound
import com.example.testsecuritythierry.models.DataVirusTotalFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent
import java.util.*
import kotlin.system.measureTimeMillis

/*
* Quotas in the Free VirusTotal account:
* Request rate	4 lookups / min
* Daily quota   500 lookups / day
* */
class VirusCheckerRepository: KoinComponent {

    private var virusTotalRawApiKey = ""
    // to be used wit ha x-apikey header
    private val virusTotalApiKey: String
        get() {
            val rawKey = "$virusTotalRawApiKey=="
            val decoder: Base64.Decoder = Base64.getDecoder()
            return String(decoder.decode(rawKey))
        }

    private lateinit var api: VirusTotalApi

    fun init(virusTotalRawApiKeyIn: String) {
        virusTotalRawApiKey = virusTotalRawApiKeyIn
        createApi()
    }

    private fun createApi() = run {
        api = RetrofitHelper.getInstance(
            baseUrl = virusTotalBaseUrl,
            maxConnections = maxConcurrentConnectionsOnVirusTotal,
            requestHeader = Pair(first = "x-apikey", second = virusTotalApiKey)
        ).create(VirusTotalApi::class.java)
    }

    // https://stackoverflow.com/questions/58658630/parallel-request-with-retrofit-coroutines-and-suspend-functions
    @OptIn(FlowPreview::class)
    fun analyseFileHashes(hashes: List<String>): Flow<Pair<String, Any>> = hashes
        .asFlow()
        .flowOn(Dispatchers.IO)
        .flatMapMerge(concurrency = maxConcurrentConnectionsOnVirusTotal) { hash ->
            flow {
                val elapsed = measureTimeMillis {
                    //    withContext(Dispatchers.IO) {
                    try {
                        val response = api.analyseFileHash(hash)
                        // code 404 means no virus
                        if (!response.isSuccessful && response.code() == 404) {
                            val result = AnalysisResultNoThreat()
                            emit(hash to result)
                        }
                        // other error
                        if (!response.isSuccessful && response.code() != 404) {
                            val result = AnalysisResultError()
                            emit(hash to result)
                        }
                        // virus found
                        if (response.isSuccessful) {
                            val result = AnalysisResultVirusFound()
                            emit(hash to result)
                        }
                    } catch(e: Throwable) {
                        val result = AnalysisResultError()
                        emit(hash to result)
                    }
                }
                //println(elapsed)
                // we make sure the flow task takes more than one minute
                delay(maxOf(0L, 10 * 1000L - elapsed)) // TEMPORARY 10s
            }
        }

    suspend fun <K, V> Flow<Pair<K, V>>.toMap(): Map<K, V> {
        val result = mutableMapOf<K, V>()
        collect { (k, v) -> run {
            result[k] = v
        }}
        return result
    }
}