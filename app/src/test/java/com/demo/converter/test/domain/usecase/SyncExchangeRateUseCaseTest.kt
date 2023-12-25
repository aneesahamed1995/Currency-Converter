package com.demo.converter.test.domain.usecase

import com.demo.converter.data.network.ErrorType
import com.demo.converter.domain.entity.Error
import com.demo.converter.domain.entity.Result
import com.demo.converter.domain.entity.error
import com.demo.converter.domain.entity.isError
import com.demo.converter.domain.entity.isSuccess
import com.demo.converter.domain.repository.CurrencyRepository
import com.demo.converter.domain.usecase.GetCurrencyListUseCase
import com.demo.converter.domain.usecase.SyncExchangeRateUseCase
import com.demo.converter.rule.MainCoroutineRule
import com.demo.converter.rule.koinTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SyncExchangeRateUseCaseTest:KoinTest {

    @get:Rule
    val mainDispatcherRule: MainCoroutineRule = get()

    companion object{
        @ClassRule(order = 0) @JvmField val koinRule = koinTestRule
        @ClassRule(order = 1) @JvmField val mockProvider = MockProviderRule.create {
            Mockito.mock(it.java)
        }
    }

    @Test
    fun `get success response`(){
        runTest {
            val currencyRepository = declareMock<CurrencyRepository>()
            whenever(currencyRepository.syncExchangeRates("USD")) doReturn Result.Success(Unit)
            val useCase = get<SyncExchangeRateUseCase>()
            val result = useCase.execute("USD")
            assertTrue(result.isSuccess)
        }
    }

    @Test
    fun `get error response`(){
        runTest {
            val currencyRepository = declareMock<CurrencyRepository>()
            whenever(currencyRepository.syncExchangeRates("USD")) doReturn Result.Failure(Error(ErrorType.NETWORK_ERROR))
            val useCase = get<SyncExchangeRateUseCase>()
            val result = useCase.execute("USD")
            assertTrue(result.isError)
            assertTrue(result.error.errorType == ErrorType.NETWORK_ERROR)
        }
    }
}