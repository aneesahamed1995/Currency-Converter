package com.kfh.converter.data.repository

import com.kfh.converter.data.network.ApiEndPoint
import com.kfh.converter.data.network.response.CurrencyListResponse
import com.kfh.converter.data.network.response.ExchangeRateResponse
import com.kfh.converter.data.network.retrofit.ResultCall
import com.kfh.converter.data.network.response.BankDetailResponse
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