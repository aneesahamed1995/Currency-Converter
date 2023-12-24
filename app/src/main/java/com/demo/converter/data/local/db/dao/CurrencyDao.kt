package com.demo.converter.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demo.converter.data.local.db.entity.CurrencyLocalEntity
import com.demo.converter.data.local.db.entity.CurrencyExchangeRateLocalEntity
import com.demo.converter.data.local.db.entity.CurrencyExchangeRateWithNameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao{

    @Query("SELECT * from currency")
    fun getCurrencies():List<CurrencyLocalEntity>

    @Query("SELECT currency.name , currency.code AS currency_code, exchange_rate.exchange_rate, exchange_rate.base_currency_code  FROM exchange_rate exchange_rate  JOIN Currency currency ON  currency.code = exchange_rate.currency_code WHERE exchange_rate.base_currency_code = :baseCurrencyCode")
    suspend fun getExchangeRates(baseCurrencyCode:String):List<CurrencyExchangeRateWithNameEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCurrencies(currencies:List<CurrencyLocalEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExchangeRates(exchangeRates:List<CurrencyExchangeRateLocalEntity>)
}
