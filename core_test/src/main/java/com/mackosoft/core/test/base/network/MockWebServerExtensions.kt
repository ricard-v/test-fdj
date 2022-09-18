package com.mackosoft.core.test.base.network

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

fun MockWebServer.setResponseAndEnqueue(response: String) {
    enqueue(MockResponse().setBody(response))
}

fun MockWebServer.assertPathMatch(expectedPath: String) {
    val requestPath = takeRequest().path
    assert(requestPath == expectedPath) {
        "Expected to have a $expectedPath but instead got $requestPath"
    }
}

// constant port : https://stackoverflow.com/questions/37965558/okhttp-mockwebserver-fails-to-accept-connections-when-restarted#comment109123321_37965558
fun MockWebServer.startForMultipleTests() = start(8008)
