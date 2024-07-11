package com.example.f1livetiming.utils

import com.example.f1livetiming.ui.liveTimingScreen.DriverData
import com.example.f1livetiming.ui.liveTimingScreen.LiveTimingData
import com.example.f1livetiming.ui.model.Driver
import com.example.f1livetiming.ui.model.DriverPosition
import com.example.f1livetiming.ui.model.Lap
import com.example.f1livetiming.ui.model.Session
import com.example.f1livetiming.ui.model.Stint
import kotlinx.collections.immutable.persistentListOf

val expectedFullDriverPositionResponse = listOf(
    DriverPosition(
        driverPosition = 1,
        driverNumber = 1,
        driverStartingPosition = 2
    ),
    DriverPosition(
        driverPosition = 2,
        driverNumber = 4,
        driverStartingPosition = 3
    ),
    DriverPosition(
        driverPosition = 3,
        driverNumber = 63,
        driverStartingPosition = 1
    ),
    DriverPosition(
        driverPosition = 4,
        driverNumber = 44,
        driverStartingPosition = 7
    ),
    DriverPosition(
        driverPosition = 5,
        driverNumber = 81,
        driverStartingPosition = 4
    ),
    DriverPosition(
        driverPosition = 6,
        driverNumber = 14,
        driverStartingPosition = 6
    ),
    DriverPosition(
        driverPosition = 7,
        driverNumber = 18,
        driverStartingPosition = 9
    ),
    DriverPosition(
        driverPosition = 8,
        driverNumber = 3,
        driverStartingPosition = 5
    ),
    DriverPosition(
        driverPosition = 9,
        driverNumber = 10,
        driverStartingPosition = 15
    ),
    DriverPosition(
        driverPosition = 10,
        driverNumber = 31,
        driverStartingPosition = 20
    ),
    DriverPosition(
        driverPosition = 11,
        driverNumber = 27,
        driverStartingPosition = 18
    ),
    DriverPosition(
        driverPosition = 12,
        driverNumber = 20,
        driverStartingPosition = 14
    ),
    DriverPosition(
        driverPosition = 13,
        driverNumber = 77,
        driverStartingPosition = 17
    ),
    DriverPosition(
        driverPosition = 14,
        driverNumber = 22,
        driverStartingPosition = 8
    ),
    DriverPosition(
        driverPosition = 15,
        driverNumber = 24,
        driverStartingPosition = 19
    ),
    DriverPosition(
        driverPosition = 16,
        driverNumber = 55,
        driverStartingPosition = 12
    ),
    DriverPosition(
        driverPosition = 17,
        driverNumber = 23,
        driverStartingPosition = 10
    ),
    DriverPosition(
        driverPosition = 18,
        driverNumber = 11,
        driverStartingPosition = 16
    ),
    DriverPosition(
        driverPosition = 19,
        driverNumber = 16,
        driverStartingPosition = 11
    ),
    DriverPosition(
        driverPosition = 20,
        driverNumber = 2,
        driverStartingPosition = 13
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
    Triple(
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
        second = Lap(
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
        third = 77.776
    ),
    Triple(
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
        second = Lap(
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
        third = 80.172,
    ),
    Triple(
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
        second = Lap(
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
        third = 79.450,
    )
)

val expectedNullLapDurationResponse = listOf(
    Triple(
        first = Lap(
            driverNumber = 1,
            lapDuration = null,
            lapNumber = 1,
            sector1Duration = null,
            sector2Duration = null,
            sector3Duration = null,
            segmentsSector1 = listOf(
                0,
                0,
                0,
                0,
                0,
                0,
                0
            ),
            segmentsSector2 = listOf(
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0
            ),
            segmentsSector3 = listOf(
                0,
                0,
                0,
                0,
                0,
                0
            )

        ),
        second = Lap(
            driverNumber = 1,
            lapDuration = null,
            lapNumber = 1,
            sector1Duration = null,
            sector2Duration = 32.054,
            sector3Duration = 23.658,
            segmentsSector1 = listOf(
                2048,
                2049,
                2049,
                2049,
                2049,
                2049,
                2049
            ),
            segmentsSector2 = listOf(
                2049,
                2049,
                2049,
                2049,
                2049,
                2049,
                2051,
                2049
            ),
            segmentsSector3 = listOf(
                2048,
                2048,
                2049,
                2051,
                2048,
                2048
            )

        ),
        third = 0.0
    )
)

val expectedStintsResponse = listOf(
    Stint(
        driverNumber = 1,
        stintNumber = 3,
        lapEnd = 67,
        lapStart = 45,
        compound = "SOFT",
        tyreAgeAtStart = 0
    ),
    Stint(
        driverNumber = 2,
        stintNumber = 3,
        lapEnd = 65,
        lapStart = 35,
        compound = "HARD",
        tyreAgeAtStart = 0
    ),
    Stint(
        driverNumber = 3,
        stintNumber = 3,
        lapEnd = 66,
        lapStart = 43,
        compound = "HARD",
        tyreAgeAtStart = 0
    ))

    val fullDataExpectedResponse =  LiveTimingData(
        sessionName = "Race",
        countryCode = "GBR",
        circuitName = "Silverstone",
        driverDataList = persistentListOf(
            DriverData(
                driverNumber = 1,
                driverPosition = 1,
                driverPositionsChanged = 0,
                driverAcronym = "VER",
                teamColor = "#FF3671C6",
                bestLap = 77.776,
                lastLap = 79.774,
                tireCompound = "WET",
                pitNumber = 3,
                stintLaps = 25,
                firstSectorDuration = 24.062,
                secondSectorDuration = 32.054,
                thirdSectorDuration = 23.658,
                firstMicroSectors = persistentListOf(
                    2048,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049
                ),
                secondMicroSectors = persistentListOf(
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2051,
                    2049
                ),
                thirdMicroSectors = persistentListOf(
                    2048,
                    2048,
                    2049,
                    2051,
                    2048,
                    2048
                )
            )
        )
        )

val incompleteDataExpectedResponse = LiveTimingData(
    sessionName = "",
    countryCode = "UNK",
    circuitName = "",
    driverDataList = persistentListOf(
        DriverData(
            driverNumber = 1,
            driverPosition = 1,
            driverPositionsChanged = 0,
            driverAcronym = "VER",
            teamColor = "#FF3671C6",
            bestLap = 0.0,
            lastLap = 0.0,
            tireCompound = "UNK",
            pitNumber = 0,
            stintLaps = 0,
            firstSectorDuration = 0.0,
            secondSectorDuration = 0.0,
            thirdSectorDuration = 0.0,
            firstMicroSectors = persistentListOf(),
            secondMicroSectors = persistentListOf(),
            thirdMicroSectors = persistentListOf()
        )
    )
)

val nullDataExpectedResponse = LiveTimingData(
    sessionName = "",
    countryCode = "UNK",
    circuitName = "",
    driverDataList = persistentListOf(
        DriverData(
            driverNumber = 1,
            driverPosition = 1,
            driverPositionsChanged = 0,
            driverAcronym = "VER",
            teamColor = "#FF3671C6",
            bestLap = 0.0,
            lastLap = 0.0,
            tireCompound = "UNK",
            pitNumber = 3,
            stintLaps = 23,
            firstSectorDuration = 0.0,
            secondSectorDuration = 32.054,
            thirdSectorDuration = 23.658,
            firstMicroSectors = persistentListOf(
                2048,
                2049,
                2049,
                2049,
                2049,
                2049,
                2049
            ),
            secondMicroSectors = persistentListOf(
                2049,
                2049,
                2049,
                2049,
                2049,
                2049,
                2051,
                2049
            ),
            thirdMicroSectors = persistentListOf(
                2048,
                2048,
                2049,
                2051,
                2048,
                2048
            )
        )
    )
)

val sessionsExpectedResponse = listOf(
    Session(
        sessionName = "Race",
        countryCode = "GBR",
        countryName = "Great Britain",
        circuitName = "Silverstone"
    )
)
