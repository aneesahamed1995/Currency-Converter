package com.demo.converter.data.network.retrofit
import com.demo.converter.data.network.NetworkStateManager
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NetworkResponseAdapter<R : Any>(
    private val successType: Type,
    private val moshi: Moshi,
    private val networkStateManager: NetworkStateManager,
    private val ioDispatcher: CoroutineDispatcher
):CallAdapter<R, ResultCall<R>> {
    override fun responseType(): Type = successType

    override fun adapt(call: Call<R>): ResultCall<R> = ResultCallImpl(call,moshi,networkStateManager,ioDispatcher)
}