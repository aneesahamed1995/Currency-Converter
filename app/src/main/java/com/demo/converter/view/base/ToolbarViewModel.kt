package com.demo.converter.view.base

import androidx.lifecycle.MutableLiveData

class ToolbarViewModel:BaseViewModel() {

    private val _toolbarTitleLiveData by lazy { MutableLiveData<String?>() }
    val toolbarTitleLiveData get() = _toolbarTitleLiveData

    private val _toolbarTypeLiveData by lazy { MutableLiveData<Int>() }
    val toolbarTypeLiveData get() = _toolbarTypeLiveData



    fun setToolbarData(title:String?,toolbarViewType:Int){
        toolbarTitleLiveData.value = title
        toolbarTypeLiveData.value = toolbarViewType
    }

    fun setToolbarTitle(title:String){
        toolbarTitleLiveData.value = title
    }


    fun setToolbarType(toolbarViewType: Int){
        toolbarTypeLiveData.value = toolbarViewType
    }
}