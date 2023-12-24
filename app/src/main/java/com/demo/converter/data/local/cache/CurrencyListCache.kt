package com.demo.converter.data.local.cache

import com.demo.converter.common.extension.isNotNullOrEmpty
import com.demo.converter.domain.entity.CurrencyExchangeRate

data class CurrencyListCache(
    var baseCurrencyCode:String? = null,
    var exchangeRates:List<CurrencyExchangeRate>? = null
){
    val hasCache get() = baseCurrencyCode.isNotNullOrEmpty() && !exchangeRates.isNullOrEmpty()
}