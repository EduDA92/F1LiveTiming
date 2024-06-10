package com.example.f1livetiming.ui.liveTimingScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1livetiming.data.repository.F1LiveTimingRepository
import com.example.f1livetiming.ui.model.DriverPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LiveTimingViewModel @Inject constructor(
    private val liveTimingRepository: F1LiveTimingRepository
) : ViewModel() {

    val liveTimingUIState: StateFlow<LiveTimingUIState> = liveTimingRepository.getDriversPositions {}.map{
        val data = LiveTimingData(it)
        LiveTimingUIState.Success(data)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        LiveTimingUIState.Loading
    )

}

sealed interface LiveTimingUIState {
    data object Loading : LiveTimingUIState
    data class Success(val liveTimingData: LiveTimingData) : LiveTimingUIState
    data class Error(val message: String) : LiveTimingUIState
}

data class LiveTimingData(
    val positions: List<DriverPosition>
)