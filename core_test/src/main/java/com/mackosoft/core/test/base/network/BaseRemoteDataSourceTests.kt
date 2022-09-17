package com.mackosoft.core.test.base.network

import com.mackosoft.core.test.base.BaseTests
import org.junit.Rule

abstract class BaseRemoteDataSourceTests : BaseTests() {

    @get:Rule
    protected val mockWebServerRule = MockWebServerRule()
}