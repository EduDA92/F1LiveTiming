package com.example.f1livetiming.data.network

import com.example.f1livetiming.data.network.model.DriverDTO
import com.example.f1livetiming.data.network.model.DriverPositionDTO
import com.example.f1livetiming.data.network.model.IntervalDTO
import com.example.f1livetiming.data.network.model.LapDTO
import com.example.f1livetiming.data.network.model.SessionDTO
import com.example.f1livetiming.data.network.model.StintDTO
import com.example.f1livetiming.data.network.model.TeamRadioDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface F1Service {

    /* On live events session key = latest*/
    @GET("position")
    suspend fun getDriversPositions(
        @Query("session_key") sessionKey: String
    ): Response<List<DriverPositionDTO>>

    @GET("drivers")
    suspend fun getDrivers(
        @Query("session_key") sessionKey: String
    ): Response<List<DriverDTO>>

    @GET("laps")
    suspend fun getLaps(
        @Query("session_key") sessionKey: String
    ): Response<List<LapDTO>>

    @GET("stints")
    suspend fun getStints(
        @Query("session_key") sessionKey: String
    ): Response<List<StintDTO>>

    @GET("sessions")
    suspend fun getSession(
        @Query("session_key") sessionKey: String
    ): Response<List<SessionDTO>>

    @GET("intervals")
    suspend fun getIntervals(
        @Query("session_key") sessionKey: String
    ): Response<List<IntervalDTO>?>

    @GET("team_radio")
    suspend fun getTeamsRadio(
        @Query("session_key") sessionKey: String
    ): Response<List<TeamRadioDTO>>

}