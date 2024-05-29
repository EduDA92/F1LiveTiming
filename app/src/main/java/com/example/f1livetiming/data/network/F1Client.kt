package com.example.f1livetiming.data.network

import javax.inject.Inject

class F1Client @Inject constructor(
    private val f1Service: F1Service
) {

    suspend fun getDriversPosition(
        sessionKey: String
    ) = f1Service.getDriversPositions(sessionKey)


}