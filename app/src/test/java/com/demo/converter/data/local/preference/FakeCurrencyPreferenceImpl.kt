package com.demo.converter.data.local.preference

class FakeCurrencyPreferenceImpl:CurrencyPreference {

    override fun getExchangeRateSyncedTime() = 0L

    override fun setExchangeRateSyncedTime(timeMillis: Long){

    }
}