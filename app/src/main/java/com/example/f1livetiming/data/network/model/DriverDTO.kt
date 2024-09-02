package com.example.f1livetiming.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DriverDTO(
    @SerialName("name_acronym") val nameAcronym: String,
    @SerialName("driver_number") val driverNumber: Int,
    @SerialName("team_colour") val teamColor: String?
)
