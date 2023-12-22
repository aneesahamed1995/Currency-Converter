package com.demo.converter.domain.entity

import com.demo.converter.common.AppConstant

data class CurrencyExchangeRate(
    val baseCode: String,
    val code: String,
    val rate: Double,
    val name: String = AppConstant.EMPTY
)