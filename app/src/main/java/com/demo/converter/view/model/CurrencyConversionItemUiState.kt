package com.demo.converter.view.model


data class CurrencyConversionItemUiState(
    val name:String,
    val code:String,
    val baseCode:String,
    val exchangeRate:Double,
    val baseAmount:Double,
    val amount:Double
)