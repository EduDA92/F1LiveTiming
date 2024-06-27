package com.example.f1livetiming.data.mapper

import com.example.f1livetiming.data.network.model.StintDTO
import com.example.f1livetiming.ui.model.Stint

fun StintDTO.asDomain(): Stint {

    return Stint(
        compound = compound,
        driverNumber = driverNumber,
        lapEnd = lapEnd,
        lapStart = lapStart,
        stintNumber = stintNumber,
        tyreAgeAtStart = tyreAgeAtStart
    )

}

fun List<StintDTO>.asDomain() = map { it.asDomain() }