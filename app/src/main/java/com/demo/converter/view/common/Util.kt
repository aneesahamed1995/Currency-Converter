package com.demo.converter.view.common

import android.content.Context
import com.demo.converter.R
import com.demo.converter.common.extension.showToast
import com.demo.converter.data.network.ErrorType
import com.demo.converter.domain.entity.Error

fun showErrorMessage(context: Context, error: Error?){
    if (error != null){
        when(error.errorType){
            ErrorType.NO_NETWORK -> context.showToast(R.string.noConnectionTitle)
            ErrorType.NETWORK_ERROR-> context.showToast(R.string.alert_message_not_able_to_connect_internet)
            ErrorType.API_ERROR->context.showToast(error.errorMessage)
            ErrorType.UNKNOWN_ERROR, ErrorType.API_UNKNOWN_ERROR->context.showToast(R.string.something_went_wrong)
        }
    }
}