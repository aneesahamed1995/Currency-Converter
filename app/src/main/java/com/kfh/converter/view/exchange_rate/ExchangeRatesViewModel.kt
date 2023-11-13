package com.kfh.converter.view.exchange_rate

import androidx.lifecycle.viewModelScope
import com.kfh.converter.domain.entity.CurrencyExchange
import com.kfh.converter.domain.entity.Error
import com.kfh.converter.domain.usecase.GetCurrencyExchangesUseCase
import com.kfh.converter.view.base.BaseViewModel
import com.kfh.converter.view.mapper.CurrencyExchangeUiStateMapper
import com.kfh.converter.view.model.CurrencyExchangeUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class ExchangeRatesUiState(
    val currencyExchanges:List<CurrencyExchangeUiState> = emptyList(),
    val isLoading:Boolean = false,
    val error:Error? = null
)

class ExchangeRatesViewModel(
    private val selectedCountryCode:String,
    private val getCurrencyExchangesUseCase: GetCurrencyExchangesUseCase,
    private val currencyExchangeUiStateMapper: CurrencyExchangeUiStateMapper,
    private val defaultDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val baseCurrency = "KWD"

    private val _profileUiState: MutableStateFlow<ExchangeRatesUiState> by lazy { MutableStateFlow(ExchangeRatesUiState(isLoading = true)) }
    val profileUiState: StateFlow<ExchangeRatesUiState> = _profileUiState.asStateFlow()

    fun getExchangeRates(){
        _profileUiState.update { it.copy(isLoading = true, error = null) }
        execute(viewModelScope,{ getCurrencyExchangesUseCase.execute(baseCurrency)},{ currencyExchanges->
            updateExchangeRates(currencyExchanges)
        },{ error->
            _profileUiState.update { it.copy(isLoading = false, error = error) }
        })
    }

    private fun updateExchangeRates(currencyExchanges:List<CurrencyExchange>){
        viewModelScope.launch {
            _profileUiState.update { it.copy(withContext(defaultDispatcher){ currencyExchangeUiStateMapper.mapFrom(currencyExchanges).also { it.find { it.code == selectedCountryCode }?.isSelected = true }},false,null) }
        }
    }
}