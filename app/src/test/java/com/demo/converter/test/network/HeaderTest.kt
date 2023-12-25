package com.demo.converter.test.network

import com.demo.converter.common.extension.isNotNullOrEmpty
import com.demo.converter.data.network.Header
import com.demo.converter.data.repository.CurrencyApi
import com.demo.converter.domain.entity.isSuccess
import com.demo.converter.rule.MainCoroutineRule
import com.demo.converter.rule.MockWebServerRule
import com.demo.converter.rule.koinTestRule
import com.demo.converter.utils.setResponsePath
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.test.KoinTest
import kotlin.test.assertTrue

class HeaderTest : KoinTest {

    private val api: CurrencyApi by inject()

    @get:Rule
    val mainDispatcherRule: MainCoroutineRule = get()

    companion object{
        @ClassRule(order = 0) @JvmField val koinRule = koinTestRule
        @ClassRule(order = 1) @JvmField val mockWebServerRule = MockWebServerRule()
        private val mockWebServer get() = mockWebServerRule.mockWebServer
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test header`(){
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setResponsePath("exchange_rate/exchange_rate_success_response.json"))
            val response = api.getBaseExchangeRate().await()
            val request = mockWebServer.takeRequest()
            assertTrue(response.isSuccess)
            assertTrue(request.getHeader(Header.Key.AUTHORIZATION).isNotNullOrEmpty())
            assertTrue(request.getHeader(Header.Key.AUTHORIZATION)?.takeIf { it.startsWith("Token") }!= null)
        }
    }
}