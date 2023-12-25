package com.demo.converter.common.extension

import com.demo.converter.common.AppConstant
import java.util.Date


fun String?.isNotNullOrEmpty() = !this.isNullOrEmpty()

inline fun <reified T>Any?.getOrDefault(): T{
    return when(T::class){
        String::class-> this as? T?: AppConstant.STRING_EMPTY as T
        Int::class-> this as? T?:0 as T
        Double::class-> this as? T?:0.0 as T
        Long::class-> this as? T?:0L as T
        Float::class -> this as? T?: 0f as T
        Boolean::class -> this as? T?: false as T
        Date::class-> this as? T?: Date() as T
        else-> throw IllegalArgumentException("Unknown Type:: Mention valid type in when statement")
    }
}

fun Double.toFormattedDouble() = "%.4f".format(this).toBigDecimal().stripTrailingZeros().toDouble()
