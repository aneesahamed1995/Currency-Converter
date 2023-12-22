package com.demo.converter.domain.entity

sealed class Result<out T : Any>{
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Failure(val error: Error) : Result<Nothing>()
}
val <T : Any>Result<T>.value get() = (this as Result.Success).data
val <T : Any>Result<T>.isSuccess get() = this is Result.Success
val <T : Any>Result<T>.isError get() = this is Result.Failure
val <T : Any>Result<T>.error get() = (this as Result.Failure).error
fun <T : Any>Result<T>.successOr(fallback:T):T = (this as? Result.Success)?.data?:fallback