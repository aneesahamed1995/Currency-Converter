package com.demo.converter.common.extension

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.demo.converter.common.AppConstant
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Date
import java.util.Locale

val String.EMPTY get() = ""

fun String?.isNotNullOrEmpty() = !this.isNullOrEmpty()


inline fun <reified T>Any?.getOrDefault(): T{
    return when(T::class){
        String::class-> this as? T?: AppConstant.EMPTY as T
        Int::class-> this as? T?:0 as T
        Double::class-> this as? T?:0.0 as T
        Long::class-> this as? T?:0L as T
        Float::class -> this as? T?: 0f as T
        Boolean::class -> this as? T?: false as T
        Date::class-> this as? T?: Date() as T
        else-> throw IllegalArgumentException("Unknown Type:: Mention valid type in when statement")
    }
}


fun String?.toSafeDouble() = try {
    if (this.isNotNullOrEmpty()){
        this!!.toDouble()
    }
    else 0.0
}catch (ex:Exception){ 0.0 }

fun Double.toFormattedDouble(): Double = DecimalFormat("#.##", DecimalFormatSymbols(Locale.ENGLISH)).format(this).toDouble()

fun hideKeyboard(activity: Activity) {
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = activity.currentFocus
    if (imm != null && view != null) {
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}