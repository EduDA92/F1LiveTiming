package com.example.f1livetiming.ui.liveTimingScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1livetiming.data.repository.F1LiveTimingRepository
import com.example.f1livetiming.ui.model.Lap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LiveTimingViewModel @Inject constructor(
     liveTimingRepository: F1LiveTimingRepository
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
        ),
            liveTimingRepository.getLaps(
                onIdle = { _liveTimingUIState.update { LiveTimingUIState.Idle } },
                onError = { errorMessage ->
                    _liveTimingUIState.update {
                        LiveTimingUIState.Error(
                            errorMessage
                        )
                    }
                }
            )) { positions, drivers, laps ->


            val driverDataList = positions.map {

                DriverData(
                    driverNumber = it.driverNumber,
                    driverPosition = it.driverPosition,
                    driverAcronym = drivers.firstOrNull { driver -> driver.driverNumber == it.driverNumber }?.driverAcronym ?: "UNK",
                    teamColor = drivers.firstOrNull { driver -> driver.driverNumber == it.driverNumber }?.teamColor ?: "#000000",
                    lastLap = laps.firstOrNull { pair: Pair<Lap, Double> -> pair.first.driverNumber == it.driverNumber }?.first?.lapDuration ?: 0.0,
                    bestLap = laps.firstOrNull { pair: Pair<Lap, Double> -> pair.first.driverNumber == it.driverNumber }?.second ?: 0.0
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
    val teamColor: String,
    val lastLap: Double,
    val bestLap: Double
)