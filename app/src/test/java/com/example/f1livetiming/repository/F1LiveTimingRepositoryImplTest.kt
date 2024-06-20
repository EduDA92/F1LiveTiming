package com.example.f1livetiming.repository

import com.example.f1livetiming.data.network.F1Client
import com.example.f1livetiming.data.network.F1Service
import com.example.f1livetiming.data.repository.F1LiveTimingRepositoryImpl
import com.example.f1livetiming.ui.model.Driver
import com.example.f1livetiming.ui.model.DriverPosition
import com.example.f1livetiming.utils.MainDispatcherRule
import com.example.f1livetiming.utils.enqueueResponse
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

        assertEquals(expectedFullResponse, result)

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
    fun f1LiveTimingRepositoryImpl_FullDriverResponse_returnsExpectedList() = runTest {

        mockWebServer.enqueueResponse("DriversResponse.json")

        val result = repository.getDrivers(
            onIdle = {},
            onError = {}
        ).first()

        assertEquals(expectedDriverResponse, result)

    }


    private val expectedDriverResponse = listOf(
        Driver(
            driverAcronym = "VER",
            driverNumber = 1,
            teamColor = "#3671C6"
        ),
        Driver(
            driverAcronym = "SAR",
            driverNumber = 2,
            teamColor = "#64C4FF"
        )
    )

    private val expectedFullResponse = listOf(
        DriverPosition(
            driverPosition = 1,
            driverNumber = 1
        ),
        DriverPosition(
            driverPosition = 2,
            driverNumber = 4
        ),
        DriverPosition(
            driverPosition = 3,
            driverNumber = 63
        ),
        DriverPosition(
            driverPosition = 4,
            driverNumber = 44
        ),
        DriverPosition(
            driverPosition = 5,
            driverNumber = 81
        ),
        DriverPosition(
            driverPosition = 6,
            driverNumber = 14
        ),
        DriverPosition(
            driverPosition = 7,
            driverNumber = 18
        ),
        DriverPosition(
            driverPosition = 8,
            driverNumber = 3
        ),
        DriverPosition(
            driverPosition = 9,
            driverNumber = 10
        ),
        DriverPosition(
            driverPosition = 10,
            driverNumber = 31
        ),
        DriverPosition(
            driverPosition = 11,
            driverNumber = 27
        ),
        DriverPosition(
            driverPosition = 12,
            driverNumber = 20
        ),
        DriverPosition(
            driverPosition = 13,
            driverNumber = 77
        ),
        DriverPosition(
            driverPosition = 14,
            driverNumber = 22
        ),
        DriverPosition(
            driverPosition = 15,
            driverNumber = 24
        ),
        DriverPosition(
            driverPosition = 16,
            driverNumber = 55
        ),
        DriverPosition(
            driverPosition = 17,
            driverNumber = 23
        ),
        DriverPosition(
            driverPosition = 18,
            driverNumber = 11
        ),
        DriverPosition(
            driverPosition = 19,
            driverNumber = 16
        ),
        DriverPosition(
            driverPosition = 20,
            driverNumber = 2
        )
    )

}