package com.example.f1livetiming.repository

import com.example.f1livetiming.data.network.F1Client
import com.example.f1livetiming.data.network.F1Service
import com.example.f1livetiming.data.network.converter.EmptyConverter
import com.example.f1livetiming.data.repository.F1LiveTimingRepositoryImpl
import com.example.f1livetiming.ui.model.DriverPosition
import com.example.f1livetiming.ui.model.Interval
import com.example.f1livetiming.ui.model.Lap
import com.example.f1livetiming.ui.model.Stint
import com.example.f1livetiming.ui.model.TeamRadio
import com.example.f1livetiming.utils.MainDispatcherRule
import com.example.f1livetiming.utils.enqueueResponse
import com.example.f1livetiming.utils.expectedDriverResponse
import com.example.f1livetiming.utils.expectedFullDriverPositionResponse
import com.example.f1livetiming.utils.expectedLapsResponse
import com.example.f1livetiming.utils.expectedNullLapDurationResponse
import com.example.f1livetiming.utils.expectedStintsResponse
import com.example.f1livetiming.utils.intervalsExpectedResponse
import com.example.f1livetiming.utils.radiosExpectedResponse
import com.example.f1livetiming.utils.sessionsExpectedResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


class F1LiveTimingRepositoryImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: F1LiveTimingRepositoryImpl
    private lateinit var f1Client: F1Client
    private lateinit var f1Service: F1Service
    private lateinit var mockWebServer: MockWebServer

    /* Setup f1service with mockWebServer and initialize client and repository */
    @Before
    fun setup(){

        val networkJson = Json {
            ignoreUnknownKeys = true
        }

        mockWebServer = MockWebServer()
        mockWebServer.start()

        f1Service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(EmptyConverter())
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(F1Service::class.java)

        f1Client = F1Client(f1Service)

        repository = F1LiveTimingRepositoryImpl(f1Client, mainDispatcherRule.testDispatcher)

    }

    @After
    fun stopServer() {
        mockWebServer.shutdown()
    }


    @Test
    fun f1LiveTimingRepositoryImpl_fullPositionResponse_returnsExpectedPositionList() = runTest {

        mockWebServer.enqueueResponse("PositionsFullResponse.json")

        val result = repository.getDriversPositions(
            onIdle = {},
            onError = {}
        ).first()

        assertEquals(expectedFullDriverPositionResponse, result)

    }

    @Test
    fun f1LiveTimingRepositoryImpl_emptyPositionResponse_returnsEmptyList() = runTest {

        mockWebServer.enqueueResponse("EmptyResponse.json")

        val result = repository.getDriversPositions(
            onIdle = {},
            onError = {}
        ).first()

        assertEquals(result, emptyList<DriverPosition>())

    }

    @Test
    fun f1LiveTimingRepositoryImpl_fullDriverResponse_returnsExpectedList() = runTest {

        mockWebServer.enqueueResponse("DriversResponse.json")

        val result = repository.getDrivers(
            onIdle = {},
            onError = {}
        ).first()

        assertEquals(expectedDriverResponse, result)

    }

    @Test
    fun f1LiveTimingRepositoryImpl_emptyLapsResponse_returnsEmptyList() = runTest {

        mockWebServer.enqueueResponse("EmptyResponse.json")

        val result = repository.getLaps(
            onIdle = {},
            onError = {}
        ).first()

        assertEquals(emptyList<Pair<Lap, Double>>(), result)

    }

    @Test
    fun f1LiveTimingRepositoryImpl_fullLapsResponse_returnsExpectedList() = runTest{

        mockWebServer.enqueueResponse("LapsResponse.json")

        /* Result for the first 3 drivers only */
        val result = repository.getLaps(
            onIdle = {},
            onError = {}
        ).first().filter { it.first.driverNumber == 1 || it.first.driverNumber == 2 || it.first.driverNumber == 3 }

        assertEquals(expectedLapsResponse, result)

    }

    @Test
    fun f1LiveTimingRepositoryImpl_nullLapDurationResponse_returnsDefaultValueBestLap() = runTest {

        mockWebServer.enqueueResponse("nullLapDurationResponse.json")

        val result = repository.getLaps(
            onIdle = {},
            onError = {}
        ).first()

        assertEquals(expectedNullLapDurationResponse, result)

    }

    @Test
    fun f1LiveTimingRepositoryImpl_emptyStintResponse_returnsEmptyList() = runTest {

        mockWebServer.enqueueResponse("EmptyResponse.json")

        val result = repository.getStints(
            onIdle = {},
            onError = {}
        ).first()

        assertEquals(emptyList<Stint>(), result)

    }

    @Test
    fun f1LiveTimingRepositoryImpl_fullStintResponse_returnsExpectedList() = runTest {

        mockWebServer.enqueueResponse("StintsResponse.json")

        val result = repository.getStints(
            onIdle = {},
            onError = {}
        ).first().filter { it.driverNumber == 1 || it.driverNumber == 2 || it.driverNumber == 3 }.sortedBy { it.driverNumber }

        assertEquals(expectedStintsResponse, result)
    }

    @Test
    fun f1LiveTimingRepositoryImpl_sessionResponse_returnsExpectedResponse() = runTest {

        mockWebServer.enqueueResponse("SessionResponse.json")

        val result = repository.getSession(
           onIdle = {},
            onError = {}
        ).first()

        assertEquals(sessionsExpectedResponse, result)

    }

    @Test
    fun f1LiveTimingRepositoryImpl_intervalResponse_returnsExpectedResponse() = runTest {

        mockWebServer.enqueueResponse("IntervalResponse.json")

        val result = repository.getIntervals(
            onIdle = {},
            onError = {}
        ).first().filter { it.driverNumber == 44 || it.driverNumber == 1 || it.driverNumber == 3 || it.driverNumber == 4 }.sortedBy { it.driverNumber }

        assertEquals(intervalsExpectedResponse, result)

    }

    @Test
    fun f1LiveTimingRepositoryImpl_nullIntervalResponse_returnsEmptyList() = runTest {

        mockWebServer.enqueueResponse("nullResponse.json")

        val result = repository.getIntervals(
            onIdle = {},
            onError = {}
        ).first()

        assertEquals(emptyList<Interval>(), result)

    }

    @Test
    fun f1LiveTimingRepositoryImpl_radiosResponse_returnsExpectedResponse() = runTest {

        mockWebServer.enqueueResponse("TeamsRadio.json")

        val result = repository.getTeamsRadio(
            onIdle = {},
            onError = {}
        ).first()

        assertEquals(
            radiosExpectedResponse,
            result
        )

    }

    @Test
    fun f1LiveTimingRepositoryImpl_radiosEmptyResponse_returnsEmptyList() = runTest {

        mockWebServer.enqueueResponse("nullResponse.json")

        val result = repository.getTeamsRadio(
            onIdle = {},
            onError = {}
        ).first()

        assertEquals(emptyList<TeamRadio>(), result)

    }




}