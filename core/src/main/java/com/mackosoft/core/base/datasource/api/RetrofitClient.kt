package com.mackosoft.core.base.datasource.api

import androidx.annotation.VisibleForTesting
import com.mackosoft.core.BuildConfig
import com.mackosoft.network.okHttpClient
import com.mackosoft.network.retrofit
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // can't be const because of mock
    @VisibleForTesting
    val apiBaseUrl = "https://www.thesportsdb.com/api/v1/json/%s/"
    private const val API_KEY = "50130162"

    val globalOkHttpClient by lazy {
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
        retrofit(apiBaseUrl.format(API_KEY)) {
            okHttpClient = globalOkHttpClient
            converterFactories = listOf(
                GsonConverterFactory.create()
            )
        }
    }

}