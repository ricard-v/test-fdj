package com.mackosoft.core.base.datasource.api

import androidx.annotation.VisibleForTesting
import com.mackosoft.network.okHttpClient
import com.mackosoft.network.retrofit
import io.mockk.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // can't be const because of mock
    @VisibleForTesting
    val apiBaseUrl = "https://www.thesportsdb.com/api/v1/json/"

    private val globalOkHttpClient by lazy {
        okHttpClient {
            interceptors = listOf(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG)
                        HttpLoggingInterceptor.Level.BODY
                    else
                        HttpLoggingInterceptor.Level.NONE
                }
            )
        }
    }

    val instance: Retrofit by lazy {
        retrofit(apiBaseUrl) {
            okHttpClient = globalOkHttpClient
            converterFactories = listOf(
                GsonConverterFactory.create()
            )
        }
    }

}