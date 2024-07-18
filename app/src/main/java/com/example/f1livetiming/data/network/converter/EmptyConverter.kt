package com.example.f1livetiming.data.network.converter


import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class EmptyConverter: Converter.Factory() {


    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> = object : Converter<ResponseBody, Any> {

        val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(this@EmptyConverter, type, annotations)


        override fun convert(p0: ResponseBody): Any? {
            return if(p0.contentLength() == 0L) {
                null
            } else {
                nextResponseBodyConverter.convert(p0)
            }
        }


    }
}