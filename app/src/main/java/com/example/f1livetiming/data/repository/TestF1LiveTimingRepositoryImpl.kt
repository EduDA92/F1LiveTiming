package com.example.f1livetiming.data.repository

import com.example.f1livetiming.ui.model.Driver
import com.example.f1livetiming.ui.model.DriverPosition
import com.example.f1livetiming.ui.model.Lap
import com.example.f1livetiming.ui.model.Stint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


enum class ResponseState{
    WAITING,
    SUCCESS,
    ERROR
}

class TestF1LiveTimingRepositoryImpl : F1LiveTimingRepository  {

    private var driverPositionsList = emptyList<DriverPosition>()
    private var driverList = emptyList<Driver>()
    private var lapList = emptyList<Pair<Lap, Double>>()
    private var stintList = emptyList<Stint>()
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

    override fun getLaps(
        onIdle: () -> Unit,
        onError: (String) -> Unit
    ): Flow<List<Pair<Lap, Double>>> = flow {
        when(responseState){
            ResponseState.WAITING -> {
                // no-op
            }
            ResponseState.SUCCESS -> {
                onIdle()
                emit(lapList)
            }
            ResponseState.ERROR -> {
                onError("Error")
            }
        }
    }

    override fun getStints(onIdle: () -> Unit, onError: (String) -> Unit): Flow<List<Stint>> = flow {
        when(responseState){
            ResponseState.WAITING -> {
                // no-op
            }
            ResponseState.SUCCESS -> {
                onIdle()
                emit(stintList)
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

    fun changeLapsList(list: List<Pair<Lap, Double>>){
        lapList = list
    }

    fun changeStintList(list: List<Stint>){
        stintList = list
    }

    fun changeResponseState(state: ResponseState){
         responseState = state
    }

}