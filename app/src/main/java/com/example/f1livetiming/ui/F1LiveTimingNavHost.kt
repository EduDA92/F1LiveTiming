package com.example.f1livetiming.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.f1livetiming.ui.liveTimingScreen.navigation.liveTimingScreen
import com.example.f1livetiming.ui.liveTimingScreen.navigation.liveTimingScreenRoute
import com.example.f1livetiming.ui.radiosScreen.navigation.radiosScreen

@Composable
fun F1LiveTimingNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = liveTimingScreenRoute,
        modifier = modifier
    ) {

        liveTimingScreen()
        radiosScreen()

    }

}