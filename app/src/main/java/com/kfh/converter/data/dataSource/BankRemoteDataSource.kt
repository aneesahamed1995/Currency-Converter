package com.kfh.converter.data.dataSource

import com.kfh.converter.data.mapper.BankMapper
import com.kfh.converter.data.repository.BankApi
import com.kfh.converter.domain.entity.Bank
import com.kfh.converter.domain.entity.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface BankRemoteDataSource {
    suspend fun validateIban(iban:String):Result<Bank>
}

class BankRemoteDataSourceImpl(private val apiService: BankApi, private val bankMapper: BankMapper, private val ioDispatcher: CoroutineDispatcher):BankRemoteDataSource{

    override suspend fun validateIban(iban: String) = withContext(ioDispatcher){
        when(val result = apiService.validateIban(iban).execute()){
            is Result.Success-> Result.Success(bankMapper.mapFrom(result.data))
            is Result.Failure->result
        }
    }

}