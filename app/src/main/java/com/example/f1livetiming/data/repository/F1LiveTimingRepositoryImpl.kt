package com.example.f1livetiming.data.repository

import com.example.f1livetiming.data.dispatchers.Dispatcher
import com.example.f1livetiming.data.dispatchers.F1LiveTimingDispatchers
import com.example.f1livetiming.data.mapper.asDomain
import com.example.f1livetiming.data.network.F1Client
import com.example.f1livetiming.ui.model.Driver
import com.example.f1livetiming.ui.model.DriverPosition
import com.example.f1livetiming.ui.model.Lap
import com.example.f1livetiming.ui.model.Session
import com.example.f1livetiming.ui.model.Stint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException
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

        while (true) {

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
                                }.last().position,
                                driverPositions.sortedBy {
                                    LocalDateTime.parse(
                                        it.date,
                                        DateTimeFormatter.ISO_DATE_TIME
                                    )
                                }.first().position
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

            delay(3000)

        }

    }.flowOn(ioDispatcher)

    /** Get the driver list from the API and returns a list of [Driver]
     * If the flow fails because of an IOException(No internet connection when making the request)
     * the flow will retry with constant backoff of 5s */

    override fun getDrivers(
        onIdle: () -> Unit,
        onError: (String) -> Unit
    ): Flow<List<Driver>> = flow {

        val driverResponse = f1Client.getDrivers("latest")

        if (driverResponse.isSuccessful) {
            emit(driverResponse.body()!!.asDomain())
            onIdle()
        } else {
            onError(driverResponse.errorBody().toString())
        }

    }.retryWhen { cause: Throwable, _ ->
        if (cause is IOException) {
            onError(cause.message.toString())
            delay(5000)
            true
        } else {
            false
        }
    }.flowOn(ioDispatcher)

    /** Get the lap list from the API, this will return a list that contains a Triple of [Lap] and [Double]
     * the triple will be Triple<LastLap, CurrentLap, BestLapDuration>
     * the LastLap will be the latest lap in the  list with lapDuration != null
     * the currentLap will be the latest lap in the list no matter if lapDuration == null
     * and best lap will be the lap with least time*/

    override fun getLaps(
        onIdle: () -> Unit,
        onError: (String) -> Unit
    ): Flow<List<Triple<Lap, Lap, Double>>> =
        flow<List<Triple<Lap, Lap, Double>>> {

            while (true) {

                try {

                    val lapsResponse = f1Client.getLaps("latest")

                    if (lapsResponse.isSuccessful) {

                        val driverLapsResponse = lapsResponse.body()!!.groupBy { it.driverNumber }
                        val driverLapsList = mutableListOf<Triple<Lap, Lap, Double>>()

                        driverLapsResponse.forEach { (driverNumber, lapsList) ->

                            driverLapsList.add(
                                Triple(
                                    first = lapsList.filter { it.lapDuration != null }
                                        .maxByOrNull { it.lapNumber }?.asDomain() ?: Lap(
                                        driverNumber = driverNumber,
                                        lapDuration = null,
                                        lapNumber = 1,
                                        sector1Duration = null,
                                        sector2Duration = null,
                                        sector3Duration = null,
                                        segmentsSector1 = listOf(0, 0, 0, 0, 0, 0, 0),
                                        segmentsSector2 = listOf(0, 0, 0, 0, 0, 0, 0, 0),
                                        segmentsSector3 = listOf(0, 0, 0, 0, 0, 0)
                                    ),
                                    second = lapsList.maxByOrNull { it.lapNumber }!!.asDomain(),
                                    third = lapsList.filter { it.lapDuration != null }
                                        .minOfOrNull { it.lapDuration!! } ?: 0.0,
                                )
                            )

                        }

                        emit(driverLapsList)
                        onIdle()

                    } else {
                        onError(lapsResponse.errorBody().toString())
                    }

                } catch (e: Exception) {
                    onError(e.message.toString())
                }

                delay(5500)

            }

        }.flowOn(ioDispatcher)

    /** Get the stint list from the API and return a list with the latest [Stint] for each driver */

    override fun getStints(onIdle: () -> Unit, onError: (String) -> Unit): Flow<List<Stint>> =
        flow<List<Stint>> {

            while (true) {

                try {

                    val stintsResponse = f1Client.getStints("latest")

                    if (stintsResponse.isSuccessful) {

                        val driverStints = stintsResponse.body()!!.groupBy { it.driverNumber }
                        val driverStintsList = mutableListOf<Stint>()

                        driverStints.forEach { (_, stintList) ->

                            driverStintsList.add(
                                stintList.maxByOrNull { it.stintNumber }!!.asDomain()
                            )

                        }

                        emit(driverStintsList)
                        onIdle()

                    } else {
                        onError(stintsResponse.errorBody().toString())
                    }


                } catch (e: Exception) {
                    onError(e.message.toString())
                }


                delay(9000)
            }


        }.flowOn(ioDispatcher)

    /** Get the session list from the API and return a list with the latest [Session] */

    override fun getSession(onIdle: () -> Unit, onError: (String) -> Unit): Flow<List<Session>> = flow {

            val sessionResponse = f1Client.getSession("latest")

            if (sessionResponse.isSuccessful) {

                emit(sessionResponse.body()!!.asDomain())
                onIdle()

            } else {
                onError(sessionResponse.errorBody().toString())
            }

        }.retryWhen { cause: Throwable, _ ->
            if (cause is IOException) {
                onError(cause.message.toString())
                delay(5000)
                true
            } else {
                false
            }
        }.flowOn(ioDispatcher)
}