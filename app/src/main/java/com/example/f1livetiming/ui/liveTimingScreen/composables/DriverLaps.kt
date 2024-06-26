package com.example.f1livetiming.ui.liveTimingScreen.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
fun DriverLaps(
    modifier: Modifier = Modifier,
    lastLap: Double,
    bestLap: Double
) {
    
    Column(modifier = modifier) {

         val lastLapFormatted = lastLap.toDuration(DurationUnit.SECONDS).toComponents { minutes, seconds, nanoseconds ->
            String.format("%02d:%02d.%03d", minutes, seconds, nanoseconds.div(1000000))
        }

        val bestLapFormatted = bestLap.toDuration(DurationUnit.SECONDS).toComponents { minutes, seconds, nanoseconds ->
            String.format("%02d:%02d.%03d", minutes, seconds, nanoseconds.div(1000000))
        }
        
        Text(lastLapFormatted, fontSize = 18.sp)
        Text(bestLapFormatted, fontSize = 12.sp, color = Color.Gray)
        
    }

}

@Preview
@Composable
fun DriverLapsPreview(){
    
    DriverLaps(lastLap = 79.774, bestLap = 77.776)
    
}