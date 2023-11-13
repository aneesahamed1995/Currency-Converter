package com.kfh.converter.view.model

import com.kfh.converter.domain.entity.Error

sealed class UIState<T> {

    data class Failure<T>(val errorData: Error): UIState<T>()

    data class Loading<T>(val isLoading: Boolean = false , val apiId:Int = 0): UIState<T>()
}