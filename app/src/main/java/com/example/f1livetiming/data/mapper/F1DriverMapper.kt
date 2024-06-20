package com.example.f1livetiming.data.mapper

import com.example.f1livetiming.data.network.model.DriverDTO
import com.example.f1livetiming.ui.model.Driver

object F1DriverMapper {

    private const val argbStartString = "#"

    fun DriverDTO.asDomain(): Driver {

        val stringBuilder = StringBuilder()

        return Driver(
            driverAcronym = nameAcronym,
            driverNumber = driverNumber,
            teamColor = stringBuilder.append(argbStartString).append(teamColor).toString()

        )

    }

    fun List<DriverDTO>.asDomain() = map {it.asDomain()}

}