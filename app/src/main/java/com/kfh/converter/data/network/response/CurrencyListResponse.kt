package com.kfh.converter.data.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CurrencyListResponse(
    @SerializedName("success") val success:Boolean? = null,
    @SerializedName("symbols") @Expose val symbols:Map<String,String>? = null
):BaseResponse()