package com.kfh.converter.data.network.response

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("message") val message:String? = null,
    @SerializedName("errors") private val _error:ErrorResponse? = null
){
    val detailedErrorMessage get() = _error?.detailedError
}

open class ErrorResponse(
    @SerializedName("iban_number") private val _detailedError:List<String>? = null
){
    val detailedError:String? get() = _detailedError?.getOrNull(0)
}