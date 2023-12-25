package com.demo.converter.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.demo.converter.data.local.db.dao.CurrencyDao
import com.demo.converter.data.local.db.entity.CurrencyExchangeRateLocalEntity
import com.demo.converter.data.local.db.entity.CurrencyLocalEntity

class RoomDbHelper(context: Context) {
    val db = Room.databaseBuilder(context, AppDatabase::class.java, "currency_database").build()
}

@Database(version = DbConstant.DB_VERSION, entities = [CurrencyLocalEntity::class, CurrencyExchangeRateLocalEntity::class], exportSchema = false)
abstract class AppDatabase: RoomDatabase(){
    abstract fun currencyDao(): CurrencyDao
}
