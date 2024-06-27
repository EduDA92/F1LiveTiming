package com.example.f1livetiming.ui.liveTimingScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.f1livetiming.R

@Composable
fun DriverStint(
    modifier: Modifier = Modifier,
    tireCompound: String,
    stintLaps: Int,
    pitNumber: Int
) {

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {

        when (tireCompound) {
            "SOFT" -> {
                Image(
                    painter = painterResource(id = R.drawable.soft), contentDescription = "",
                    modifier = Modifier.size(36.dp)
                )
            }

            "MEDIUM" -> {
                Image(
                    painter = painterResource(id = R.drawable.medium), contentDescription = "",
                    modifier = Modifier.size(36.dp)
                )
            }

            "HARD" -> {
                Image(
                    painter = painterResource(id = R.drawable.hard), contentDescription = "",
                    modifier = Modifier.size(36.dp)
                )
            }

            "WET" -> {
                Image(
                    painter = painterResource(id = R.drawable.wet), contentDescription = "",
                    modifier = Modifier.size(36.dp)
                )
            }

            "INTERMEDIATE" -> {
                Image(
                    painter = painterResource(id = R.drawable.intermediate),
                    contentDescription = "", modifier = Modifier.size(36.dp)
                )
            }

            else -> {/** TODO UNK STATE */   }
        }

        Spacer(modifier = Modifier.size(4.dp))

        Column {
            val stintLapsString = String.format("L %d", stintLaps)
            Text(text = stintLapsString)
            Text(text = pitNumber.toString(), color = Color.Gray)
        }

    }

}

@Preview
@Composable
fun DriverStintPreview() {
    DriverStint(tireCompound = "SOFT", stintLaps = 22, pitNumber = 3)
}