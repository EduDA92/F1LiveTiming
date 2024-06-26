package com.example.f1livetiming.ui.model

data class Lap(
    val driverNumber: Int,
    val lapDuration: Double?,
    val lapNumber: Int,
    val sector1Duration: Double?,
    val sector2Duration: Double?,
    val sector3Duration: Double?,
    val segmentsSector1: List<Int?>,
    val segmentsSector2: List<Int?>,
    val segmentsSector3: List<Int?>
)
