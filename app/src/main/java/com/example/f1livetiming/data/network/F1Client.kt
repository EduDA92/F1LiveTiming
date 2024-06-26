package com.example.f1livetiming.data.network

import javax.inject.Inject

class F1Client @Inject constructor(
    private val f1Service: F1Service
) {

    suspend fun getDriversPosition(
        sessionKey: String
    ) = f1Service.getDriversPositions(sessionKey)

    suspend fun getDrivers(
        sessionKey: String
    ) = f1Service.getDrivers(sessionKey)

    suspend fun getLaps(
        sessionKey: String
    ) = f1Service.getLaps(sessionKey)

}