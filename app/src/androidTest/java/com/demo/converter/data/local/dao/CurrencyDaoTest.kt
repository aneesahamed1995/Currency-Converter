package com.demo.converter.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.demo.converter.data.local.db.AppDatabase
import com.demo.converter.data.local.db.dao.CurrencyDao
import com.demo.converter.data.local.db.entity.CurrencyExchangeRateLocalEntity
import com.demo.converter.data.local.db.entity.CurrencyLocalEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class CurrencyDaoTest {

    private lateinit var db:AppDatabase
    private lateinit var currencyDao:CurrencyDao

    @Before
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDatabase::class.java).allowMainThreadQueries().build()
        currencyDao=  db.currencyDao()
    }

    @After
    fun closeDatabase() {
        db.close()
    }

    @Test
    fun insert_currencyList_and_get_currency_List(){
        runBlocking {
            val currencies = mutableListOf<CurrencyLocalEntity>().also { list->
                repeat(10){
                    list.add(CurrencyLocalEntity(
                        "United State Dollar","USD$it"
                    ))
                }
                list.add(CurrencyLocalEntity(
                    "United State Dollar","USD1"
                ))
            }
            currencyDao.insertCurrencies(currencies)
            val job = async {
                delay(5000)
                val result = currencyDao.getCurrencies()
                assertTrue(result.isNotEmpty())
                assertTrue(result.size == 10)
            }
            job.cancelAndJoin()
        }
    }

    @Test
    fun insert_exchange_rate_and_get_exchange_rate(){
        runTest {
            val currencies = mutableListOf<CurrencyLocalEntity>().also { list->
                repeat(10){
                    list.add(CurrencyLocalEntity(
                        "United State Dollar","USD$it"
                    ))
                }
            }
            currencyDao.insertCurrencies(currencies)

            val exchangeRates = mutableListOf<CurrencyExchangeRateLocalEntity>().also { list->
                repeat(10){
                    list.add(CurrencyExchangeRateLocalEntity(
                        "USD","USD$it",it.toDouble()
                    ))
                }
            }
            currencyDao.insertExchangeRates(exchangeRates)
            async {
                delay(5000)
                val result = currencyDao.getExchangeRates("USD")
                assertTrue(result.isNotEmpty())
                assertTrue(result[0].name.isNotEmpty())
                assertTrue(result.size == 10)
            }.cancelAndJoin()
        }
    }

}