package com.demo.converter.rule

import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.ExternalResource
import org.koin.test.KoinTest
import org.koin.test.get

class MockWebServerRule:ExternalResource(),KoinTest {

    lateinit var mockWebServer: MockWebServer
    override fun before() {
        super.before()
        mockWebServer = get()
        mockWebServer.start()
    }
    override fun after() {
        super.after()
        mockWebServer.shutdown()
    }
}