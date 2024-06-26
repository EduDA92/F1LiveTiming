package com.example.f1livetiming.viewModel

import com.example.f1livetiming.data.repository.ResponseState
import com.example.f1livetiming.data.repository.TestF1LiveTimingRepositoryImpl
import com.example.f1livetiming.ui.liveTimingScreen.DriverData
import com.example.f1livetiming.ui.liveTimingScreen.LiveTimingData
import com.example.f1livetiming.ui.liveTimingScreen.LiveTimingUIState
import com.example.f1livetiming.ui.liveTimingScreen.LiveTimingViewModel
import com.example.f1livetiming.ui.model.Driver
import com.example.f1livetiming.ui.model.DriverPosition
import com.example.f1livetiming.ui.model.Lap
import com.example.f1livetiming.utils.MainDispatcherRule
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
     * The initial value of the [LiveTimingData] is driverDataList = emptyList() */

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
        assertEquals(LiveTimingData(driverDataList = emptyList()), viewModel.liveTimingData.value)

        collectJob.cancel()
        collectJob2.cancel()
    }

    /** When [ResponseState] is Error [LiveTimingUIState] should be Error
     * [LiveTimingData] driverDataList = emptyList() */
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
        assertEquals(LiveTimingData(driverDataList = emptyList()), viewModel.liveTimingData.value)

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
            repository.changeDriverPositionList(listOf(DriverPosition(1, 1)))
            repository.changeDriverList(listOf(Driver("VER", 1, "#FF3671C6")))
            repository.changeLapsList(
                listOf(
                    Pair(
                        first = Lap(
                            driverNumber = 1,
                            lapDuration = 79.774,
                            lapNumber = 66,
                            sector1Duration = 23.251,
                            sector2Duration = 32.326,
                            sector3Duration = 24.197,
                            segmentsSector1 = listOf(
                                2049,
                                2049,
                                2048,
                                2048,
                                2048,
                                2048,
                                2048
                            ),
                            segmentsSector2 = listOf(
                                2048,
                                2048,
                                2048,
                                2048,
                                2048,
                                2048,
                                2048,
                                2048
                            ),
                            segmentsSector3 = listOf(
                                2048,
                                2048,
                                2048,
                                2048,
                                2064,
                                2064
                            )

                        ),
                        second = 77.776
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
                LiveTimingData(
                    driverDataList = listOf(
                        DriverData(
                            driverNumber = 1,
                            driverPosition = 1,
                            driverAcronym = "VER",
                            teamColor = "#FF3671C6",
                            bestLap = 77.776,
                            lastLap = 79.774
                        )
                    )
                ),
                viewModel.liveTimingData.value
            )

            collectJob.cancel()
            collectJob2.cancel()
        }

    /** When there is no lap data associated to a driver the default lap data sent to the UI
     * will be 0.0 */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun f1LiveTimingViewModel_whenApiSendsIncompleteLapData_lapDataIsDefaultData() = runTest {

        /** Setup the state of the repo first */
        repository.changeResponseState(state = ResponseState.SUCCESS)
        repository.changeDriverPositionList(listOf(DriverPosition(1, 1)))
        repository.changeDriverList(listOf(Driver("VER", 1, "#FF3671C6")))
        repository.changeLapsList(
            listOf(Pair(
                first = Lap(
                    driverNumber = 256,
                    lapDuration = 79.774,
                    lapNumber = 66,
                    sector1Duration = 23.251,
                    sector2Duration = 32.326,
                    sector3Duration = 24.197,
                    segmentsSector1 = listOf(
                        2049,
                        2049,
                        2048,
                        2048,
                        2048,
                        2048,
                        2048
                    ),
                    segmentsSector2 = listOf(
                        2048,
                        2048,
                        2048,
                        2048,
                        2048,
                        2048,
                        2048,
                        2048
                    ),
                    segmentsSector3 = listOf(
                        2048,
                        2048,
                        2048,
                        2048,
                        2064,
                        2064
                    )

                ),
                second = 77.776
            )) )

        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.liveTimingUIState.collect()
        }

        val collectJob2 = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.liveTimingData.collect()
        }

        assertEquals(LiveTimingUIState.Idle, viewModel.liveTimingUIState.value)

        assertEquals(
            LiveTimingData(
                driverDataList = listOf(
                    DriverData(
                        driverNumber = 1,
                        driverPosition = 1,
                        driverAcronym = "VER",
                        teamColor = "#FF3671C6",
                        bestLap = 0.0,
                        lastLap = 0.0
                    )
                )
            ),
            viewModel.liveTimingData.value
        )

        collectJob.cancel()
        collectJob2.cancel()
    }


    /** When the data lap data from the API contains null lap duration the default values for the UI
     * will be 0.0 for best & last lap */
    @Test
    fun f1LiveTimingViewModel_whenApiSendNullLapTimesData_lapDataIsDefaultData() = runTest {

        /** Setup the state of the repo first */
        repository.changeResponseState(state = ResponseState.SUCCESS)
        repository.changeDriverPositionList(listOf(DriverPosition(1, 1)))
        repository.changeDriverList(listOf(Driver("VER", 1, "#FF3671C6")))
        repository.changeLapsList(
            listOf(
                Pair(
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
                    second = 0.0
                )
            ))

        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.liveTimingUIState.collect()
        }

        val collectJob2 = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.liveTimingData.collect()
        }

        assertEquals(LiveTimingUIState.Idle, viewModel.liveTimingUIState.value)

        assertEquals(
            LiveTimingData(
                driverDataList = listOf(
                    DriverData(
                        driverNumber = 1,
                        driverPosition = 1,
                        driverAcronym = "VER",
                        teamColor = "#FF3671C6",
                        bestLap = 0.0,
                        lastLap = 0.0
                    )
                )
            ),
            viewModel.liveTimingData.value
        )

        collectJob.cancel()
        collectJob2.cancel()
    }
}