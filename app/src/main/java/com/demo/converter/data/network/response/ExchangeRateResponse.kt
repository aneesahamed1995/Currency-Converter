package com.demo.converter.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ExchangeRateResponse(
    @Json(name = "rates")  val rates:Map<String,Double>? = null
):BaseResponse()