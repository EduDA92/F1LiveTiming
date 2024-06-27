package com.example.f1livetiming.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StintDTO(
    @SerialName("compound") val compound: String,
    @SerialName("driver_number") val driverNumber: Int,
    @SerialName("lap_end") val lapEnd: Int,
    @SerialName("lap_start") val lapStart: Int,
    @SerialName("stint_number") val stintNumber: Int,
    @SerialName("tyre_age_at_start") val tyreAgeAtStart: Int
)
