package com.example.f1livetiming

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.SemanticsNodeInteractionCollection
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.f1livetiming.ui.liveTimingScreen.DriverData
import com.example.f1livetiming.ui.liveTimingScreen.LiveTimingData
import com.example.f1livetiming.ui.liveTimingScreen.LiveTimingScreen
import com.example.f1livetiming.ui.liveTimingScreen.LiveTimingUIState
import kotlinx.collections.immutable.persistentListOf
import org.junit.Rule
import org.junit.Test
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class LiveTimingScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun idleIndicationCircle_whenAppIsIdle_isShown() {

        composeTestRule.setContent {
            LiveTimingScreen(
                state = LiveTimingUIState.Idle,
                liveTimingData = data
            )
        }

        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.idle_test_tag)).assertExists()

    }

    @Test
    fun errorIndicationCircle_whenAppIsError_isShown(){

        composeTestRule.setContent {
            LiveTimingScreen(state = LiveTimingUIState.Error(""), liveTimingData = data)
        }

        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.error_test_tag)).assertExists()

    }

    @Test
    fun loadingIndicationCircle_whenAppIsLoading_isShown(){

        composeTestRule.setContent {
            LiveTimingScreen(state = LiveTimingUIState.Loading, liveTimingData = data)
        }

        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.loading_test_tag)).assertExists()

    }

    @Test
    fun driverData_isCorrectlyDisplayed(){

        val lastLapFormatted = data.driverDataList[0].lastLap.toDuration(DurationUnit.SECONDS).toComponents { minutes, seconds, nanoseconds ->
            composeTestRule.activity.getString(R.string.last_lap_sr, minutes, seconds, nanoseconds.div(1000000))
        }

        val bestLapFormatted =data.driverDataList[0].bestLap.toDuration(DurationUnit.SECONDS).toComponents { minutes, seconds, nanoseconds ->
            composeTestRule.activity.getString(R.string.best_lap_sr, minutes, seconds, nanoseconds.div(1000000))
        }

        composeTestRule.setContent {
            LiveTimingScreen(
                state = LiveTimingUIState.Idle,
                liveTimingData = data
            )
        }

        // Check Driver Tag is correctly displayed
        composeTestRule.onNodeWithText(data.driverDataList[0].driverAcronym).assertExists()
        composeTestRule.onNodeWithText(data.driverDataList[0].driverPosition.toString()).assertExists()

        // Check Stint info is correctly displayed
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.soft_tyre_cd)).assertExists()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.stint_laps_sr, data.driverDataList[0].stintLaps)).assertExists()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.pit_number_sr, data.driverDataList[0].pitNumber)).assertExists()

        // Check Position Change is correctly displayed
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.positive_position_change_sr, data.driverDataList[0].driverPositionsChanged)).assertExists()

        // Check Interval is correctly displayed
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.interval_sr, data.driverDataList[0].interval)).assertExists()
        composeTestRule.onNodeWithText(data.driverDataList[0].gapToLeader).assertExists()

        // Check Laps is correctly displayed
        composeTestRule.onNodeWithText(lastLapFormatted).assertExists()
        composeTestRule.onNodeWithText(bestLapFormatted).assertExists()


    }

    @Test
    fun driverTimingData_whenTap_displaysMicrosectors(){

        composeTestRule.setContent {
            LiveTimingScreen(
                state = LiveTimingUIState.Idle,
                liveTimingData = data
            )
        }

        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.driver_box_test_tag)).performClick()


        //Check Micro Sector Duration
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.micro_sector_sr, data.driverDataList[0].firstSectorDuration)).assertExists()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.micro_sector_sr, data.driverDataList[0].secondSectorDuration)).assertExists()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.micro_sector_sr, data.driverDataList[0].thirdSectorDuration)).assertExists()

        //Check dots are present
        composeTestRule.onAllNodesWithTag(composeTestRule.activity.getString(R.string.micro_sector_color_dot_test_tag)).assertAreDisplayed()

    }

    private fun SemanticsNodeInteractionCollection.assertAreDisplayed(): SemanticsNodeInteractionCollection {
        fetchSemanticsNodes().forEachIndexed { index, _ ->
            get(index).assertIsDisplayed()
        }
        return this
    }

    private val data =  LiveTimingData(
        sessionName = "Race",
        countryCode = "GBR",
        circuitName = "Silverstone",
        persistentListOf(
            DriverData(
                driverNumber = 1,
                driverPosition = 1,
                driverPositionsChanged = 24,
                driverAcronym = "VER",
                teamColor = "#3671C6",
                lastLap = 79.774,
                bestLap = 77.776,
                tireCompound = "SOFT",
                pitNumber = 3,
                stintLaps = 23,
                interval = "95.554",
                gapToLeader = "1L",
                firstSectorDuration = 22.5,
                secondSectorDuration = 22.6,
                thirdSectorDuration = 76.9,
                firstMicroSectors = persistentListOf(
                    0,
                    2048,
                    2049,
                    2050,
                    2051,
                    2052,
                    2064,
                    2068
                ),
                secondMicroSectors = persistentListOf(
                    0,
                    2048,
                    2049,
                    2050,
                    2051,
                    2052,
                    2064,
                    2068
                ),
                thirdMicroSectors = persistentListOf(
                    0,
                    2048,
                    2049,
                    2050,
                    2051,
                    2052,
                    2064,
                    2068
                )
            )
        )
    )

}