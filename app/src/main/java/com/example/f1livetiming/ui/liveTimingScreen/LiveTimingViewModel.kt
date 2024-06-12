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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LiveTimingViewModel @Inject constructor(
    private val liveTimingRepository: F1LiveTimingRepository
) : ViewModel() {



    private val _liveTimingUIState: MutableStateFlow<LiveTimingUIState> =  MutableStateFlow(LiveTimingUIState.Loading)
    val liveTimingUIState: StateFlow<LiveTimingUIState> = _liveTimingUIState.asStateFlow()

    val liveTimingData: StateFlow<LiveTimingData> = liveTimingRepository.getDriversPositions(
        onStart = {_liveTimingUIState.update { LiveTimingUIState.Loading }},
        onIdle = {_liveTimingUIState.update { LiveTimingUIState.Idle }},
        onError = {_liveTimingUIState.update { LiveTimingUIState.Error(it.toString()) }}
    ).map {

        LiveTimingData(
            positions = it
        )

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        LiveTimingData(
            positions = emptyList()
        )
    )

}

sealed interface LiveTimingUIState {
    data object Loading : LiveTimingUIState
    data object Idle : LiveTimingUIState
    data class Error(val message: String) : LiveTimingUIState
}

data class LiveTimingData(
    val positions: List<DriverPosition>
)