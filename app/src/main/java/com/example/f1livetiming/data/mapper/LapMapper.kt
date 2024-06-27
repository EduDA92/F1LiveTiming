package com.example.f1livetiming.data.mapper

import com.example.f1livetiming.data.network.model.LapDTO
import com.example.f1livetiming.ui.model.Lap


fun LapDTO.asDomain(): Lap {

    return Lap(
        driverNumber = driverNumber,
        lapDuration = lapDuration,
        lapNumber = lapNumber,
        sector1Duration = sector1Duration,
        sector2Duration = sector2Duration,
        sector3Duration = sector3Duration,
        segmentsSector1 = segmentsSector1,
        segmentsSector2 = segmentsSector2,
        segmentsSector3 = segmentsSector3
    )

}

fun List<LapDTO>.asDomain() = map { it.asDomain() }