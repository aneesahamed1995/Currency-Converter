package com.demo.converter.domain.entity

import com.demo.converter.data.network.ErrorType

data class Error(
    val errorType: Int = 0,
    val errorCode: Int = 0,
    val errorMessage: String? = null,
    var apiId:Int = 0
){
    val isApiError = errorType == ErrorType.API_ERROR
}
