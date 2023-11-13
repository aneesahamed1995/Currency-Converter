package com.kfh.converter.common.extension

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kfh.converter.common.AppConstant
import java.lang.reflect.Type
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Date
import java.util.Locale

val String.EMPTY get() = ""

fun String?.isNotNullOrEmpty() = !this.isNullOrEmpty()

fun <T> Collection<T>?.isNotNullOrEmpty(): Boolean = this!=null && this.isNotEmpty()

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

inline fun <reified T> String?.toList():List<T>{
    if(!this.isNullOrEmpty()){
        val listType: Type = TypeToken.getParameterized(List::class.java,T::class.java).type
        return Gson().fromJson(this,listType)
    }
    return listOf()
}

inline fun <reified T> String?.toObject():T?{
    if (this.isNotNullOrEmpty()){
        return Gson().fromJson(this,T::class.java)
    }
    return null
}

fun Any?.toJson():String{
    if (this!=null){
        return Gson().toJson(this)
    }
    return AppConstant.EMPTY
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