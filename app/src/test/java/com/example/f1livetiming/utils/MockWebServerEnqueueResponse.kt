package com.example.f1livetiming.utils

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import java.nio.charset.StandardCharsets

 fun MockWebServer.enqueueResponse(fileName: String) {
    val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/$fileName")
    val source = inputStream.source().buffer()
    val mockResponse = MockResponse()
    this.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)))
}