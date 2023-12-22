package com.demo.converter.data.network

class ErrorType{
    companion object{
        const val NETWORK_ERROR = 1
        const val API_ERROR = 2
        const val UNKNOWN_ERROR = 3
        const val API_UNKNOWN_ERROR = 4
        const val NO_NETWORK = 5
        const val TASK_CANCELLATION_ERROR = 6
        const val NO_LOCATION_ERROR = 7
        const val NO_GPS_ERROR=  8
        const val NO_LOCATION_PERMISSION_ERROR = 9
        const val IN_APP_ERROR = 10
        const val NO_DATA = 11
    }
}