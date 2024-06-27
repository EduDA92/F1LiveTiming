package com.example.f1livetiming.data.mapper

import com.example.f1livetiming.data.network.model.DriverPositionDTO
import com.example.f1livetiming.ui.model.DriverPosition

fun DriverPositionDTO.asDomain(): DriverPosition {

    return DriverPosition(
        driverNumber = driverNumber,
        driverPosition = position
    )

}

fun List<DriverPositionDTO>.asDomain() = map {it.asDomain()}