package com.demo.converter.data.repository

import com.demo.converter.data.dataSource.BankRemoteDataSource
import com.demo.converter.domain.repository.BankRepository

class BankRepositoryImpl(private val bankRemoteDataSource: BankRemoteDataSource):BankRepository {

    override suspend fun validateIban(iban: String) = bankRemoteDataSource.validateIban(iban)
}