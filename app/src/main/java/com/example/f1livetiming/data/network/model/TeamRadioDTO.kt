package com.example.f1livetiming.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamRadioDTO(
    @SerialName("driver_number") val driverNumber: Int,
    @SerialName("recording_url") val recordingUrl: String
)
