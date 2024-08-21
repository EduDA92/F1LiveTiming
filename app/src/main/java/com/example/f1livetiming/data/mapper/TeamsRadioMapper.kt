package com.example.f1livetiming.data.mapper

import com.example.f1livetiming.data.network.model.TeamRadioDTO
import com.example.f1livetiming.ui.model.TeamRadio

fun TeamRadioDTO.asUIModel(): TeamRadio {
    return TeamRadio(
        driverNumber = driverNumber,
        recordingUrl = recordingUrl,
        date = date
    )
}

fun List<TeamRadioDTO>.asUIModel() = map { it.asUIModel() }