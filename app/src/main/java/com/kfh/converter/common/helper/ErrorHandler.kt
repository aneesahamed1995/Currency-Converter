package com.kfh.converter.common.helper

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.kfh.converter.R
import com.kfh.converter.data.network.ErrorType
import com.kfh.converter.domain.entity.Error

object ErrorHandler {

    private val handler by lazy {Handler(Looper.getMainLooper())  }

    fun handleError(context: Context?=null, error:Error){
        context?.let {
            when(error.errorType){
                ErrorType.NO_NETWORK -> showToast(it,it.getString(R.string.noConnectionTitle))
                ErrorType.NETWORK_ERROR-> showToast(it,it.getString(R.string.alert_message_not_able_to_connect_internet))
                ErrorType.API_ERROR->showToast(context,error.errorMessage)
                ErrorType.UNKNOWN_ERROR, ErrorType.API_UNKNOWN_ERROR->showToast(it,it.getString(R.string.something_went_wrong))
            }
        }
    }

    private fun showToast(context: Context?,message:String?){
        if(!message.isNullOrEmpty()){
            context?.let {
                if(Looper.myLooper()!= Looper.getMainLooper()){
                    handler.post{
                        Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}