package com.example.f1livetiming.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionDTO (
    @SerialName("session_name") val sessionName: String,
    @SerialName("country_code") val countryCode: String,
    @SerialName("country_name") val countryName: String,
    @SerialName("circuit_short_name") val circuitName: String
)