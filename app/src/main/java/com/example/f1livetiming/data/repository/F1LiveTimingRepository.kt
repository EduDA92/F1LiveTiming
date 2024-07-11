package com.example.f1livetiming.data.repository

import com.example.f1livetiming.ui.model.Driver
import com.example.f1livetiming.ui.model.DriverPosition
import com.example.f1livetiming.ui.model.Lap
import com.example.f1livetiming.ui.model.Session
import com.example.f1livetiming.ui.model.Stint
import kotlinx.coroutines.flow.Flow

interface F1LiveTimingRepository {

    fun getDriversPositions(
        onIdle: () -> Unit,
        onError: (String) -> Unit
    ): Flow<List<DriverPosition>>

    fun getDrivers(
        onIdle: () -> Unit,
        onError: (String) -> Unit
    ): Flow<List<Driver>>

    fun getLaps(
        onIdle: () -> Unit,
        onError: (String) -> Unit
    ): Flow<List<Triple<Lap, Lap, Double>>>

    fun getStints(
        onIdle: () -> Unit,
        onError: (String) -> Unit
    ): Flow<List<Stint>>

    fun getSession(
        onIdle: () -> Unit,
        onError: (String) -> Unit
    ): Flow<List<Session>>

}