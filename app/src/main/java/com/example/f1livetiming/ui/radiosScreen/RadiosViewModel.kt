package com.example.f1livetiming.ui.radiosScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1livetiming.data.repository.F1LiveTimingRepository
import com.example.f1livetiming.ui.model.TeamRadio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class RadiosViewModel @Inject constructor(
    liveTimingRepository: F1LiveTimingRepository
) : ViewModel() {

    private val _radiosUIState: MutableStateFlow<RadiosUIState> =
        MutableStateFlow(RadiosUIState.Loading)
    val radiosUIState = _radiosUIState.asStateFlow()

    val radiosData: StateFlow<RadiosData> = combine(
        liveTimingRepository.getDrivers(
            onIdle = { _radiosUIState.update { RadiosUIState.Idle } },
            onError = { error -> _radiosUIState.update { RadiosUIState.Error(error) } }
        ),
        liveTimingRepository.getTeamsRadio(
            onIdle = { _radiosUIState.update { RadiosUIState.Idle } },
            onError = { error -> _radiosUIState.update { RadiosUIState.Error(error) } }
        )
    ) { drivers, radios ->

        // Get the current device time zone
        val currentTimeZone = ZoneId.systemDefault()

        // 	The UTC date and time, in ISO 8601 format.
        val groupedTeamRadios = radios.groupBy { it.driverNumber }.mapKeys { mapEntry ->

            // Pair <DriverAcronym, TeamColor>
            Pair(
                drivers.firstOrNull { it.driverNumber == mapEntry.key }?.driverAcronym ?: "UNK",
                drivers.firstOrNull { it.driverNumber == mapEntry.key }?.teamColor ?: "#000000"
            )

        }.mapValues { mapEntry ->
            mapEntry.value.map { teamRadio ->

                // Parse the UTC Date
                val dateTimeFormatted = LocalDateTime.parse(teamRadio.date, DateTimeFormatter.ISO_DATE_TIME)

                // Convert the UTC time to the device time zone
                val localDateTime =
                    dateTimeFormatted.atZone(ZoneId.of("UTC")).withZoneSameInstant(currentTimeZone)
                        .toLocalDateTime()

                // Formater to format the time to HH:mm:ss
                val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

                teamRadio.copy(
                    date = localDateTime.format(formatter)
                )
            }
        }

        RadiosData(
            driverRadios = groupedTeamRadios
        )

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        RadiosData(
            driverRadios = emptyMap()
        )
    )

}

sealed interface RadiosUIState {
    data object Loading : RadiosUIState
    data object Idle : RadiosUIState
    data class Error(val message: String) : RadiosUIState
}

data class RadiosData(
    val driverRadios: Map<Pair<String, String>, List<TeamRadio>>
)