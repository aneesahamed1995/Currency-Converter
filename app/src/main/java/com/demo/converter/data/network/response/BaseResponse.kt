package com.demo.converter.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
open class BaseResponse(
    @Json(name = "description") val message:String? = null,
    @Json(name = "error") val error:Boolean? = false
)