package com.mackosoft.core.test.base.network

import android.annotation.SuppressLint
import androidx.annotation.CallSuper
import com.mackosoft.core.base.datasource.BaseRemoteDataSource
import com.mackosoft.core.base.datasource.api.RetrofitClient
import com.mackosoft.core.test.base.BaseTests
import io.mockk.every
import io.mockk.mockkObject
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule

abstract class BaseRemoteDataSourceTests<RemoteDataSource : BaseRemoteDataSource> : BaseTests() {

    companion object {
        @BeforeClass
        @JvmStatic
        fun init() {
            mockkObject(RetrofitClient)
        }
    }

    @get:Rule
    val mockWebServerRule = MockWebServerRule()

    protected abstract val remoteDataSource: RemoteDataSource

    @SuppressLint("VisibleForTests")
    @Before
    @CallSuper
    open fun setupTest() {
        // Retrofit
        every {
            RetrofitClient.apiBaseUrl
        } returns mockWebServerRule.server.url("/").toString()
    }
}