package com.demo.converter.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.demo.converter.common.AppConstant
import com.demo.converter.data.local.db.DbConstant

@Entity(tableName = DbConstant.TableName.CURRENCY)
data class CurrencyLocalEntity(
    @PrimaryKey @ColumnInfo(DbConstant.CurrencyTableColumn.NAME) var name: String = AppConstant.EMPTY,
    @ColumnInfo(DbConstant.CurrencyTableColumn.CODE) var code: String = AppConstant.EMPTY,
    @ColumnInfo(DbConstant.CurrencyTableColumn.LAST_SYNC_TIME) var lastSyncTime:Long = 0L
)

