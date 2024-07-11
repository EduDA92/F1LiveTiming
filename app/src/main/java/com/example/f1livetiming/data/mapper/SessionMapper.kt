package com.example.f1livetiming.data.mapper

import com.example.f1livetiming.data.network.model.SessionDTO
import com.example.f1livetiming.ui.model.Session

fun SessionDTO.asDomain(): Session {
    return Session(
        sessionName = sessionName,
        countryCode = countryCode,
        countryName = countryName,
        circuitName = circuitName
    )
}

fun List<SessionDTO>.asDomain() = map { it.asDomain() }