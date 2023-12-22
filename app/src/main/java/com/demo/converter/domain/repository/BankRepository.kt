package com.demo.converter.domain.repository

import com.demo.converter.domain.entity.Bank
import com.demo.converter.domain.entity.Result

interface BankRepository {
    suspend fun validateIban(iban:String): Result<Bank>
}