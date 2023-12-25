package com.demo.converter.test.repository

import com.demo.converter.domain.entity.isSuccess
import com.demo.converter.domain.repository.CurrencyRepository
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
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyRepositoryTest:KoinTest {

    private val currencyRepository:CurrencyRepository by inject()
    @get:Rule
    val mainDispatcherRule: MainCoroutineRule = get()

    companion object{
        @ClassRule(order = 0) @JvmField val koinRule = koinTestRule
        @ClassRule(order = 1) @JvmField val mockWebServerRule = MockWebServerRule()
        private val mockWebServer get() = mockWebServerRule.mockWebServer
    }

    @Test
    fun `get Currency list success response`(){
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setResponsePath("currencies/currencies_response.json"))
            val response = currencyRepository.getCurrencies()
            assertTrue(response.isNotEmpty())
        }
    }

    @Test
    fun `get Currency list empty response`(){
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setResponsePath("currencies/currencies_empty_response.json"))
            val response = currencyRepository.getCurrencies()
            assertTrue(response.isEmpty())
        }
    }

    @Test
    fun `get Currency list when cache available`(){
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setResponsePath("currencies/currencies_response.json"))
            val response1 = currencyRepository.getCurrencies()
            assertTrue(response1.isNotEmpty())
            val response2 = currencyRepository.getCurrencies()
            assertTrue(response2.isNotEmpty())
            assertTrue(mockWebServer.requestCount == 1)
        }
    }

    @Test
    fun `sync currency Exchange Rate`(){
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setResponsePath("exchange_rate/exchange_rate_success_response.json"))
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setResponsePath("exchange_rate/exchange_rate_success_response.json"))
            val response = currencyRepository.syncExchangeRates("EUR")
            assertTrue(response.isSuccess)
            val eurExchangeRates = currencyRepository.getExchangeRates("EUR")
            assertTrue(eurExchangeRates.isNotEmpty())
            assertTrue(eurExchangeRates[0].baseCode == "EUR")
            currencyRepository.syncExchangeRates("AED")
            val aedExchangeRates = currencyRepository.getExchangeRates("AED")
            assertTrue(aedExchangeRates.isNotEmpty())
            assertTrue(aedExchangeRates[0].baseCode == "AED")
            assertTrue(currencyRepository.getExchangeRates("INR").isEmpty())
        }
    }



}