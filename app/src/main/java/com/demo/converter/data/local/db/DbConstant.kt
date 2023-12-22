package com.demo.converter.data.local.db

object DbConstant {
    const val DB_VERSION = 1

    object TableName{
        const val CURRENCY = "currency"
        const val EXCHANGE_RATE = "exchange_rate"
    }

    object CurrencyTableColumn{
        const val NAME = "name"
        const val CODE = "code"
        const val LAST_SYNC_TIME = "last_sync_time"
    }

    object ExchangeRateTableColumn{
        const val CURRENCY_CODE = "currency_code"
        const val EXCHANGE_RATE = "exchange_rate"
        const val BASE_CURRENCY_CODE = "base_currency_code"
    }
}