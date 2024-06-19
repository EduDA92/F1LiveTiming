package com.example.f1livetiming.data.repository

import com.example.f1livetiming.ui.model.DriverPosition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart


enum class ResponseState{
    WAITING,
    SUCCESS,
    ERROR
}

class TestF1LiveTimingRepositoryImpl : F1LiveTimingRepository  {

    private var driverPositionsList = emptyList<DriverPosition>()
    private var responseState = ResponseState.WAITING

    override fun getDriversPositions(
        onIdle: () -> Unit,
        onError: (String) -> Unit
    ): Flow<List<DriverPosition>> = flow {

        when(responseState){
            ResponseState.WAITING -> {
                // no-op
            }
            ResponseState.SUCCESS -> {
                onIdle()
                emit(driverPositionsList)
            }
            ResponseState.ERROR -> {
                onError("Error")
            }
        }

    }

    fun changeDriverPositionList(list: List<DriverPosition>){
        driverPositionsList = list
    }

    fun changeResponseState(state: ResponseState){
         responseState = state
    }

}