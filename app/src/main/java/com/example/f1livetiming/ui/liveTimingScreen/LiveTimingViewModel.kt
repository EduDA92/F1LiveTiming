package com.example.f1livetiming.ui.liveTimingScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.f1livetiming.data.repository.F1LiveTimingRepository
import com.example.f1livetiming.ui.model.DriverPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class LiveTimingViewModel @Inject constructor(
    private val liveTimingRepository: F1LiveTimingRepository
) : ViewModel() {


    private val _liveTimingUIState: MutableStateFlow<LiveTimingUIState> =
        MutableStateFlow(LiveTimingUIState.Loading)
    val liveTimingUIState: StateFlow<LiveTimingUIState> = _liveTimingUIState.asStateFlow()

    val liveTimingData: StateFlow<LiveTimingData> =
        combine(liveTimingRepository.getDriversPositions(
            onIdle = { _liveTimingUIState.update { LiveTimingUIState.Idle } },
            onError = { errorMessage ->
                _liveTimingUIState.update {
                    LiveTimingUIState.Error(
                        errorMessage
                    )
                }
            }
        ), liveTimingRepository.getDrivers(
            onIdle = { _liveTimingUIState.update { LiveTimingUIState.Idle } },
            onError = { errorMessage ->
                _liveTimingUIState.update {
                    LiveTimingUIState.Error(
                        errorMessage
                    )
                }
            }
        )) { positions, drivers ->


            val driverDataList = positions.map {

                DriverData(
                    driverNumber = it.driverNumber,
                    driverPosition = it.driverPosition,
                    driverAcronym = drivers.first { driver -> driver.driverNumber == it.driverNumber }.driverAcronym,
                    teamColor = drivers.first { driver -> driver.driverNumber == it.driverNumber }.teamColor
                )

            }

            LiveTimingData(
                driverDataList = driverDataList
            )

        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            LiveTimingData(
                driverDataList = emptyList()
            )
        )

}

sealed interface LiveTimingUIState {
    data object Loading : LiveTimingUIState
    data object Idle : LiveTimingUIState
    data class Error(val message: String) : LiveTimingUIState
}

data class LiveTimingData(
    val driverDataList: List<DriverData>
)

data class DriverData(
    val driverNumber: Int,
    val driverPosition: Int,
    val driverAcronym: String,
    val teamColor: String
)