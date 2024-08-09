package com.example.f1livetiming

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.f1livetiming.ui.liveTimingScreen.navigation.liveTimingScreenRoute
import com.example.f1livetiming.ui.radiosScreen.navigation.radiosScreenRoute

sealed class Screens(val route: String, @StringRes val resourceId: Int, @DrawableRes val drawableId: Int) {

    object LiveTimingScreen: Screens(liveTimingScreenRoute, R.string.live_timing_screen_sr, R.drawable.outline_timer_24)
    object RadiosScreen: Screens(radiosScreenRoute, R.string.radios_screen_sr, R.drawable.headset)

}