package com.example.f1livetiming

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoActivityResumedException
import com.example.f1livetiming.ui.liveTimingScreen.navigation.liveTimingScreenRoute
import com.example.f1livetiming.ui.radiosScreen.navigation.radiosScreenRoute
import org.junit.Rule
import org.junit.Test


class LiveTimingNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun firstScreen_isLiveTimingScreen() {
        composeTestRule.onNodeWithTag(liveTimingScreenRoute).assertExists()
    }

    @Test
    fun navigationBar_RadioScreenButton_navigatesToRadioScreen(){
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.radios_screen_sr)).performClick()
        composeTestRule.onNodeWithTag(radiosScreenRoute).assertExists()
    }

    @Test
    fun navigationBar_WhenBackFromTopDestinations_navigatesToLiveTimingScreen(){
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.radios_screen_sr)).performClick()
        Espresso.pressBack()
        composeTestRule.onNodeWithTag(liveTimingScreenRoute).assertExists()
    }

    @Test(expected = NoActivityResumedException::class)
    fun navigationBar_BackFromMainScreen_ExitsApp(){
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.radios_screen_sr)).performClick()
        Espresso.pressBack()
        Espresso.pressBack()
    }


}