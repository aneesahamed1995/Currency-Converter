package com.demo.converter.domain.entity


data class Bank(
    val iban:String,
    val country:String,
    val checksum:String,
    val bankCode:String,
    val accountNumber:String,
    val name:String,
    val zip:String,
    val city:String,
    val bic:String
)
