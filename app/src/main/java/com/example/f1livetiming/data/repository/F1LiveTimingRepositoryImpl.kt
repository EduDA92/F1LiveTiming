package com.example.f1livetiming.data.repository

import android.util.Log
import com.example.f1livetiming.data.dispatchers.Dispatcher
import com.example.f1livetiming.data.dispatchers.F1LiveTimingDispatchers
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


class F1LiveTimingRepositoryImpl @Inject constructor(
    private val f1Client: F1Client,
    @Dispatcher(F1LiveTimingDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : F1LiveTimingRepository {

    override fun getDriversPositions(
        onError: (String) -> Unit
    ): Flow<List<DriverPosition>> = flow {

        try {
            val driversPositionResponse = f1Client.getDriversPosition("latest")

            if (driversPositionResponse.isSuccessful) {

                Log.d(TAG, "Driver Positions response successful")

                val driverPositionsResponse = driversPositionResponse.body()!!.groupBy { it.driverNumber }
                val driverPositionList = mutableListOf<DriverPosition>()

                /* Group the data by driver, we cannot request the data by date because the initial lineup
                * will be at the start of the session and the subsequent data is only position updates.
                * so we need the full data in order to know the position of each driver at any time */

                driverPositionsResponse.forEach { (driverNumber, driverPositions) ->

                    /* For each driver create a DriverPosition object with the driver number and
                    * the latest updated position. */

                    driverPositionList.add(
                        DriverPosition(
                            driverNumber,
                            driverPositions.sortedBy {
                                LocalDateTime.parse(
                                    it.date,
                                    DateTimeFormatter.ISO_DATE_TIME
                                )
                            }.last().position
                        )
                    )

                }

                Log.d(TAG, driverPositionList.toString())

                emit(driverPositionList.sortedBy { it.driverPosition })


            } else {
                Log.d(TAG, "Driver Positions response error")
                Log.d(TAG, driversPositionResponse.errorBody().toString())
                onError(driversPositionResponse.errorBody().toString())
            }

        } catch (e: Exception){
            Log.d(TAG, "Exception: " + e.message.toString())
            onError(e.message.toString())
        }


    }.flowOn(ioDispatcher)
}