package com.mackosoft.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient

class OkHttpClientBuilder {
    private val builder: OkHttpClient.Builder

    internal constructor(okHttpClient: OkHttpClient) {
        builder = okHttpClient.newBuilder()
    }

    internal constructor() {
        builder = OkHttpClient.Builder()
    }

    var interceptors: List<Interceptor> = emptyList()

    fun build(): OkHttpClient {
        interceptors.forEach(builder::addInterceptor)
        return builder.build()
    }
}

/**
 * Create a new [OkHttpClient] instance.
 *
 * @param block lambda to set parameters to the new [OkHttpClient] instance
 * @see [OkHttpClientBuilder]
 */
fun okHttpClient(
    block: OkHttpClientBuilder.() -> Unit
) = OkHttpClientBuilder().apply(block).build()

/**
 * Create a shared [OkHttpClient] instance with the one given in parameter.
 *
 * @param baseOkHttpClient client from which the instance will be shared
 * @param block optional lambda to set parameters to the new [OkHttpClient] instance
 * @see [OkHttpClientBuilder]
 */
fun okHttpClient(
    baseOkHttpClient: OkHttpClient,
    block: (OkHttpClientBuilder.() -> Unit)? = null
): OkHttpClient {
    val builder = OkHttpClientBuilder(baseOkHttpClient)
    block?.let(builder::apply)
    return builder.build()
}
