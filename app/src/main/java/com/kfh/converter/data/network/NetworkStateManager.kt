package com.kfh.converter.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

interface NetworkStateManager{
    fun isConnectingToInternet():Boolean
}

class NetworkStateManagerImpl(context: Context):NetworkStateManager {

    private val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun isConnectingToInternet() = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork).isNetworkCapabilitiesValid()

    private fun NetworkCapabilities?.isNetworkCapabilitiesValid(): Boolean = when {
        this == null -> false
        hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) && hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)->true
        else -> false
    }
}