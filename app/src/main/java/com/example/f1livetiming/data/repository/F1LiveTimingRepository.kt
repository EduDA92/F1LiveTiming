package com.example.f1livetiming.data.repository

import com.example.f1livetiming.ui.model.Driver
import com.example.f1livetiming.ui.model.DriverPosition
import com.example.f1livetiming.ui.model.Lap
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
    ): Flow<List<Pair<Lap, Double>>>
}