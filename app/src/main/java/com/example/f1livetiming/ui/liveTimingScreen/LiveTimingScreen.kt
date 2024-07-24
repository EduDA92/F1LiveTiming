package com.example.f1livetiming.ui.liveTimingScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.f1livetiming.R
import com.example.f1livetiming.ui.liveTimingScreen.composables.DriverInterval
import com.example.f1livetiming.ui.liveTimingScreen.composables.DriverLaps
import com.example.f1livetiming.ui.liveTimingScreen.composables.DriverMicroSectors
import com.example.f1livetiming.ui.liveTimingScreen.composables.DriverPositionChange
import com.example.f1livetiming.ui.liveTimingScreen.composables.DriverStint
import com.example.f1livetiming.ui.liveTimingScreen.composables.DriverTag
import com.example.f1livetiming.ui.liveTimingScreen.composables.LiveTimingHeader
import kotlinx.collections.immutable.persistentListOf


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
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        LiveTimingHeader(
            countryCode = liveTimingData.countryCode,
            sessionName = liveTimingData.sessionName,
            circuitName =liveTimingData.circuitName,
            liveTimingUIState = state
        )
        Box {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(items = liveTimingData.driverDataList, key = { it.driverNumber }) {

                    var expanded by rememberSaveable { mutableStateOf(false) }

                    Box(modifier = Modifier
                        .animateContentSize()
                        .clickable { expanded = !expanded }
                        .testTag(stringResource(id = R.string.driver_box_test_tag))) {
                        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateItemPlacement(tween(250)),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                DriverTag(
                                    driverPosition = it.driverPosition,
                                    driverName = it.driverAcronym,
                                    color = Color(it.teamColor.toColorInt())
                                )

                                Spacer(modifier = Modifier.size(5.dp))

                                DriverStint(
                                    tireCompound = it.tireCompound,
                                    stintLaps = it.stintLaps,
                                    pitNumber = it.pitNumber
                                )

                                Spacer(modifier = Modifier.size(5.dp))

                                DriverPositionChange(positionChange = it.driverPositionsChanged)

                                Spacer(modifier = Modifier.size(5.dp))
                                
                                DriverInterval(gapToLeader = it.gapToLeader, interval = it.interval)

                                Spacer(modifier = Modifier.size(5.dp))

                                DriverLaps(lastLap = it.lastLap, bestLap = it.bestLap)

                            }

                            if (expanded) {
                                DriverMicroSectors(
                                    firstMicroSectorTime = it.firstSectorDuration,
                                    firstMicroSectors = it.firstMicroSectors,
                                    secondMicroSectorTime = it.secondSectorDuration,
                                    secondMicroSectors = it.secondMicroSectors,
                                    thirdMicroSectorTime = it.thirdSectorDuration,
                                    thirdMicroSectors = it.thirdMicroSectors
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.size(5.dp))

                    HorizontalDivider(modifier = Modifier.fillMaxWidth())

                }
            }

            if (state is LiveTimingUIState.Loading || liveTimingData.driverDataList.isEmpty()) {
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
            sessionName = "Race",
            countryCode = "GBR",
            circuitName = "Silverstone",
            persistentListOf(
                DriverData(
                    driverNumber = 1,
                    driverPosition = 1,
                    driverPositionsChanged = 24,
                    driverAcronym = "VER",
                    teamColor = "#3671C6",
                    lastLap = 79.774,
                    bestLap = 77.776,
                    tireCompound = "SOFT",
                    pitNumber = 3,
                    stintLaps = 23,
                    interval = "95.554",
                    gapToLeader = "1L",
                    firstSectorDuration = 22.5,
                    secondSectorDuration = 22.6,
                    thirdSectorDuration = 76.9,
                    firstMicroSectors = persistentListOf(
                        0,
                        2048,
                        2049,
                        2050,
                        2051,
                        2052,
                        2064,
                        2068
                    ),
                    secondMicroSectors = persistentListOf(
                        0,
                        2048,
                        2049,
                        2050,
                        2051,
                        2052,
                        2064,
                        2068
                    ),
                    thirdMicroSectors = persistentListOf(
                        0,
                        2048,
                        2049,
                        2050,
                        2051,
                        2052,
                        2064,
                        2068
                    )
                ),
                DriverData(
                    driverNumber = 14,
                    driverPosition = 2,
                    driverPositionsChanged = 0,
                    driverAcronym = "ALO",
                    teamColor = "#229971",
                    lastLap = 79.743,
                    bestLap = 78.334,
                    tireCompound = "HARD",
                    pitNumber = 3,
                    stintLaps = 23,
                    interval = "123.554",
                    gapToLeader = "10L",
                    firstSectorDuration = 22.5,
                    secondSectorDuration = 22.6,
                    thirdSectorDuration = 76.9,
                    firstMicroSectors = persistentListOf(
                        0,
                        2048,
                        2049,
                        2050,
                        2051,
                        2052,
                        2064,
                        2068
                    ),
                    secondMicroSectors = persistentListOf(
                        0,
                        2048,
                        2049,
                        2050,
                        2051,
                        2052,
                        2064,
                        2068
                    ),
                    thirdMicroSectors = persistentListOf(
                        0,
                        2048,
                        2049,
                        2050,
                        2051,
                        2052,
                        2064,
                        2068
                    )
                ),
                DriverData(
                    driverNumber = 44,
                    driverPosition = 3,
                    driverPositionsChanged = -24,
                    driverAcronym = "HAM",
                    teamColor = "#27F4D2",
                    lastLap = 80.041,
                    bestLap = 77.809,
                    tireCompound = "WET",
                    pitNumber = 3,
                    stintLaps = 23,
                    interval = "0",
                    gapToLeader = "0",
                    firstSectorDuration = 22.5,
                    secondSectorDuration = 22.6,
                    thirdSectorDuration = 76.9,
                    firstMicroSectors = persistentListOf(
                        0,
                        2048,
                        2049,
                        2050,
                        2051,
                        2052,
                        2064,
                        2068
                    ),
                    secondMicroSectors = persistentListOf(
                        0,
                        2048,
                        2049,
                        2050,
                        2051,
                        2052,
                        2064,
                        2068
                    ),
                    thirdMicroSectors = persistentListOf(
                        0,
                        2048,
                        2049,
                        2050,
                        2051,
                        2052,
                        2064,
                        2068
                    )
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
        liveTimingData = LiveTimingData("", "", "", persistentListOf())
    )

}