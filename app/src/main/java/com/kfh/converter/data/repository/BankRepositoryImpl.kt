package com.kfh.converter.data.repository

import com.kfh.converter.data.dataSource.BankRemoteDataSource
import com.kfh.converter.domain.repository.BankRepository

class BankRepositoryImpl(private val bankRemoteDataSource: BankRemoteDataSource):BankRepository {

    override suspend fun validateIban(iban: String) = bankRemoteDataSource.validateIban(iban)
}