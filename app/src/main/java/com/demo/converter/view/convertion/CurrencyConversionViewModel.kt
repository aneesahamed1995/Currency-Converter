package com.demo.converter.view.convertion

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.converter.common.AppConstant
import com.demo.converter.common.BundleProperty
import com.demo.converter.domain.usecase.CurrencyConversionUseCase
import com.demo.converter.domain.usecase.SyncExchangeRateUseCase
import com.demo.converter.view.mapper.CurrencyConversionUiItemStateMapper
import com.demo.converter.view.model.CurrencyConversionItemUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CurrencyConversionUiState(
    val showExchangeRateLoading:Boolean = false,
    val conversionUiItems:List<CurrencyConversionItemUiState> = emptyList(),
    val baseCurrencyCode:String = AppConstant.STRING_EMPTY
)

class ConverterViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val syncExchangeRateUseCase: SyncExchangeRateUseCase,
    private val currencyConversionUseCase: CurrencyConversionUseCase,
    private val uiItemStateMapper: CurrencyConversionUiItemStateMapper ,
    defaultDispatcher: CoroutineDispatcher
) :ViewModel() {

    var baseCurrencyCode:String get() = savedStateHandle.get<String>(BundleProperty.SELECTED_CURRENCY_CODE)?:AppConstant.STRING_EMPTY
        set(value) = savedStateHandle.set(BundleProperty.SELECTED_CURRENCY_CODE,value)

    val hasBaseCurrency get() = baseCurrencyCode.isNotEmpty()

    private val _uiState = MutableStateFlow(0.0)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val uiState: StateFlow<CurrencyConversionUiState> = _uiState
        .debounce(500)
        .distinctUntilChanged()
        .mapLatest { currencyConversionUseCase.execute(baseCurrencyCode,_uiState.value) }
        .mapLatest { it.toMutableList().also { items-> items.removeAll { item->item.code == baseCurrencyCode } } }
        .mapLatest { CurrencyConversionUiState(false,uiItemStateMapper.mapTo(it),baseCurrencyCode) }
        .flowOn(defaultDispatcher)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            CurrencyConversionUiState(true)
        )

    fun updateAmount(amount: Double){
        _uiState.update { amount }
    }

    fun refreshConversion(){
        _uiState.value = _uiState.value
    }

    fun syncCurrentExchangeRate(){
        viewModelScope.launch {
            syncExchangeRateUseCase.execute(baseCurrencyCode)
        }
    }
}