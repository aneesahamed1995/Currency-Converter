package com.demo.converter.test.network

import com.demo.converter.data.network.ErrorType
import com.demo.converter.data.repository.CurrencyApi
import com.demo.converter.domain.entity.error
import com.demo.converter.rule.MainCoroutineRule
import com.demo.converter.rule.MockWebServerRule
import com.demo.converter.rule.koinTestRule
import com.demo.converter.utils.setResponsePath
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.SocketPolicy
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.test.KoinTest
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkErrorTest: KoinTest {


    private val currencyApi: CurrencyApi by inject()

    @get:Rule
    val mainDispatcherRule: MainCoroutineRule = get()

    companion object{
        @ClassRule(order = 0) @JvmField val koinRule = koinTestRule
        @ClassRule(order = 1) @JvmField val mockWebServerRule = MockWebServerRule()
        private val mockWebServer get() = mockWebServerRule.mockWebServer
    }

    @Test
    fun `test api known Error`(){
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(412).setResponsePath("exchange_rate/exchange_rate_failed_response.json"))
            val response = currencyApi.getBaseExchangeRate().await()
            mockWebServer.takeRequest()
            assertEquals(response.error.errorType, ErrorType.API_ERROR)
        }
    }

    @Test
    fun `test slow network Error`(){
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setResponsePath("exchange_rate/exchange_rate_failed_response.json").throttleBody(1,15, TimeUnit.SECONDS))
            val response = currencyApi.getBaseExchangeRate().await()
            mockWebServer.takeRequest()
            assertEquals(response.error.errorType, ErrorType.NETWORK_ERROR)
        }
    }

    @Test
    fun `test unknown failed Error`(){
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(500).setResponsePath("exchange_rate/exchange_rate_failed_response.json").setSocketPolicy(SocketPolicy.DISCONNECT_DURING_REQUEST_BODY))
            val response = currencyApi.getBaseExchangeRate().await()
            mockWebServer.takeRequest()
            assertEquals(response.error.errorCode , 500)
        }
    }
}