package com.kfh.converter.data.network.retrofit
import com.kfh.converter.data.network.NetworkStateManager
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResultCallAdapterFactory(
    private val networkStateManager: NetworkStateManager,
    private val ioDispatcher: CoroutineDispatcher
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *>? {
        if (ResultCall::class.java!= getRawType(returnType)) return null
        check(returnType is ParameterizedType){ "Return type must be a parameterized type." }
        val responseType = getParameterUpperBound(0, returnType)
        return NetworkResponseAdapter<Any>(responseType,networkStateManager,ioDispatcher)
    }
}