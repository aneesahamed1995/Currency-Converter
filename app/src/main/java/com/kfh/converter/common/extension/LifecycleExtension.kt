package com.kfh.converter.common.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.kfh.converter.view.model.SingleEvent

fun <Type : Any, L : LiveData<Type>> LifecycleOwner.observe(liveData: L, observer: (Type?) -> Unit) =
    liveData.observe(this, Observer(observer))

fun <Type : Any, L : LiveData<SingleEvent<Type>>> LifecycleOwner.observeEvent(eventLiveData : L, observer: (Type) -> Unit = {}) =
    eventLiveData.observe(this, SingleEventObserver(observer))


private class SingleEventObserver<T>(private val eventObserver:(T)->Unit): Observer<SingleEvent<T>> {
    override fun onChanged(event: SingleEvent<T>) {
        if (!event.isCleared){
            event.isObserved()?.let { eventData->
                eventObserver(eventData)
            }
        }
    }
}