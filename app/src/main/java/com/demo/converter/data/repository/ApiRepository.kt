package com.demo.converter.data.repository

import com.demo.converter.data.network.ApiEndPoint
import com.demo.converter.data.network.response.ExchangeRateResponse
import com.demo.converter.data.network.retrofit.ResultCall
import retrofit2.http.GET


interface CurrencyApi{
    @GET(ApiEndPoint.GET_ALL_CURRENCY)
    fun getAllCurrencies():ResultCall<Map<String,String>>

    @GET(ApiEndPoint.GET_EXCHANGE_RATE)
    fun getBaseExchangeRate():ResultCall<ExchangeRateResponse>
}