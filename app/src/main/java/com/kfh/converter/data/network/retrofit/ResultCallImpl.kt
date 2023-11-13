package com.kfh.converter.data.network.retrofit

import com.kfh.converter.common.extension.getOrDefault
import com.kfh.converter.common.extension.isNotNullOrEmpty
import com.kfh.converter.common.extension.toObject
import com.kfh.converter.data.network.ErrorType
import com.kfh.converter.data.network.NetworkStateManager
import com.kfh.converter.data.network.response.BaseResponse
import com.kfh.converter.domain.entity.Error
import com.kfh.converter.domain.entity.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import java.io.PrintWriter
import java.io.StringWriter
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import kotlin.coroutines.cancellation.CancellationException

class ResultCallImpl<R : Any>(
    private val delegate: Call<R>,
    private val networkStateManager: NetworkStateManager,
    private val ioDispatcher: CoroutineDispatcher
) : ResultCall<R> {

    override fun get(): Result<R> = callApi(delegate)

    override suspend fun execute(): Result<R> = callApi(delegate)

    override suspend fun await(): Result<R> = withContext(ioDispatcher){callApi(delegate)}

    private fun callApi(delegate: Call<R>):Result<R>{
        val apiResult = try {
           if (networkStateManager.isConnectingToInternet()){
               val result = delegate.execute()
               val responseBody = result.body()
               responseBody?.let { Result.Success(it) } ?: getErrorResponse(result)
           }
           else{
               Result.Failure(Error(ErrorType.NO_NETWORK))
           }
        }
        catch (ex:Exception){
            getExceptionResponse(ex)
        }
        return apiResult
    }

    private fun <T : Any> getErrorResponse(response: Response<T>) =
        response.errorBody()?.string()?.takeIf { it.isNotNullOrEmpty() }?.let { errorBody->
            try{
                val error = errorBody.toObject<BaseResponse>()
                if (error != null){
                    val message = error.message.getOrDefault<String>()+error.detailedErrorMessage.getOrDefault<String>()
                    Result.Failure(Error(ErrorType.API_ERROR, response.code(), message))
                }
                else{
                    Result.Failure(Error(ErrorType.API_UNKNOWN_ERROR, response.code()))
                }
            }catch (ex:Exception){
                getExceptionResponse(ex)
            }
        }?:Result.Failure(Error(ErrorType.UNKNOWN_ERROR, response.code(), response.message()))


    private fun <T:Any> getExceptionResponse(ex:Exception):Result<T>{
        val error =  when(ex){
            is TimeoutException,
            is ConnectException,
            is UnknownHostException,
            is SocketTimeoutException -> Error(ErrorType.NETWORK_ERROR, errorMessage = ex.message)
            else->{
                if(ex is CancellationException){
                    Error(ErrorType.TASK_CANCELLATION_ERROR, errorMessage = getStringTrackTrace(ex))
                }
                else{
                    Error(ErrorType.UNKNOWN_ERROR, errorMessage = getStringTrackTrace(ex))
                }
            }
        }
        return Result.Failure(error)
    }


    private fun getStringTrackTrace(e:Exception):String{
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        e.printStackTrace(pw)
        return sw.toString()
    }

}