package com.kfh.converter.data.mapper

import com.kfh.converter.domain.entity.Currency

class CurrencyMapper {

    fun mapFrom(map: Map<String,String>?) = map?.map { (code,name)-> Currency(code, name) }?: emptyList()
}