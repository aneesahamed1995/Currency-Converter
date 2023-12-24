package com.demo.converter.utils

import com.demo.converter.data.network.NetworkStateManager

class FakeNetworkStateManager: NetworkStateManager {

    override fun isConnectingToInternet(): Boolean = true
}