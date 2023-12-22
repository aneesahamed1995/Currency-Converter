package com.demo.converter.view.currency_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.converter.domain.entity.Currency
import com.demo.converter.domain.repository.CurrencyRepository
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
    val currencyExchanges:List<CurrencyItemUiState> = emptyList(),
    val isLoading:Boolean = false
)

class CurrencyListViewModel(
    private val selectedCurrencyCode:String,
    private val currencyRepository: CurrencyRepository,
    private val currencyItemUiStateMapper: CurrencyItemUiStateMapper,
    private val defaultDispatcher: CoroutineDispatcher
) :ViewModel() {


    private val _uiState: MutableStateFlow<CurrencyListUiState> by lazy { MutableStateFlow(CurrencyListUiState(isLoading = true)) }
    val uiState: StateFlow<CurrencyListUiState> = _uiState.asStateFlow()


    fun getCurrencies(){
        viewModelScope.launch {
            val currencies = currencyRepository.getCurrencies()
            _uiState.update {
                it.copy(
                    currencyExchanges = getCurrencyUiItems(currencies),
                    isLoading = false
                )
            }
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