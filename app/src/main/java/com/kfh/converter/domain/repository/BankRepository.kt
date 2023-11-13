package com.kfh.converter.domain.repository

import com.kfh.converter.domain.entity.Bank
import com.kfh.converter.domain.entity.Result

interface BankRepository {
    suspend fun validateIban(iban:String): Result<Bank>
}