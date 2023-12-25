package com.demo.converter.test.network.dataSource

import com.demo.converter.data.dataSource.remote.CurrencyRemoteDataSource
import com.demo.converter.data.network.ErrorType
import com.demo.converter.domain.entity.error
import com.demo.converter.domain.entity.isError
import com.demo.converter.domain.entity.isSuccess
import com.demo.converter.domain.entity.value
import com.demo.converter.rule.MainCoroutineRule
import com.demo.converter.rule.MockWebServerRule
import com.demo.converter.rule.koinTestRule
import com.demo.converter.test.repository.CurrencyRepositoryTest
import com.demo.converter.utils.setResponsePath
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.koin.core.component.get
import org.koin.test.KoinTest
import org.koin.test.inject


@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyRemoteDataSourceTest:KoinTest {

    private val dataSource:CurrencyRemoteDataSource by inject()

    @get:Rule
    val mainDispatcherRule: MainCoroutineRule = get()

    companion object{
        @ClassRule(order = 0) @JvmField val koinRule = koinTestRule
        @ClassRule(order = 1) @JvmField val mockWebServerRule = MockWebServerRule()
        private val mockWebServer get() = mockWebServerRule.mockWebServer
    }

    @Test
    fun `get currencies success response`(){
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setResponsePath("currencies/currencies_response.json"))
            val response = dataSource.getCurrencies()
            assert(response.isSuccess)
            assert(response.value.isNotEmpty())
        }
    }


    @Test
    fun `get empty currencies success response`(){
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setResponsePath("currencies/currencies_empty_response.json"))
            val response = dataSource.getCurrencies()
            assert(response.isSuccess)
            assert(response.value.isEmpty())
        }
    }

    @Test
    fun `get Currencies Malformed success response`(){
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setResponsePath("currencies/currencies_malformed_response.json"))
            val response = dataSource.getCurrencies()
            assert(response.isError)
            assert(response.error.errorType == ErrorType.UNKNOWN_ERROR)
        }
    }

    @Test
    fun `get exchange rate success response`(){
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setResponsePath("exchange_rate/exchange_rate_success_response.json"))
            val response = dataSource.getBaseExchangeRates()
            assert(response.isSuccess)
            assert(response.value.isNotEmpty())
        }
    }

    @Test
    fun `get exchange rate error response`(){
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(412).setResponsePath("exchange_rate/exchange_rate_failed_response.json"))
            val response = dataSource.getBaseExchangeRates()
            assert(response.isError)
            assert(response.error.errorType == ErrorType.API_ERROR)
        }
    }

    @Test
    fun `get exchange rate Malformed response`(){
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setResponsePath("exchange_rate/exchange_rate_malformed_response.json"))
            val response = dataSource.getBaseExchangeRates()
            assert(response.isError)
            assert(response.error.errorType == ErrorType.UNKNOWN_ERROR)
        }
    }
}