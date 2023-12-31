package com.demo.converter.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.demo.converter.common.AppConstant
import com.demo.converter.data.local.db.DbConstant

@Entity(tableName = DbConstant.TableName.CURRENCY)
data class CurrencyLocalEntity(
    @PrimaryKey @ColumnInfo(DbConstant.CurrencyTableColumn.NAME) var name: String = AppConstant.STRING_EMPTY,
    @ColumnInfo(DbConstant.CurrencyTableColumn.CODE) var code: String = AppConstant.STRING_EMPTY
)

