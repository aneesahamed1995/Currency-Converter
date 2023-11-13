package com.kfh.converter.view.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrencyExchangeUiState(
    val name:String,
    val code:String,
    val exchangeRate:Double,
    var isSelected:Boolean = false
):Parcelable