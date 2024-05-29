package com.example.f1livetiming.data.network

import com.example.f1livetiming.data.network.model.DriverPositionDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface F1Service {

    /* On live events session key = latest*/
    @GET("position")
    suspend fun getDriversPositions(
        @Query("session_key") sessionKey: String
    ): Response<List<DriverPositionDTO>>

}