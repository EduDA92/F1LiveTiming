package com.example.f1livetiming.ui.liveTimingScreen

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.f1livetiming.ui.liveTimingScreen.composables.DriverLaps
import com.example.f1livetiming.ui.liveTimingScreen.composables.DriverTag


@Composable
fun LiveTimingRoute(
    modifier: Modifier = Modifier,
    viewModel: LiveTimingViewModel = hiltViewModel()
) {

    val state by viewModel.liveTimingUIState.collectAsStateWithLifecycle()
    val liveTimingData by viewModel.liveTimingData.collectAsStateWithLifecycle()

    LiveTimingScreen(
        modifier = modifier,
        state = state,
        liveTimingData = liveTimingData
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LiveTimingScreen(
    modifier: Modifier = Modifier,
    state: LiveTimingUIState,
    liveTimingData: LiveTimingData
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {

        when (state) {
            is LiveTimingUIState.Error -> {
                Text(state.message, modifier = Modifier.align(Alignment.Center))
            }

            LiveTimingUIState.Idle -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    items(items = liveTimingData.driverDataList, key = { it.driverNumber }) {

                        Row(
                            modifier = Modifier.fillMaxWidth().animateItemPlacement(tween(250)),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            DriverTag(
                                driverPosition = it.driverPosition,
                                driverName = it.driverAcronym,
                                color = Color(it.teamColor.toColorInt())
                            )

                            Spacer(modifier = Modifier.size(10.dp))

                            DriverStint(
                                tireCompound = it.tireCompound,
                                stintLaps = it.stintLaps,
                                pitNumber = it.pitNumber
                            )

                            Spacer(modifier = Modifier.size(10.dp))

                            DriverLaps(lastLap = it.lastLap, bestLap = it.bestLap)

                        }


                    }
                }
            }

            LiveTimingUIState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }

}

@Preview
@Composable
fun PreviewFullListLiveTimingScreen() {

    LiveTimingScreen(
        state = LiveTimingUIState.Idle,
        liveTimingData = LiveTimingData(
            listOf(
                DriverData(
                    driverNumber = 1,
                    driverPosition = 1,
                    driverAcronym = "VER",
                    teamColor = "#3671C6",
                    lastLap = 79.774,
                    bestLap = 77.776,
                    tireCompound = "SOFT",
                    pitNumber = 3,
                    stintLaps = 23
                ),
                DriverData(
                    driverNumber = 14,
                    driverPosition = 2,
                    driverAcronym = "ALO",
                    teamColor = "#229971",
                    lastLap = 79.743,
                    bestLap = 78.334,
                    tireCompound = "HARD",
                    pitNumber = 3,
                    stintLaps = 23
                ),
                DriverData(
                    driverNumber = 44,
                    driverPosition = 3,
                    driverAcronym = "HAM",
                    teamColor = "#27F4D2",
                    lastLap = 80.041,
                    bestLap = 77.809,
                    tireCompound = "WET",
                    pitNumber = 3,
                    stintLaps = 23
                )
            )
        )

    )

}

@Preview
@Composable
fun PreviewLiveTimingScreen() {

    LiveTimingScreen(
        state = LiveTimingUIState.Loading,
        liveTimingData = LiveTimingData(emptyList())
    )

}