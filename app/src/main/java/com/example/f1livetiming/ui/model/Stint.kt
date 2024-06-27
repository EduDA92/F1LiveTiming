package com.example.f1livetiming.ui.model



data class Stint(
    val compound: String,
    val driverNumber: Int,
    val lapEnd: Int,
    val lapStart: Int,
    val stintNumber: Int,
    val tyreAgeAtStart: Int
)
