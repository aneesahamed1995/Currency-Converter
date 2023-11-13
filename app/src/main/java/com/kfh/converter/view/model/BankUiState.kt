package com.kfh.converter.view.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BankUiState(
    val iban:String,
    val country:String,
    val checksum:String,
    val bankCode:String,
    val accountNumber:String,
    val name:String,
    val zip:String,
    val city:String,
    val bic:String
):Parcelable
