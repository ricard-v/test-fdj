package com.mackosoft.network

import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

class RetrofitBuilder internal constructor(baseUrl: String) {
    private val builder: Retrofit.Builder

    var okHttpClient: OkHttpClient? = null
    var converterFactories: List<Converter.Factory> = emptyList()

    fun build(): Retrofit {
        okHttpClient?.let(builder::client)
        converterFactories.forEach(builder::addConverterFactory)
        return builder.build()
    }

    init {
        builder = Retrofit.Builder()
            .baseUrl(baseUrl)
    }
}

/**
 * Create a new [Retrofit] instance with the given base URL.
 *
 * @param baseUrl the base URL to use to build the [Retrofit] instance
 * @param block lambda to set parameters to the new [Retrofit] instance
 * @see [RetrofitBuilder]
 */
fun retrofit(
    baseUrl: String,
    block: RetrofitBuilder.() -> Unit
) = RetrofitBuilder(baseUrl).apply(block).build()