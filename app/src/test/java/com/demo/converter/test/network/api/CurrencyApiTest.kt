package com.demo.converter.test.network.api

import com.demo.converter.data.repository.CurrencyApi
import com.demo.converter.rule.MainCoroutineRule
import com.demo.converter.rule.MockWebServerRule
import com.demo.converter.rule.koinTestRule
import com.demo.converter.utils.HttpMethod
import com.demo.converter.utils.hasBody
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.test.KoinTest
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyApiTest: KoinTest {

    @get:Rule
    val mainDispatcherRule: MainCoroutineRule = get()

    private val api:CurrencyApi by inject()

    companion object{
        @ClassRule(order = 0) @JvmField val koinRule = koinTestRule
        @ClassRule(order = 1) @JvmField val mockWebServerRule = MockWebServerRule()
        private val mockWebServer get() = mockWebServerRule.mockWebServer
    }

    @Test
    fun `test ExchangeRate API`(){
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(""))
            api.getBaseExchangeRate().await()
            val request = mockWebServer.takeRequest()
            assertEquals(request.method, HttpMethod.GET)
            assertEquals(request.requestUrl!!.querySize,0)
            assertFalse(request.hasBody())
            assertTrue(request.requestUrl!!.toUrl().toString().isNotEmpty())
        }
    }

    @Test
    fun `test Currencies API`(){
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(""))
            api.getAllCurrencies().await()
            val request = mockWebServer.takeRequest()
            assertEquals(request.method, HttpMethod.GET)
            assertEquals(request.requestUrl!!.querySize,0)
            assertFalse(request.hasBody())
            assertTrue(request.requestUrl!!.toUrl().toString().isNotEmpty())
        }
    }
}