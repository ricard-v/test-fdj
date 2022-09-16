package com.mackosoft.core.base.network

import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class MockWebServerRule : TestRule {

    lateinit var server: MockWebServer

    override fun apply(base: Statement, description: Description) = object : Statement() {
        override fun evaluate() {
            startMockWebServer()
            try {
                base.evaluate()
            } finally {
                stopMockWebServer()
            }
        }
    }

    fun startMockWebServer() {
        server = MockWebServer()
        server.startForMultipleTests()
    }

    fun stopMockWebServer() {
        server.shutdown()
    }
}