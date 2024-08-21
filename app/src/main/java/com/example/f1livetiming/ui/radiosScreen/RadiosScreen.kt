package com.example.f1livetiming.ui.radiosScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.f1livetiming.ui.model.TeamRadio
import com.example.f1livetiming.ui.radiosScreen.composables.DriverHeader
import com.example.f1livetiming.ui.radiosScreen.composables.RadiosHeader
import com.example.f1livetiming.ui.radiosScreen.navigation.radiosScreenRoute

@Composable
fun RadiosScrenRoute(
    modifier: Modifier = Modifier,
    viewModel: RadiosViewModel = hiltViewModel()
) {

    val state by viewModel.radiosUIState.collectAsStateWithLifecycle()
    val data by viewModel.radiosData.collectAsStateWithLifecycle()
    //workflow test
    RadiosScreen(
        modifier = modifier.testTag(radiosScreenRoute),
        state = state,
        data = data
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RadiosScreen(
    modifier: Modifier = Modifier,
    state: RadiosUIState,
    data: RadiosData
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {

        RadiosHeader(
            modifier = Modifier.padding(8.dp),
            radiosUIState = state
        )

        LazyColumn(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {

            data.driverRadios.forEach { (driverData, teamRadios) ->

                stickyHeader {
                    Surface(modifier = Modifier.fillMaxWidth()) {

                        DriverHeader(
                            modifier = Modifier.wrapContentWidth(),
                            driverAcronym = driverData.first,
                            teamColor = Color(driverData.second.toColorInt())
                        )

                    }
                }

                items(teamRadios) {
                    Text(text = it.date)
                }

            }

        }

    }
}

@Preview
@Composable
fun RadiosScreenPreview() {

    RadiosScreen(
        state = RadiosUIState.Idle,
        data = RadiosData(
            driverRadios = mapOf(
                Pair("VER", "#3671C6") to listOf(
                    TeamRadio(
                        driverNumber = 1,
                        recordingUrl = "",
                        date = "16:00:00"
                    )
                ),
                Pair("HAM", "#6CD3BF") to listOf(
                    TeamRadio(
                        driverNumber = 44,
                        recordingUrl = "",
                        date = "16:11:00"
                    )
                ),
                Pair("ALO", "#358C75") to listOf(
                    TeamRadio(
                        driverNumber = 14,
                        recordingUrl = "",
                        date = "16:22:00"
                    )
                )
            )
        )
    )

}