package com.example.f1livetiming.data.repository

import com.example.f1livetiming.ui.model.DriverPosition
import kotlinx.coroutines.flow.Flow

interface F1LiveTimingRepository {

    fun getDriversPositions(
        sessionKey: String,
        onError: (String) -> Unit
    ): Flow<List<DriverPosition>>

}