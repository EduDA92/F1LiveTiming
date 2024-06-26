package com.example.f1livetiming.utils

import com.example.f1livetiming.ui.model.Driver
import com.example.f1livetiming.ui.model.DriverPosition
import com.example.f1livetiming.ui.model.Lap

object ExpectedResponses {

    val expectedFullDriverPositionResponse = listOf(
        DriverPosition(
            driverPosition = 1,
            driverNumber = 1
        ),
        DriverPosition(
            driverPosition = 2,
            driverNumber = 4
        ),
        DriverPosition(
            driverPosition = 3,
            driverNumber = 63
        ),
        DriverPosition(
            driverPosition = 4,
            driverNumber = 44
        ),
        DriverPosition(
            driverPosition = 5,
            driverNumber = 81
        ),
        DriverPosition(
            driverPosition = 6,
            driverNumber = 14
        ),
        DriverPosition(
            driverPosition = 7,
            driverNumber = 18
        ),
        DriverPosition(
            driverPosition = 8,
            driverNumber = 3
        ),
        DriverPosition(
            driverPosition = 9,
            driverNumber = 10
        ),
        DriverPosition(
            driverPosition = 10,
            driverNumber = 31
        ),
        DriverPosition(
            driverPosition = 11,
            driverNumber = 27
        ),
        DriverPosition(
            driverPosition = 12,
            driverNumber = 20
        ),
        DriverPosition(
            driverPosition = 13,
            driverNumber = 77
        ),
        DriverPosition(
            driverPosition = 14,
            driverNumber = 22
        ),
        DriverPosition(
            driverPosition = 15,
            driverNumber = 24
        ),
        DriverPosition(
            driverPosition = 16,
            driverNumber = 55
        ),
        DriverPosition(
            driverPosition = 17,
            driverNumber = 23
        ),
        DriverPosition(
            driverPosition = 18,
            driverNumber = 11
        ),
        DriverPosition(
            driverPosition = 19,
            driverNumber = 16
        ),
        DriverPosition(
            driverPosition = 20,
            driverNumber = 2
        )
    )

    val expectedDriverResponse = listOf(
        Driver(
            driverAcronym = "VER",
            driverNumber = 1,
            teamColor = "#3671C6"
        ),
        Driver(
            driverAcronym = "SAR",
            driverNumber = 2,
            teamColor = "#64C4FF"
        )
    )

    val expectedLapsResponse = listOf(
        Pair(
            first = Lap(
                driverNumber = 1,
                lapDuration = 79.774,
                lapNumber = 66,
                sector1Duration = 23.251,
                sector2Duration = 32.326,
                sector3Duration = 24.197,
                segmentsSector1 = listOf(
                    2049,
                    2049,
                    2048,
                    2048,
                    2048,
                    2048,
                    2048
                ),
                segmentsSector2 = listOf(
                    2048,
                    2048,
                    2048,
                    2048,
                    2048,
                    2048,
                    2048,
                    2048
                ),
                segmentsSector3 = listOf(
                    2048,
                    2048,
                    2048,
                    2048,
                    2064,
                    2064
                )

            ),
            second = 77.776
        ),
        Pair(
            first = Lap(
                driverNumber = 2,
                lapDuration = 85.203,
                lapNumber = 64,
                sector1Duration = 25.139,
                sector2Duration = 34.847,
                sector3Duration = 25.217,
                segmentsSector1 = listOf(
                    null,
                    2048,
                    2048,
                    2048,
                    2048,
                    2048,
                    2048
                ),
                segmentsSector2 = listOf(
                    2048,
                    2048,
                    2048,
                    2048,
                    2048,
                    2048,
                    2048,
                    2048
                ),
                segmentsSector3 = listOf(
                    2048,
                    2048,
                    2048,
                    2048,
                    2064,
                    2064
                )

            ),
            second = 80.172,
        ),
        Pair(
            first = Lap(
                driverNumber = 3,
                lapDuration = 79.527,
                lapNumber = 65,
                sector1Duration = 23.52,
                sector2Duration = 32.152,
                sector3Duration = 23.855,
                segmentsSector1 = listOf(
                    null,
                    2048,
                    2048,
                    2048,
                    2048,
                    2048,
                    2048
                ),
                segmentsSector2 = listOf(
                    2048,
                    2048,
                    2048,
                    2048,
                    2048,
                    2048,
                    2048,
                    2048
                ),
                segmentsSector3 = listOf(
                    2048,
                    2048,
                    2048,
                    2048,
                    2064,
                    2064
                )

            ),
            second = 79.450,
        )
    )

}