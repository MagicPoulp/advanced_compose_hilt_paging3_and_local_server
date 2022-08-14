package com.example.testsecuritythierry.http


import com.example.testsecuritythierry.BuildConfig
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


// https://www.geeksforgeeks.org/retrofit-with-kotlin-coroutine-in-android/
// https://github.com/AsyncHttpClient/async-http-client/tree/master/extras/retrofit2
// https://stackoverflow.com/questions/52881862/throttle-or-limit-kotlin-coroutine-count
// add a header:
// https://stackoverflow.com/questions/32605711/adding-header-to-all-request-with-retrofit-2
object RetrofitHelper {

    // we can specify the base url, the max number of concurrent connections, and an extra API key header
    fun getInstance(baseUrl: String, maxConnections: Int, requestHeader: Pair<String,String>): Retrofit {

        val dispatcher = Dispatcher()
        dispatcher.maxRequests = maxConnections

        val builder = OkHttpClient().newBuilder()
        builder.dispatcher(dispatcher)
        builder.readTimeout(10, TimeUnit.SECONDS)
        builder.connectTimeout(1, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            builder.addInterceptor(interceptor)
        }

        builder.addInterceptor { chain: Interceptor.Chain ->
            val request: Request = chain.request().newBuilder()
                .addHeader(requestHeader.first, requestHeader.second)
                .build()
            chain.proceed(request)
        }

        val client = builder.build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
