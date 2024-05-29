package com.example.f1livetiming.ui.liveTimingScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun LiveTimingRoute(
    modifier: Modifier = Modifier,
    viewModel: LiveTimingViewModel = hiltViewModel()
) {

    val state by  viewModel.liveTimingUIState.collectAsStateWithLifecycle()

    LiveTimingScreen(
        modifier = modifier,
        state = state
    )

}

@Composable
fun LiveTimingScreen(
    modifier: Modifier = Modifier,
    state: LiveTimingUIState
) {

    when(state){
        is LiveTimingUIState.Error -> {
            Text("Error", modifier = modifier.fillMaxSize())
        }
        LiveTimingUIState.Loading -> {
            Text("Loading", modifier = modifier.fillMaxSize())
        }
        is LiveTimingUIState.Success -> {

            LazyColumn {

                items(items = state.liveTimingData.positions){

                    Text(it.driverNumber.toString())

                }

            }

        }
    }

}


@Preview
@Composable
fun previewLiveTimingScreen() {

    LiveTimingScreen(
        state = LiveTimingUIState.Success(LiveTimingData(emptyList()))
    )

}