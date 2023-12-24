package com.demo.converter.domain.entity

import com.demo.converter.common.extension.toFormattedDouble


data class CurrencyConversion(
    val baseCode: String,
    val code: String,
    val rate: Double,
    val name: String,
    val baseAmount:Double
){
    val amount = (baseAmount * rate).toFormattedDouble()
}