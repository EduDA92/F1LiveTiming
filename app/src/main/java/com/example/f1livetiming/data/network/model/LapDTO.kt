package com.example.f1livetiming.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LapDTO (
    @SerialName("driver_number") val driverNumber: Int,
    @SerialName("lap_duration") val lapDuration: Double?,
    @SerialName("lap_number") val lapNumber: Int,
    @SerialName("duration_sector_1") val sector1Duration: Double?,
    @SerialName("duration_sector_2") val sector2Duration: Double?,
    @SerialName("duration_sector_3") val sector3Duration: Double?,
    @SerialName("segments_sector_1") val segmentsSector1: List<Int?>,
    @SerialName("segments_sector_2") val segmentsSector2: List<Int?>,
    @SerialName("segments_sector_3") val segmentsSector3: List<Int?>
)