package com.example.f1livetiming.data.repository

import android.util.Log
import com.example.f1livetiming.data.dispatchers.Dispatcher
import com.example.f1livetiming.data.dispatchers.F1LiveTimingDispatchers
import com.example.f1livetiming.data.network.F1Client
import com.example.f1livetiming.ui.model.DriverPosition
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

private const val TAG = "F1PastEventTimingRepo"


class F1LiveTimingRepositoryImpl @Inject constructor(
    private val f1Client: F1Client,
    @Dispatcher(F1LiveTimingDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : F1LiveTimingRepository {

    /**
     * Get the drivers positions from the API returns List of [DriverPosition].
     * The flow will call the API every 5 seconds for updates.
     */

    override fun getDriversPositions(
        onIdle: () -> Unit,
        onError: (String) -> Unit
    ): Flow<List<DriverPosition>> = flow {

        while(true){

            try {
                val driversPositionResponse = f1Client.getDriversPosition("latest")

                if (driversPositionResponse.isSuccessful) {

                    val driverPositionsResponse =
                        driversPositionResponse.body()!!.groupBy { it.driverNumber }
                    val driverPositionList = mutableListOf<DriverPosition>()

                    /** Group the data by driver, we cannot request the data by date because the initial lineup
                     * will be at the start of the session and the subsequent data is only position updates.
                     * so we need the full data in order to know the position of each driver at any time */

                    driverPositionsResponse.forEach { (driverNumber, driverPositions) ->

                        /** For each driver create a DriverPosition object with the driver number and
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

                    emit(driverPositionList.sortedBy { it.driverPosition })
                    onIdle()


                } else {
                    onError(driversPositionResponse.errorBody().toString())
                }

            } catch (e: Exception) {
                onError(e.message.toString())
            }

            delay(5000)

        }

    }.flowOn(ioDispatcher)
}