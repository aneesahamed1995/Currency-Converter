package com.kfh.converter.view.converter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.kfh.converter.common.BundleProperty
import com.kfh.converter.common.extension.getOrDefault
import com.kfh.converter.common.extension.toFormattedDouble
import com.kfh.converter.common.extension.toSafeDouble
import com.kfh.converter.data.network.NetworkConstant
import com.kfh.converter.domain.entity.Error
import com.kfh.converter.domain.repository.BankRepository
import com.kfh.converter.view.base.BaseViewModel
import com.kfh.converter.view.mapper.BankDataUiStateMapper
import com.kfh.converter.view.model.BankUiState
import com.kfh.converter.view.model.CurrencyExchangeUiState
import com.kfh.converter.view.model.SingleEvent
import com.kfh.converter.view.model.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ConverterViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val bankRepository: BankRepository,
    private val bankDataUiStateMapper: BankDataUiStateMapper
) : BaseViewModel() {


    private val _bank:MutableLiveData<SingleEvent<BankUiState>> by lazy { MutableLiveData() }
    val bank:LiveData<SingleEvent<BankUiState>> get() = _bank

    var exchangeRate:CurrencyExchangeUiState? get() = savedStateHandle.get(BundleProperty.SELECTED_EXCHANGE_RATE)
        set(value) = savedStateHandle.set(BundleProperty.SELECTED_EXCHANGE_RATE,value)

    val exchangeRateFlow:StateFlow<CurrencyExchangeUiState?> = savedStateHandle.getStateFlow(BundleProperty.SELECTED_EXCHANGE_RATE,null)

    private val fromCurrency = MutableStateFlow(0.0)
    private val toCurrency = MutableStateFlow(0.0)

    val toCurrencyFlow: StateFlow<String> = combine(
        fromCurrency,
        exchangeRateFlow
    ) { currency, exchangeRate ->
        val rate = exchangeRate?.exchangeRate.getOrDefault<Double>()
        if (rate != 0.0) (currency/ rate).toFormattedDouble().toString() else "0"
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "0")

    val fromCurrencyFlow: StateFlow<String> = combine(
        toCurrency,
        exchangeRateFlow
    ) { currency, exchangeRate ->
        if (currency != 0.0) (exchangeRate?.exchangeRate.getOrDefault<Double>() * currency).toFormattedDouble().toString() else "0"
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "0")



    fun updateFromCurrency(value:String?){
        if (fromCurrency.value != value.toSafeDouble()){
            fromCurrency.value = value.toSafeDouble()
        }
    }

    fun updateToCurrency(value:String?){
        if (toCurrency.value != value.toSafeDouble()){
            toCurrency.value = value.toSafeDouble()
        }
    }


    fun validate(iban:String){
        getResult(viewModelScope,{bankRepository.validateIban(iban)},{ bank->
            _bank.value = SingleEvent(bankDataUiStateMapper.mapFrom(bank))
        },true,NetworkConstant.ApiRequestID.VALIDATE_IBAN)
    }

    override fun onFailure(error: Error) {
        showAPIProgress(false,error.apiId)
        _uiState.value = SingleEvent( UIState.Failure(error))
    }

    override fun showAPIProgress(show: Boolean, apiId: Int) {
        _uiState.value = SingleEvent(UIState.Loading(show,apiId))
    }
}