package com.example.f1livetiming.data.repository

import android.app.blob.BlobStoreManager.Session
import android.util.Log
import com.example.f1livetiming.data.dispatchers.Dispatcher
import com.example.f1livetiming.data.dispatchers.F1LiveTimingDispatchers
import com.example.f1livetiming.data.mapper.F1DTOMappers.asDomain
import com.example.f1livetiming.data.network.F1Client
import com.example.f1livetiming.ui.model.DriverPosition
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

private const val TAG = "F1PastEventTimingRepo"

// This repo is for testing functionality before tackling live events
// session_key = 9472 Bahrain

class F1PastEventTimingRepositoryImpl @Inject constructor(
    private val f1Client: F1Client,
    @Dispatcher(F1LiveTimingDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : F1LiveTimingRepository {

    override fun getDriversPositions(
        sessionKey: String,
        onError: (String) -> Unit
    ): Flow<List<DriverPosition>> = flow {

        val driversPositionResponse = f1Client.getDriversPosition(sessionKey)

        if(driversPositionResponse.isSuccessful){

            Log.d(TAG, "Driver Positions response successful")

            val list = driversPositionResponse.body()!!

            // The UTC date and time, in ISO 8601 format.
            val firstDateTime = LocalDateTime.parse(list[0].date, DateTimeFormatter.ISO_DATE_TIME)

            emit(list.filter { LocalDateTime.parse(it.date, DateTimeFormatter.ISO_DATE_TIME) == firstDateTime }.asDomain())



        } else  {
            Log.d(TAG, "Driver Positions response error")
            Log.d(TAG, driversPositionResponse.errorBody().toString())
            onError(driversPositionResponse.errorBody().toString())
        }

    }.flowOn(ioDispatcher)
}