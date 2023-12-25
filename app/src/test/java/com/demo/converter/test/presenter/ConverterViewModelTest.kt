package com.demo.converter.test.presenter

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.demo.converter.common.BundleProperty
import com.demo.converter.domain.entity.CurrencyConversion
import com.demo.converter.domain.usecase.CurrencyConversionUseCase
import com.demo.converter.rule.MainCoroutineRule
import com.demo.converter.rule.koinTestRule
import com.demo.converter.view.convertion.ConverterViewModel
import kotlinx.coroutines.test.runTest
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import org.koin.test.KoinTest
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ConverterViewModelTest:KoinTest {

    val conversions = mutableListOf<CurrencyConversion>().also { list->
        repeat(10){
            list.add(CurrencyConversion("USD$it","INR$it",83.0,"Indian Rupee",100.0))
        }
    }



    @get:Rule
    val mainDispatcherRule: MainCoroutineRule = get()

    companion object{
        @ClassRule(order = 0) @JvmField val koinRule = koinTestRule
        @ClassRule(order = 1) @JvmField val mockProvider = MockProviderRule.create {
            Mockito.mock(it.java)
        }
    }

    @Test
    fun `get converted Currencies`(){
        runTest {
            val useCase =  declareMock<CurrencyConversionUseCase>()
            whenever(useCase.execute("INR",10.0)) doReturn conversions
            val viewModel = get<ConverterViewModel>{parametersOf(SavedStateHandle(mapOf(BundleProperty.SELECTED_CURRENCY_CODE to "INR")))}
            viewModel.updateAmount(10.0)
            viewModel.uiState.test {
                assert(awaitItem().showExchangeRateLoading)
                val uiState = awaitItem()
                assertTrue(uiState.conversionUiItems.isNotEmpty())
                assertFalse(uiState.showExchangeRateLoading)
            }
        }
    }
}