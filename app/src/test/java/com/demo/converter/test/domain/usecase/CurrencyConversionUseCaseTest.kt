package com.demo.converter.test.domain.usecase

import com.demo.converter.domain.entity.CurrencyExchangeRate
import com.demo.converter.domain.repository.CurrencyRepository
import com.demo.converter.domain.usecase.CurrencyConversionUseCase
import com.demo.converter.rule.MainCoroutineRule
import com.demo.converter.rule.koinTestRule
import kotlinx.coroutines.test.runTest
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.koin.core.component.get
import org.koin.test.KoinTest
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

class CurrencyConversionUseCaseTest:KoinTest {

    @get:Rule
    val mainDispatcherRule: MainCoroutineRule = get()

    val exchangeRate = mutableListOf<CurrencyExchangeRate>().also { list->
        repeat(10){
            list.add(CurrencyExchangeRate("USD","INR$it",it*83.0))
        }
    }

    companion object{
        @ClassRule(order = 0) @JvmField val koinRule = koinTestRule
        @ClassRule(order = 1) @JvmField val mockProvider = MockProviderRule.create {
            Mockito.mock(it.java)
        }
    }

    @Test
    fun `get currencyList with conversion`(){
        runTest {
            val currencyRepository = declareMock<CurrencyRepository>()
            whenever(currencyRepository.getExchangeRates("USD")) doReturn exchangeRate
            val useCase = get<CurrencyConversionUseCase>()
            val conversionList = useCase.execute("USD",100.0)
            assert(conversionList.isNotEmpty())
        }
    }
}