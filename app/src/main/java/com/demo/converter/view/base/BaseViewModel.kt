package com.demo.converter.view.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.converter.data.network.NetworkConstant
import com.demo.converter.domain.entity.Error
import com.demo.converter.view.model.SingleEvent
import com.demo.converter.view.model.UIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.demo.converter.domain.entity.Result

abstract class BaseViewModel: ViewModel() {

    protected val _uiState: MutableLiveData<SingleEvent<UIState<Unit>>> by lazy { MutableLiveData() }
    val uiState: LiveData<SingleEvent<UIState<Unit>>> get() = _uiState
    private var job: Job?=null

    protected fun <T:Any> getResult(scope: CoroutineScope, work: suspend (() -> Result<T>), callback: ((T) -> Unit)? = null, showProgress:Boolean = false,  apiId:Int= NetworkConstant.ApiRequestID.NONE): Job =
            scope.launch {
                    if(showProgress){
                        showAPIProgress(true,apiId)
                    }
                    val result = work()
                    if(showProgress){
                        showAPIProgress(false,apiId)
                    }
                    when(result){
                        is Result.Success->callback?.let { it(result.data) }
                        is Result.Failure-> onFailure(result.error.also { it.apiId = apiId})
                    }
            }.also{
                this.job = it
            }

    protected fun <T : Any> execute(scope: CoroutineScope, work: suspend (() -> Result<T>), successCallback: ((T) ->Unit)? = null, failureCallback: ((Error) ->Unit)? = null) =
        scope.launch {
            when(val result = work()){
                is Result.Success-> successCallback?.invoke(result.data)
                is Result.Failure-> failureCallback?.invoke(result.error)
            }
        }


    open fun showAPIProgress(show: Boolean,apiId: Int){}

    open fun onFailure(apiError: Error){}

}