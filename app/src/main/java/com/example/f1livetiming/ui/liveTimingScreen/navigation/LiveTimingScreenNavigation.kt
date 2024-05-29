package com.example.f1livetiming.ui.liveTimingScreen.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.f1livetiming.ui.liveTimingScreen.LiveTimingRoute

const val liveTimingScreenRoute = "LiveTimingScreen"

fun NavGraphBuilder.liveTimingScreen(){

    composable(route = liveTimingScreenRoute){

        LiveTimingRoute()

    }

}