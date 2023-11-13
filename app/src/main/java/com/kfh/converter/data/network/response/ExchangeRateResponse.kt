package com.kfh.converter.data.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ExchangeRateResponse(
    @SerializedName("success") val success:Boolean? = null,
    @SerializedName("rates") @Expose val rates:Map<String,Double>? = null
):BaseResponse()