package com.demo.converter.data.repository

import com.demo.converter.data.network.ApiEndPoint
import com.demo.converter.data.network.response.CurrencyListResponse
import com.demo.converter.data.network.response.ExchangeRateResponse
import com.demo.converter.data.network.retrofit.ResultCall
import com.demo.converter.data.network.response.BankDetailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BankApi{
    @GET(ApiEndPoint.IBAN_VALIDATE)
    fun validateIban(@Query("iban_number") ibanNumber:String): ResultCall<BankDetailResponse>
}

interface CurrencyApi{
    @GET(ApiEndPoint.GET_ALL_CURRENCY)
    fun getAllCurrencies():ResultCall<CurrencyListResponse>

    @GET(ApiEndPoint.GET_EXCHANGE_RATE)
    fun getExchangeRate(@Query("base") baseCurrency:String):ResultCall<ExchangeRateResponse>
}