package com.example.f1livetiming.viewModel

import com.example.f1livetiming.data.repository.ResponseState
import com.example.f1livetiming.data.repository.TestF1LiveTimingRepositoryImpl
import com.example.f1livetiming.ui.liveTimingScreen.LiveTimingData
import com.example.f1livetiming.ui.liveTimingScreen.LiveTimingUIState
import com.example.f1livetiming.ui.liveTimingScreen.LiveTimingViewModel
import com.example.f1livetiming.ui.model.Driver
import com.example.f1livetiming.ui.model.DriverPosition
import com.example.f1livetiming.ui.model.Interval
import com.example.f1livetiming.ui.model.Lap
import com.example.f1livetiming.ui.model.Session
import com.example.f1livetiming.ui.model.Stint
import com.example.f1livetiming.utils.MainDispatcherRule
import com.example.f1livetiming.utils.fullDataExpectedResponse
import com.example.f1livetiming.utils.incompleteDataExpectedResponse
import com.example.f1livetiming.utils.noRaceExpectedResponse
import com.example.f1livetiming.utils.nullDataExpectedResponse
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class F1LiveTimingViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: TestF1LiveTimingRepositoryImpl
    private lateinit var viewModel: LiveTimingViewModel

    @Before
    fun setup() {

        repository = TestF1LiveTimingRepositoryImpl()
        viewModel = LiveTimingViewModel(repository)

    }

    /**
     * The initial value of the [LiveTimingUIState] should be Loading until the combine flow operation for
     * the [LiveTimingData] assembly is done.
     * The initial value of the [LiveTimingData] is driverDataList = emptyList()
     * sessionName = "Loading", countryCode = "UNK", circuitName = "LiveTiming"*/

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun f1LiveTimingViewModel_initialState_loadingState() = runTest {

        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.liveTimingUIState.collect()
        }

        val collectJob2 = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.liveTimingData.collect()
        }

        assertEquals(LiveTimingUIState.Loading, viewModel.liveTimingUIState.value)
        assertEquals(
            LiveTimingData(
                sessionName = "Loading",
                countryCode = "UNK",
                circuitName = "LiveTiming",
                driverDataList = persistentListOf()
            ),
            viewModel.liveTimingData.value
        )

        collectJob.cancel()
        collectJob2.cancel()
    }

    /** When [ResponseState] is Error [LiveTimingUIState] should be Error
     * [LiveTimingData] default values */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun f1LiveTimingViewModel_whenApiSendsError_ErrorState() = runTest {

        /** Setup the state of the repo */
        repository.changeResponseState(state = ResponseState.ERROR)

        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.liveTimingUIState.collect()
        }

        val collectJob2 = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.liveTimingData.collect()
        }

        assertEquals(LiveTimingUIState.Error("Error"), viewModel.liveTimingUIState.value)
        assertEquals(
            LiveTimingData(
                sessionName = "Loading",
                countryCode = "UNK",
                circuitName = "LiveTiming",
                driverDataList = persistentListOf()
            ),
            viewModel.liveTimingData.value
        )

        collectJob.cancel()
        collectJob2.cancel()
    }

    /** When [ResponseState] is SUCCESS [LiveTimingUIState] should be Iddle
     * [LiveTimingData] should contain the data passed to the repository */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun f1LiveTimingViewModel_whenApiSendsData_IdleStateAndLiveTimingDataProperlyModeled() =
        runTest {

            /** Setup the state of the repo first */
            repository.changeResponseState(state = ResponseState.SUCCESS)
            repository.changeDriverPositionList(listOf(DriverPosition(1, 3, 1)))
            repository.changeDriverList(listOf(Driver("VER", 1, "#FF3671C6")))
            repository.changeLapsList(fullLapListData)
            repository.changeStintList(
                listOf(
                    Stint(
                        compound = "WET",
                        driverNumber = 1,
                        lapEnd = 24,
                        lapStart = 1,
                        stintNumber = 4,
                        tyreAgeAtStart = 2
                    )
                )
            )
            repository.changeSession(
                listOf(
                    Session(
                        sessionName = "Race",
                        countryName = "Great Britain",
                        countryCode = "GBR",
                        circuitName = "Silverstone",
                    )
                )
            )
            repository.changeIntervals(
                listOf(
                    Interval(
                        driverNumber = 1,
                        gapToLeader = "0",
                        interval = "0"
                    )
                )
            )

            val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.liveTimingUIState.collect()
            }

            val collectJob2 = launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.liveTimingData.collect()
            }


            assertEquals(LiveTimingUIState.Idle, viewModel.liveTimingUIState.value)

            assertEquals(
                fullDataExpectedResponse,
                viewModel.liveTimingData.value
            )

            collectJob.cancel()
            collectJob2.cancel()
        }

    /** When there is no lap/Stint data associated to a driver the default lap data sent to the UI
     * will be 0.0, and the stint data the default one too */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun f1LiveTimingViewModel_whenApiSendsIncompleteLapData_lapDataIsDefaultData() = runTest {

        /** Setup the state of the repo first */
        repository.changeResponseState(state = ResponseState.SUCCESS)
        repository.changeDriverPositionList(listOf(DriverPosition(1, 1, 1)))
        repository.changeDriverList(listOf(Driver("VER", 1, "#FF3671C6")))
        repository.changeLapsList(emptyList())
        repository.changeStintList(emptyList())
        repository.changeSession(emptyList())
        repository.changeIntervals(emptyList())

        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.liveTimingUIState.collect()
        }

        val collectJob2 = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.liveTimingData.collect()
        }

        assertEquals(LiveTimingUIState.Idle, viewModel.liveTimingUIState.value)

        assertEquals(
            incompleteDataExpectedResponse,
            viewModel.liveTimingData.value
        )

        collectJob.cancel()
        collectJob2.cancel()
    }


    /** When the data lap data from the API contains null lap duration the default values for the UI
     * will be 0.0 for best & last lap Also when it sends Null data for Stint compound and tyre age
     * default values will be "UNK" and 0 respectively */

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun f1LiveTimingViewModel_whenApiSendNullData_dataIsDefaultData() = runTest {

        /** Setup the state of the repo first */
        repository.changeResponseState(state = ResponseState.SUCCESS)
        repository.changeDriverPositionList(listOf(DriverPosition(1, 1, 1)))
        repository.changeDriverList(listOf(Driver("VER", 1, "#FF3671C6")))
        repository.changeLapsList(nullLapListData)
        repository.changeSession(emptyList())

        repository.changeStintList(
            listOf(
                Stint(
                    compound = null,
                    driverNumber = 1,
                    lapEnd = 24,
                    lapStart = 1,
                    stintNumber = 4,
                    tyreAgeAtStart = null
                )
            )
        )

        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.liveTimingUIState.collect()
        }

        val collectJob2 = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.liveTimingData.collect()
        }

        assertEquals(LiveTimingUIState.Idle, viewModel.liveTimingUIState.value)

        assertEquals(
            nullDataExpectedResponse,
            viewModel.liveTimingData.value
        )

        collectJob.cancel()
        collectJob2.cancel()
    }

    /** When the current session its not a Race the positionChange remains at 0
     * and the intervals are calculated based on the fastest lap difference */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun f1LiveTimingViewModel_whenSessionIsNotRace_positionsAreZeroAndIntervalsAreCalculatedDifferent() =
        runTest {

            /** Setup the state of the repo first */
            repository.changeResponseState(state = ResponseState.SUCCESS)
            repository.changeDriverPositionList(
                listOf(
                    DriverPosition(1, 1, 1),
                    DriverPosition(14, 2, 2)
                )
            )
            repository.changeDriverList(
                listOf(
                    Driver("VER", 1, "#FF3671C6"),
                    Driver("ALO", 14, "#FF358C75")
                )
            )
            repository.changeLapsList(noRaceLapListData)
            repository.changeStintList(
                listOf(
                    Stint(
                        compound = "WET",
                        driverNumber = 1,
                        lapEnd = 24,
                        lapStart = 1,
                        stintNumber = 4,
                        tyreAgeAtStart = 2
                    ),
                    Stint(
                        compound = "WET",
                        driverNumber = 14,
                        lapEnd = 24,
                        lapStart = 1,
                        stintNumber = 4,
                        tyreAgeAtStart = 2
                    )
                )
            )
            repository.changeSession(
                listOf(
                    Session(
                        sessionName = "Qualifying",
                        countryName = "Great Britain",
                        countryCode = "GBR",
                        circuitName = "Silverstone",
                    )
                )
            )

            repository.changeIntervals(
                listOf(
                    Interval(
                        driverNumber = 1,
                        gapToLeader = "0",
                        interval = "0"
                    ),
                    Interval(
                        driverNumber = 14,
                        gapToLeader = "1.567",
                        interval = "1.567"
                    )
                )
            )

            val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.liveTimingUIState.collect()
            }

            val collectJob2 = launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.liveTimingData.collect()
            }


            assertEquals(LiveTimingUIState.Idle, viewModel.liveTimingUIState.value)

            assertEquals(
                noRaceExpectedResponse,
                viewModel.liveTimingData.value
            )

            collectJob.cancel()
            collectJob2.cancel()

        }

    private val fullLapListData = listOf(
        Triple(
            first = Lap(
                driverNumber = 1,
                lapDuration = 79.774,
                lapNumber = 1,
                sector1Duration = 24.062,
                sector2Duration = 32.054,
                sector3Duration = 23.658,
                segmentsSector1 = listOf(
                    2048,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049
                ),
                segmentsSector2 = listOf(
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2051,
                    2049
                ),
                segmentsSector3 = listOf(
                    2048,
                    2048,
                    2049,
                    2051,
                    2048,
                    2048
                )

            ),
            second = Lap(
                driverNumber = 1,
                lapDuration = 79.774,
                lapNumber = 1,
                sector1Duration = 24.062,
                sector2Duration = 32.054,
                sector3Duration = 23.658,
                segmentsSector1 = listOf(
                    2048,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049
                ),
                segmentsSector2 = listOf(
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2051,
                    2049
                ),
                segmentsSector3 = listOf(
                    2048,
                    2048,
                    2049,
                    2051,
                    2048,
                    2048
                )

            ),
            third = 77.776
        )
    )

    private val nullLapListData = listOf(
        Triple(
            first = Lap(
                driverNumber = 1,
                lapDuration = null,
                lapNumber = 1,
                sector1Duration = null,
                sector2Duration = 32.054,
                sector3Duration = 23.658,
                segmentsSector1 = listOf(
                    2048,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049
                ),
                segmentsSector2 = listOf(
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2051,
                    2049
                ),
                segmentsSector3 = listOf(
                    2048,
                    2048,
                    2049,
                    2051,
                    2048,
                    2048
                )

            ),
            second = Lap(
                driverNumber = 1,
                lapDuration = null,
                lapNumber = 1,
                sector1Duration = null,
                sector2Duration = 32.054,
                sector3Duration = 23.658,
                segmentsSector1 = listOf(
                    2048,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049
                ),
                segmentsSector2 = listOf(
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2051,
                    2049
                ),
                segmentsSector3 = listOf(
                    2048,
                    2048,
                    2049,
                    2051,
                    2048,
                    2048
                )

            ),
            third = 0.0
        )
    )

    private val noRaceLapListData = listOf(
        Triple(
            first = Lap(
                driverNumber = 1,
                lapDuration = 79.774,
                lapNumber = 1,
                sector1Duration = 24.062,
                sector2Duration = 32.054,
                sector3Duration = 23.658,
                segmentsSector1 = listOf(
                    2048,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049
                ),
                segmentsSector2 = listOf(
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2051,
                    2049
                ),
                segmentsSector3 = listOf(
                    2048,
                    2048,
                    2049,
                    2051,
                    2048,
                    2048
                )

            ),
            second = Lap(
                driverNumber = 1,
                lapDuration = 79.774,
                lapNumber = 1,
                sector1Duration = 24.062,
                sector2Duration = 32.054,
                sector3Duration = 23.658,
                segmentsSector1 = listOf(
                    2048,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049
                ),
                segmentsSector2 = listOf(
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2051,
                    2049
                ),
                segmentsSector3 = listOf(
                    2048,
                    2048,
                    2049,
                    2051,
                    2048,
                    2048
                )

            ),
            third = 77.776
        ),
        Triple(
            first = Lap(
                driverNumber = 14,
                lapDuration = 79.774,
                lapNumber = 1,
                sector1Duration = 24.062,
                sector2Duration = 32.054,
                sector3Duration = 23.658,
                segmentsSector1 = listOf(
                    2048,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049
                ),
                segmentsSector2 = listOf(
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2051,
                    2049
                ),
                segmentsSector3 = listOf(
                    2048,
                    2048,
                    2049,
                    2051,
                    2048,
                    2048
                )

            ),
            second = Lap(
                driverNumber = 14,
                lapDuration = 79.774,
                lapNumber = 1,
                sector1Duration = 24.062,
                sector2Duration = 32.054,
                sector3Duration = 23.658,
                segmentsSector1 = listOf(
                    2048,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049
                ),
                segmentsSector2 = listOf(
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2049,
                    2051,
                    2049
                ),
                segmentsSector3 = listOf(
                    2048,
                    2048,
                    2049,
                    2051,
                    2048,
                    2048
                )

            ),
            third = 79.776
        )
    )

}