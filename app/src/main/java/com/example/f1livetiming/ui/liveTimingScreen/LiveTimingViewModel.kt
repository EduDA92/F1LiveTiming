package com.example.f1livetiming.ui.liveTimingScreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1livetiming.data.repository.F1LiveTimingRepository
import com.example.f1livetiming.ui.model.Interval
import com.example.f1livetiming.ui.util.combine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.math.RoundingMode
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
            ),
            liveTimingRepository.getStints(
                onIdle = { _liveTimingUIState.update { LiveTimingUIState.Idle } },
                onError = { errorMessage ->
                    _liveTimingUIState.update {
                        LiveTimingUIState.Error(
                            errorMessage
                        )
                    }
                }
            ),
            liveTimingRepository.getSession(
                onIdle = { _liveTimingUIState.update { LiveTimingUIState.Idle } },
                onError = { errorMessage ->
                    _liveTimingUIState.update {
                        LiveTimingUIState.Error(
                            errorMessage
                        )
                    }
                }
            ),
            liveTimingRepository.getIntervals(
                onIdle = { _liveTimingUIState.update { LiveTimingUIState.Idle } },
                onError = { errorMessage ->
                    _liveTimingUIState.update {
                        LiveTimingUIState.Error(
                            errorMessage
                        )
                    }
                }
            )) { positions, drivers, laps, stints, session, intervals ->


            val driverDataList = positions.mapIndexed { index, driverPosition ->

                DriverData(
                    driverNumber = driverPosition.driverNumber,
                    driverPosition = driverPosition.driverPosition,
                    driverPositionsChanged = if(session.getOrNull(0)?.sessionName == "Race") {driverPosition.driverStartingPosition - driverPosition.driverPosition} else 0,
                    driverAcronym = drivers.firstOrNull { driver -> driver.driverNumber == driverPosition.driverNumber }?.driverAcronym ?: "UNK",
                    teamColor = drivers.firstOrNull { driver -> driver.driverNumber == driverPosition.driverNumber }?.teamColor ?: "#000000",
                    lastLap = laps.firstOrNull { triple -> triple.first.driverNumber == driverPosition.driverNumber }?.first?.lapDuration ?: 0.0,
                    bestLap = laps.firstOrNull { triple -> triple.first.driverNumber == driverPosition.driverNumber }?.third ?: 0.0,
                    tireCompound = stints.firstOrNull { stint -> stint.driverNumber == driverPosition.driverNumber }?.compound ?: "UNK",
                    pitNumber = stints.firstOrNull{ stint -> stint.driverNumber == driverPosition.driverNumber }?.stintNumber?.minus(1) ?: 0,
                    interval = if(session.getOrNull(0)?.sessionName == "Race") {
                        intervals.firstOrNull { interval: Interval ->  interval.driverNumber == driverPosition.driverNumber}?.interval ?: ""
                    } else {
                        if(driverPosition.driverPosition == 1){
                            ""
                        } else {
                            /** Avoid negative number when the current driver has no time set */
                            if((laps.firstOrNull { triple -> triple.first.driverNumber == driverPosition.driverNumber }?.third ?: 0.0).minus(
                                    laps.firstOrNull { triple -> triple.first.driverNumber == positions[index - 1].driverNumber }?.third ?: 0.0
                                ) > 0){
                                (laps.firstOrNull { triple -> triple.first.driverNumber == driverPosition.driverNumber }?.third ?: 0.0).minus(
                                    laps.firstOrNull { triple -> triple.first.driverNumber == positions[index - 1].driverNumber }?.third ?: 0.0
                                ).toBigDecimal().setScale(3, RoundingMode.UP).toString()
                            } else {
                                ""
                            }

                        }
                    },
                    gapToLeader = if(session.getOrNull(0)?.sessionName == "Race") {
                        intervals.firstOrNull { interval: Interval ->  interval.driverNumber == driverPosition.driverNumber}?.gapToLeader ?: ""
                    } else {
                        if(driverPosition.driverPosition == 1){
                            ""
                        } else {
                            if((laps.firstOrNull { triple -> triple.first.driverNumber == driverPosition.driverNumber }?.third ?: 0.0).minus(
                                    laps.firstOrNull { triple -> triple.first.driverNumber == positions[0].driverNumber }?.third ?: 0.0
                                ) > 0){
                                (laps.firstOrNull { triple -> triple.first.driverNumber == driverPosition.driverNumber }?.third ?: 0.0).minus(
                                    laps.firstOrNull { triple -> triple.first.driverNumber == positions[0].driverNumber }?.third ?: 0.0
                                ).toBigDecimal().setScale(3, RoundingMode.UP).toString()
                            } else {
                                ""
                            }
                        }
                    },
                    stintLaps = stints.firstOrNull{ stint -> stint.driverNumber == driverPosition.driverNumber }?.lapEnd?.minus(
                        stints.firstOrNull{ stint -> stint.driverNumber == driverPosition.driverNumber }?.lapStart ?: 0
                    )?.plus(stints.firstOrNull{ stint -> stint.driverNumber == driverPosition.driverNumber }?.tyreAgeAtStart ?: 0) ?: 0,
                    firstSectorDuration = laps.firstOrNull { triple -> triple.second.driverNumber == driverPosition.driverNumber }?.second?.sector1Duration ?: 0.0,
                    secondSectorDuration = laps.firstOrNull { triple -> triple.second.driverNumber == driverPosition.driverNumber }?.second?.sector2Duration ?: 0.0,
                    thirdSectorDuration = laps.firstOrNull { triple -> triple.second.driverNumber == driverPosition.driverNumber }?.second?.sector3Duration ?: 0.0,
                    firstMicroSectors = laps.firstOrNull { triple -> triple.second.driverNumber == driverPosition.driverNumber }?.second?.segmentsSector1?.map{microsector ->
                        microsector ?: 0
                    }?.toImmutableList() ?: persistentListOf(),
                    secondMicroSectors = laps.firstOrNull { triple -> triple.second.driverNumber == driverPosition.driverNumber }?.second?.segmentsSector2?.map{microsector ->
                        microsector ?: 0
                    }?.toImmutableList() ?: persistentListOf(),
                    thirdMicroSectors = laps.firstOrNull { triple -> triple.second.driverNumber == driverPosition.driverNumber }?.second?.segmentsSector3?.map{microsector ->
                        microsector ?: 0
                    }?.toImmutableList() ?: persistentListOf(),
                )

            }

            LiveTimingData(
                sessionName = session.getOrNull(0)?.sessionName ?: "",
                countryCode = session.getOrNull(0)?.countryCode ?: "UNK",
                circuitName = session.getOrNull(0)?.circuitName ?: "",
                driverDataList = driverDataList.toImmutableList()
            )

        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            LiveTimingData(
                sessionName = "Loading",
                countryCode = "UNK",
                circuitName = "LiveTiming",
                driverDataList = persistentListOf()
            )
        )

}

sealed interface LiveTimingUIState {
    data object Loading : LiveTimingUIState
    data object Idle : LiveTimingUIState
    data class Error(val message: String) : LiveTimingUIState
}

data class LiveTimingData(
    val sessionName: String,
    val countryCode: String,
    val circuitName: String,
    val driverDataList: ImmutableList<DriverData>
)

data class DriverData(
    val driverNumber: Int,
    val driverPosition: Int,
    val driverPositionsChanged: Int,
    val driverAcronym: String,
    val teamColor: String,
    val lastLap: Double,
    val bestLap: Double,
    val tireCompound: String,
    val stintLaps: Int,
    val pitNumber: Int,
    val interval: String,
    val gapToLeader: String,
    val firstSectorDuration: Double,
    val secondSectorDuration: Double,
    val thirdSectorDuration: Double,
    val firstMicroSectors: ImmutableList<Int>,
    val secondMicroSectors: ImmutableList<Int>,
    val thirdMicroSectors: ImmutableList<Int>
)