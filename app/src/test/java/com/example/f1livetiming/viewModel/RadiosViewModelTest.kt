package com.example.f1livetiming.viewModel

import com.example.f1livetiming.data.repository.ResponseState
import com.example.f1livetiming.data.repository.TestF1LiveTimingRepositoryImpl
import com.example.f1livetiming.ui.model.Driver
import com.example.f1livetiming.ui.model.TeamRadio
import com.example.f1livetiming.ui.radiosScreen.RadiosData
import com.example.f1livetiming.ui.radiosScreen.RadiosUIState
import com.example.f1livetiming.ui.radiosScreen.RadiosViewModel
import com.example.f1livetiming.utils.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class RadiosViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: TestF1LiveTimingRepositoryImpl
    private lateinit var viewModel: RadiosViewModel

    @Before
    fun setup(){
        repository = TestF1LiveTimingRepositoryImpl()
        viewModel = RadiosViewModel(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun radiosViewModel_initialState_loadingState() = runTest {

       backgroundScope.launch(UnconfinedTestDispatcher()){
            viewModel.radiosUIState.collect()
        }

        backgroundScope.launch(UnconfinedTestDispatcher()){
            viewModel.radiosData.collect()
        }

        assertEquals(RadiosUIState.Loading, viewModel.radiosUIState.value)
        assertEquals(RadiosData(emptyMap()), viewModel.radiosData.value)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun radiosViewModel_whenApiSendsError_ErrorState() = runTest {

        repository.changeResponseState(ResponseState.ERROR)

        backgroundScope.launch(UnconfinedTestDispatcher()){
            viewModel.radiosUIState.collect()
        }

        backgroundScope.launch(UnconfinedTestDispatcher()){
            viewModel.radiosData.collect()
        }

        assertEquals(RadiosUIState.Error("Error"), viewModel.radiosUIState.value)
        assertEquals(RadiosData(emptyMap()), viewModel.radiosData.value)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun radiosViewModel_whenApiSendsData_IdleStateAndRadiosDataProperlyModeled() = runTest {

        val currentTimeZone = ZoneId.systemDefault()

        // Parse the UTC Date
        val dateTimeFormatted = LocalDateTime.parse("2024-07-28T14:25:45.693000+00:00", DateTimeFormatter.ISO_DATE_TIME)

        // Convert the UTC time to the device time zone
        val localDateTime =
            dateTimeFormatted.atZone(ZoneId.of("UTC")).withZoneSameInstant(currentTimeZone)
                .toLocalDateTime()

        // Formater to format the time to HH:mm:ss
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

        val formattedDate = localDateTime.format(formatter)

        repository.changeResponseState(state = ResponseState.SUCCESS)
        repository.changeDriverList(listOf(Driver("VER", 1, "#FF3671C6")))
        repository.changeRadioList(listOf(TeamRadio(driverNumber = 1, recordingUrl = "URL", date = "2024-07-28T14:25:45.693000+00:00")))

        backgroundScope.launch(UnconfinedTestDispatcher()){
            viewModel.radiosUIState.collect()
        }

        backgroundScope.launch(UnconfinedTestDispatcher()){
            viewModel.radiosData.collect()
        }

        assertEquals(RadiosUIState.Idle, viewModel.radiosUIState.value)
        assertEquals(RadiosData(
                mapOf(
                    Pair("VER", "#FF3671C6") to
                    listOf(TeamRadio(driverNumber = 1, recordingUrl = "URL", date = formattedDate))

            )), viewModel.radiosData.value)

    }

}