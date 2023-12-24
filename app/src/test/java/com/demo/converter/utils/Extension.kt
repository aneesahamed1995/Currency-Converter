package com.demo.converter.utils

import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import okio.Buffer
import java.io.InputStreamReader

fun MockResponse.setResponsePath(path:String): MockResponse {
    val reader = InputStreamReader(this.javaClass.classLoader.getResourceAsStream("response/$path"))
    val content = reader.readText()
    reader.close()
    return this.setBody(content)
}

fun RecordedRequest.hasBody() = this.bodySize > 0L

fun Buffer.isEmpty() = this.size == 0L

val HttpUrl.path get() = this.encodedPath.removePrefix("/")