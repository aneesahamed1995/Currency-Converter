package com.demo.converter.data.network.response


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CurrencyListResponse(
    val symbols:Map<String,String>? = null
):BaseResponse()