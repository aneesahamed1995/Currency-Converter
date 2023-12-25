package com.demo.converter.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import com.demo.converter.common.AppConstant
import com.demo.converter.data.local.db.DbConstant


@Entity(tableName = DbConstant.TableName.EXCHANGE_RATE, primaryKeys = [DbConstant.ExchangeRateTableColumn.BASE_CURRENCY_CODE, DbConstant.ExchangeRateTableColumn.CURRENCY_CODE])
data class CurrencyExchangeRateLocalEntity(
    @ColumnInfo(DbConstant.ExchangeRateTableColumn.BASE_CURRENCY_CODE) var baseCurrencyCode: String = AppConstant.STRING_EMPTY,
    @ColumnInfo(DbConstant.ExchangeRateTableColumn.CURRENCY_CODE) var currencyCode: String = AppConstant.STRING_EMPTY,
    @ColumnInfo(DbConstant.ExchangeRateTableColumn.EXCHANGE_RATE) var exchangeRate: Double = 0.0
){
    @Ignore var name:String = AppConstant.STRING_EMPTY
}

data class CurrencyExchangeRateWithNameEntity(
    @ColumnInfo(DbConstant.ExchangeRateTableColumn.BASE_CURRENCY_CODE) var baseCurrencyCode: String = AppConstant.STRING_EMPTY,
    @ColumnInfo(DbConstant.ExchangeRateTableColumn.CURRENCY_CODE) var currencyCode: String = AppConstant.STRING_EMPTY,
    @ColumnInfo(DbConstant.ExchangeRateTableColumn.EXCHANGE_RATE) var exchangeRate: Double = 0.0,
    @ColumnInfo(DbConstant.CurrencyTableColumn.NAME) var name:String = AppConstant.STRING_EMPTY
)
