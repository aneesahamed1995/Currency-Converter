package com.demo.converter.view.currency_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.converter.common.AppConstant
import com.demo.converter.domain.entity.Currency
import com.demo.converter.domain.entity.Error
import com.demo.converter.domain.entity.error
import com.demo.converter.domain.entity.isError
import com.demo.converter.domain.entity.isSuccess
import com.demo.converter.domain.usecase.GetCurrencyListUseCase
import com.demo.converter.domain.usecase.SyncExchangeRateUseCase
import com.demo.converter.view.mapper.CurrencyItemUiStateMapper
import com.demo.converter.view.model.CurrencyItemUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class CurrencyListUiState(
    val selectedCurrencyCode: String = AppConstant.STRING_EMPTY,
    val currencyUiItems:List<CurrencyItemUiState> = emptyList(),
    val isLoading:Boolean = false,
    val showProgressDialog:Boolean = false,
    val exchangeRatesSynced:Boolean = false,
    val error: Error? = null
)

class CurrencyListViewModel(
    private val selectedCurrencyCode:String,
    private val getCurrencyListUseCase: GetCurrencyListUseCase,
    private val syncExchangeRateUseCase: SyncExchangeRateUseCase,
    private val currencyItemUiStateMapper: CurrencyItemUiStateMapper,
    private val defaultDispatcher: CoroutineDispatcher
) :ViewModel() {


    private val _uiState: MutableStateFlow<CurrencyListUiState> by lazy { MutableStateFlow(CurrencyListUiState(isLoading = true)) }
    val uiState: StateFlow<CurrencyListUiState> = _uiState.asStateFlow()


    fun getCurrencies(){
        viewModelScope.launch {
            val currencies = getCurrencyListUseCase.execute()
            _uiState.update {
                it.copy(
                    currencyUiItems = getCurrencyUiItems(currencies),
                    isLoading = false
                )
            }
        }
    }

    fun getExchangeRates(currencyCode:String){
        viewModelScope.launch {
            _uiState.update { it.copy(showProgressDialog = true) }
            val syncResult = syncExchangeRateUseCase.execute(currencyCode)
            _uiState.update { it.copy(
                selectedCurrencyCode = currencyCode,
                exchangeRatesSynced = syncResult.isSuccess,
                showProgressDialog = false,
                error = takeIf { syncResult.isError }?.let { syncResult.error }
            )
            }
        }
    }

    fun errorMessageShown(){
        _uiState.update {
            it.copy(error = null)
        }
    }

    private suspend fun getCurrencyUiItems(currencies:List<Currency>):List<CurrencyItemUiState> = withContext(defaultDispatcher){
        currencyItemUiStateMapper.mapTo(currencies).toMutableList().also { uiItems->
            val itemIndex = uiItems.indexOfFirst { it.code == selectedCurrencyCode }
            if (itemIndex != -1){
                uiItems[itemIndex] = uiItems[itemIndex].copy(isSelected = true)
            }
        }.sortedWith(compareByDescending { it.isSelected })
    }

}