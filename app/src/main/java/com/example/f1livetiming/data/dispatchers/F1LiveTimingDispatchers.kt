package com.example.f1livetiming.data.dispatchers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val f1LiveTimingDispatchers: F1LiveTimingDispatchers) {
}

enum class F1LiveTimingDispatchers {
    IO
}