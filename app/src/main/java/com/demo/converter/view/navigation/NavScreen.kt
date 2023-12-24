package com.demo.converter.view.navigation

import androidx.annotation.StringRes
import com.demo.converter.R

enum class NavScreen(
    val route: String,
    private val routeFormat: String?,
    @StringRes val title: Int
) {

    Start(NavRoutePath.HOME, null, R.string.app_name),
    CurrencyList(NavRoutePath.CURRENCY_LIST, NavRouteArgFormat.CURRENCY_LIST,  R.string.currencies);

    fun getRouteWithArg(args:Array<String>) = routeFormat?.format(*args)?:throw IllegalArgumentException("Incorrect routeFormat $routeFormat")
}

private object NavRoute{
    const val HOME = "home"
    const val  CURRENCY_LIST = "currencies"
}

object NavRoutePath{
    const val HOME = NavRoute.HOME
    const val CURRENCY_LIST = "${NavRoute.CURRENCY_LIST}?${NavArgName.BASE_CURRENCY}={${NavArgName.BASE_CURRENCY}}"
}

object NavRouteArgFormat{
    const val CURRENCY_LIST = "${NavRoute.CURRENCY_LIST}?${NavArgName.BASE_CURRENCY}=%s"
}

object NavArgName {
    const val BASE_CURRENCY = "base_currency"
}