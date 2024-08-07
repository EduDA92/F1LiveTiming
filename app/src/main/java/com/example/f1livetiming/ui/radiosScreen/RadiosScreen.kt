package com.example.f1livetiming.ui.radiosScreen

import android.provider.MediaStore.Audio.Radio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RadiosScrenRoute(
    modifier: Modifier = Modifier
){
    //workflow test
    RadiosScreen(
        modifier = modifier
    )

}

@Composable
fun RadiosScreen(
    modifier: Modifier = Modifier
){

        Text("Radios Screen", modifier = modifier.fillMaxSize())

}

@Preview
@Composable
fun RadiosScreenPreview(){

    RadiosScreen()

}