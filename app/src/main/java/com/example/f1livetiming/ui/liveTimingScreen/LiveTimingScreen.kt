package com.example.f1livetiming.ui.liveTimingScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

                    items(items = liveTimingData.driverDataList) {

                        Row(modifier = Modifier.fillMaxWidth()) {

                            DriverTag(
                                driverPosition = it.driverPosition,
                                driverName = it.driverAcronym,
                                color = Color(it.teamColor.toColorInt())
                            )

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
                teamColor = "#3671C6"
            ),
                DriverData(
                    driverNumber = 14,
                    driverPosition = 2,
                    driverAcronym = "ALO",
                    teamColor = "#229971"
                ),
                DriverData(
                    driverNumber = 44,
                    driverPosition = 3,
                    driverAcronym = "HAM",
                    teamColor = "#27F4D2"
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