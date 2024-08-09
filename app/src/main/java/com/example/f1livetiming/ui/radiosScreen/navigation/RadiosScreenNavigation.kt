package com.example.f1livetiming.ui.radiosScreen.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.f1livetiming.ui.radiosScreen.RadiosScrenRoute

const val radiosScreenRoute = "RadiosScreenRoute"

fun NavGraphBuilder.radiosScreen(){

    composable(route = radiosScreenRoute){

        RadiosScrenRoute()

    }

}