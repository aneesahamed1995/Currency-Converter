package com.demo.converter.view.converter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.demo.converter.domain.repository.CurrencyRepository

data class CurrencyConversionUiState(
    val showExchangeRateLoading:Boolean = false
)

class ConverterViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val currencyRepository: CurrencyRepository
) :ViewModel() {


}