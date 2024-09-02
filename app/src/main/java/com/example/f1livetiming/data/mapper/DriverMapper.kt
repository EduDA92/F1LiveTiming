package com.example.f1livetiming.data.mapper

import com.example.f1livetiming.data.network.model.DriverDTO
import com.example.f1livetiming.ui.model.Driver

private const val argbStartString = "#"

fun DriverDTO.asUIModel(): Driver {

    val stringBuilder = StringBuilder()

    return Driver(
        driverAcronym = nameAcronym,
        driverNumber = driverNumber,
        teamColor = stringBuilder.append(argbStartString).append(teamColor ?: "000000").toString()

    )

}

fun List<DriverDTO>.asUIModel() = map {it.asUIModel()}