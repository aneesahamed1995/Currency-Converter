package com.kfh.converter.data.mapper

import com.kfh.converter.domain.entity.ExchangeRate

class ExchangeRateMapper {

    fun mapFrom(map: Map<String,Double>?) = map?.map { (code,rate)-> ExchangeRate(code, rate) }?: emptyList()
}