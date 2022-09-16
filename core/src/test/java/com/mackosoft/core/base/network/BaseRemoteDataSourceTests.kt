package com.mackosoft.core.base.network

import com.mackosoft.core.base.BaseTests
import io.mockk.unmockkAll
import org.junit.AfterClass
import org.junit.Rule

abstract class BaseRemoteDataSourceTests : BaseTests() {

    @get:Rule
    protected val mockWebServerRule = MockWebServerRule()
}