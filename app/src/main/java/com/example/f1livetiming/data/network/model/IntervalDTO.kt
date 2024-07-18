package com.example.f1livetiming.data.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** gapToLeader and interval can be Double or String depending if the driver is lapped or not */

@Serializable
data class IntervalDTO (
    @SerialName("driver_number") val driverNumber: Int,
    @SerialName("gap_to_leader") val gapToLeader: JsonElement?,
    @SerialName("interval")  val interval: JsonElement?
)

