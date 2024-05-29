package com.example.f1livetiming.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DriverPositionDTO(
    @SerialName(value = "driver_number") val driverNumber: Int,
    @SerialName(value = "position") val position: Int,
    @SerialName(value = "date") val date: String
)
