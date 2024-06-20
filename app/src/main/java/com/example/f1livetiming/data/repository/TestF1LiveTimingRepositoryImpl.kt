package com.example.f1livetiming.data.repository

import com.example.f1livetiming.ui.model.Driver
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
    private var driverList = emptyList<Driver>()
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



    override fun getDrivers(onIdle: () -> Unit, onError: (String) -> Unit): Flow<List<Driver>> = flow {

        when(responseState){
            ResponseState.WAITING -> {
                // no-op
            }
            ResponseState.SUCCESS -> {
                onIdle()
                emit(driverList)
            }
            ResponseState.ERROR -> {
                onError("Error")
            }
        }

    }

    fun changeDriverPositionList(list: List<DriverPosition>){
        driverPositionsList = list
    }

    fun changeDriverList(list: List<Driver>){
        driverList = list
    }

    fun changeResponseState(state: ResponseState){
         responseState = state
    }

}