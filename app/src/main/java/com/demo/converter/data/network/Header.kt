package com.demo.converter.data.network

class Header {
     object Key{
         const val AUTHORIZATION = "Authorization"
    }
    object Value{
        // WARNING:: IN real app. Api key shouldn't keep in client side. It need to fetch it from API side and Use it in Subsequent Api calls
        const val API_KEY = "Token c2458bd2696a4359a843c2bc797d8f25"
    }
}