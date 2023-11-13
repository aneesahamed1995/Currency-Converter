package com.kfh.converter.domain.entity

import com.kfh.converter.data.network.ErrorType

data class Error(
    val errorType: Int = 0,
    val errorCode: Int = 0,
    val errorMessage: String? = null,
    var apiId:Int = 0
){
    val isApiError = errorType == ErrorType.API_ERROR
}

val Error?.canShowError get() = this?.let {
    when(errorType){
        ErrorType.UNKNOWN_ERROR,
        ErrorType.API_UNKNOWN_ERROR,
        ErrorType.API_ERROR,
        ErrorType.NO_NETWORK,
        ErrorType.NETWORK_ERROR -> true
        else -> false
    }
}?:false

val Error?.canShowErrorMessage get() = this?.let { canShowError && !isApiError }?:false

val Error?.canShowErrorDialog get() = this?.let { canShowError && isApiError }?:false
