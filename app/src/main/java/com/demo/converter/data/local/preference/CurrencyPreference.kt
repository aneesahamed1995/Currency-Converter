package com.demo.converter.data.local.preference

import android.content.SharedPreferences

interface CurrencyPreference {
    fun getExchangeRateSyncedTime():Long
    fun setExchangeRateSyncedTime(timeMillis:Long)
}

class CurrencyPreferenceImpl(private val preference:SharedPreferences):CurrencyPreference{

    private companion object Key{
        const val EXCHANGE_RATE_SYNC_TIME = "exchange_rate_sync_time"
    }
    override fun getExchangeRateSyncedTime() = preference.getLong(EXCHANGE_RATE_SYNC_TIME,0L)

    override fun setExchangeRateSyncedTime(timeMillis: Long) = set { putLong(EXCHANGE_RATE_SYNC_TIME,timeMillis) }

    private fun set(func:SharedPreferences.Editor.()->Unit){
        with(preference.edit()){
            func()
            apply()
        }
    }


}